package com.parucnc.test_3.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.CommentVO;
import com.parucnc.test_3.domain.GenreVO;
import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.domain.User_BoardVO;
import com.parucnc.test_3.domain.fileUploadVO;
import com.parucnc.test_3.service.BoardServiceImpl;
import com.parucnc.test_3.service.CommentService;
import com.parucnc.test_3.service.CommentServiceImpl;
import com.parucnc.test_3.service.GenreServiceImpl;
import com.parucnc.test_3.service.User_BoardServiceImpl;
import com.parucnc.test_3.service.fileUploadServiceImpl;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String PATH = "C:\\fileSave\\";
	public static boolean isAdmin;
	@Inject
	private HomeController homeCon;
	@Inject
	private CommentServiceImpl commService;
	@Inject
	private BoardServiceImpl boardService;
	@Inject
	private User_BoardServiceImpl u_bService;
	@Inject
	private fileUploadServiceImpl fileUploadService;
	@Inject
	private GenreServiceImpl genreService;

	// 댓글 기능 추가 (로그인한사람이름으로) 예제x 시간되면 검색기능 ---- 댓글에도 페이징 (정말 시간 남으면)

	
	
	
	// 목록 표시 (그냥 전체 게시글 나열)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getList(Model model, BoardVO vo) throws Exception {
		List list = boardService.list(vo);
		model.addAttribute("list", list);
		return "board/list";
	}

	// 게시물 작성 창으로 이동
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String getWrite(Model model, HttpSession session, HttpServletResponse response) throws Exception {
		String id = null;
		try {
			id = getId(session);
		} catch (Exception e) {
			System.out.println("로그인이 필요한 서비스입니다.");
			return "redirect:/";
		}
		List genreList = genreService.selectedGenreList();
		
		model.addAttribute("genreList", genreList);
		model.addAttribute("userID", id);

		return "board/write";
	}

	@RequestMapping(value = "/likeOrDislike", method = RequestMethod.POST)
	@ResponseBody
	public String postLikeOrDislike(HttpServletResponse response, @RequestParam Map<String, String> param)
			throws Exception {
		User_BoardVO check = u_bService.check_clickedBefore(param);
		int count = 0;
		if (check == null) {
			u_bService.u_bMapping(param); // u_b테이블에 버튼누른사람 아이디랑 글번호 추가
		} else {
			response.setStatus(415);
			return "";
		}
		int lOrDl = Integer.parseInt(param.get("isLike"));
		int bno = Integer.parseInt(param.get("bno"));
		Map cntMap = new HashMap();
		cntMap.put("bno", bno);
		if (lOrDl == 1) {
			count = u_bService.countLike(cntMap);
		} else {
			count = u_bService.countDislike(cntMap);
		}
		return "" + count;
	}

	@RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
	public void getFileDownload(HttpServletResponse response, @RequestParam("fno") int fno) throws Exception {

		fileUploadVO fVO = fileUploadService.getFileInfo(fno);

		String originalName = fVO.getOriginalName();
		String path = fVO.getPath();
		logger.info(path);
		originalName = URLEncoder.encode(originalName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + originalName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		try (FileInputStream fis = new FileInputStream(path); OutputStream os = response.getOutputStream();) {

			int readCount = 0;
			byte[] buffer = new byte[1024];

			while ((readCount = fis.read(buffer)) != -1) {
				os.write(buffer, 0, readCount);
			}
			os.flush();
			os.close();
			fis.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// 마우스 오버되면 이미지 불러오는곳 --> 데이터 남용
	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public void getImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/jpeg");
		int fno = Integer.parseInt(request.getParameter("filenumber"));
		fileUploadVO vo = fileUploadService.getFileInfo(fno);

		String url = "file:///" + vo.getPath(); // 전체 경로
		URL fileUrl = null;
		try {
			fileUrl = new URL(url);
		} 
		catch (Exception e) {
			System.out.println("wrong url");
		}
		org.apache.commons.io.IOUtils.copy(fileUrl.openStream(), response.getOutputStream());
	}

	// 게시물 열람 + 조회수 증가 + 댓글 출력
	@RequestMapping(value = "/view/*", method = RequestMethod.GET)
	public String getView(@CookieValue(value = "page", required = false) Cookie pageCookie,
			HttpServletResponse response, HttpSession session, @RequestParam("bno") long bno, CommentVO commVo,
			Model model) throws Exception {
		String id = null;
		boolean login = false;
		if ((UserVO) session.getAttribute("userVO") != null) {
			login = true;
		}
		model.addAttribute("isLogin", login);
		id = getId(session);
		BoardVO vo;
		try {
			if (bno > Integer.MAX_VALUE) {
				System.out.println("max보다 큰 long");
				return "redirect:/board/noSuchPage";
			}
			vo = boardService.view(bno);
			vo.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/board/noSuchPage";
		}

		String createdDate = vo.getRegDate().substring(0, 10);
		String filePath = PATH + createdDate;

		Map map = new HashMap();
		map.put("bno", bno);
		List<fileUploadVO> list = new ArrayList<fileUploadVO>();
		list = fileUploadService.fileFind(map);

		model.addAttribute("fileList", list);

		File f = new File(filePath);
		File[] files = f.listFiles();

		model.addAttribute("files", files);

		UserVO userInfo = (UserVO) session.getAttribute("userVO");
		boolean alreadyParticipated = false;

		Map testMap = new HashMap();
		testMap.put("bno", bno);
		try {
			testMap.put("uno", userInfo.getUno());
			User_BoardVO check = u_bService.check_clickedBefore(testMap);

			if (check != null) {
				alreadyParticipated = true;
			}
		} catch (Exception e) {
			System.out.println("로그인되어있지 않음");
		}

		int countDislike = u_bService.countDislike(testMap);
		int countLike = u_bService.countLike(testMap);
		
		model.addAttribute("check", userInfo);
		model.addAttribute("bno", bno);
		model.addAttribute("countLike", countLike);
		model.addAttribute("countDislike", countDislike);
		model.addAttribute("alreadyParticipated", alreadyParticipated);
		model.addAttribute("userId", id);
		model.addAttribute("view", vo);

		// 조회수 증가
		String boardNumber = "[" + bno + "]";

		if (pageCookie == null || !("[" + pageCookie.getValue() + "]").contains(boardNumber)) {
			if (pageCookie != null) {
				boardNumber = pageCookie.getValue() + boardNumber;
			}
			SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
			Calendar t = Calendar.getInstance();
			String currHMS = format2.format(t.getTime());
			int hour = Integer.parseInt(currHMS.substring(0, 2));
			int minute = Integer.parseInt(currHMS.substring(3, 5));
			int second = Integer.parseInt(currHMS.substring(6));
			int passedTime = hour * 3600 + minute * 60 + second;

			Cookie cookie = new Cookie("page", boardNumber);
			cookie.setMaxAge(60 * 60 * 24 - passedTime); // 자정부터 조회수 다시 올릴수 있게
			cookie.setPath("/board/view");
			response.addCookie(cookie);
			boardService.viewUpdate(bno);

		}
		List comment = commService.showComment(commVo);
		model.addAttribute("commentList", comment); // cccc
		return "board/view";
	}

	// 댓글작성
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public String postView(HttpServletRequest request, @RequestParam("bno") int bno, CommentVO vo, Model model) throws Exception {
		String isSecret = request.getParameter("isSecret");
		int secret = isSecret == null ? 0 : 1;  
		vo.setSecret(secret);
		commService.insert(vo);
		return "redirect:/board/view/?bno=" + bno;
	}

	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(fileUploadVO fvo, MultipartHttpServletRequest request, BoardVO vo, Model model)
			throws Exception, IOException {
		if (vo.getTitle().equals("") || vo.getContent().equals("")) {
			return "redirect:/board/write?emptyBox=true";
		}
		boardService.write(vo);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		Calendar t = Calendar.getInstance();
		String currYMD = format1.format(t.getTime());

		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multi.getFiles("fileUpload");

		if (files.size() != 1 || files.get(0).getSize() != 0) { // 파일 업로드 안했을때는 파일 생성 안하게
			String todayPath = PATH + currYMD + "\\";
			File file = new File(todayPath);
			if (!file.exists()) {
				file.mkdirs(); // 새로운 폴더 만들기
			}

			int bno = boardService.mostRecentBno(); // 가장 최근 작성된 글 bno

			for (int i = 0; i < files.size(); i++) {
				UUID randomUUID = UUID.randomUUID();
				String changedName = randomUUID + files.get(i).getOriginalFilename();
				String originalName = files.get(i).getOriginalFilename();
				String fileRoot = todayPath + changedName;
				file = new File(fileRoot);
				files.get(i).transferTo(file); // $$

				Map map = new HashMap();
				map.put("bno", bno);
				map.put("originalName", originalName);
				map.put("ymd", currYMD);
				map.put("changedName", changedName);
				map.put("path", fileRoot);

				fileUploadService.fileSave(map);
			}
		}
		model.addAttribute("isAdmin", isAdmin);
		return "redirect:/board/listPage";
	}

	private String saveFile(MultipartFile file) {
		UUID uuid = UUID.randomUUID();
		String saveName = uuid + "_" + file.getOriginalFilename();
		File saveFile = new File(PATH, saveName);

		try {
			file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return saveName;
	}

	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(HttpSession session, @RequestParam("bno") int bno, Model model) throws Exception {
		BoardVO vo = boardService.view(bno);
		UserVO userVO = (UserVO)session.getAttribute("userVO");

		if(!userVO.getStatus().equals("admin") && !vo.getWriter().equals(userVO.getId())) {
			return "redirect:/board/listPage?invalidAccess=true";
		}
		boardService.delete(bno);
		return "redirect:/board/listPage";
	}

	
	// 게시물 수정창으로 이동
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String getModify(HttpSession session, @RequestParam("bno") int bno, Model model) throws Exception {
		model.addAttribute("userID", homeCon.userID);
		
		BoardVO vo = boardService.view(bno);
		UserVO userVO = (UserVO)session.getAttribute("userVO");

		if(!userVO.getStatus().equals("admin") && !vo.getWriter().equals(userVO.getId())) {
			return "redirect:/board/listPage?invalidAccess=true";
		}
		model.addAttribute("list", vo);
		return "board/modify";
	}

	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(@RequestParam("bno")int bno, 
			BoardVO vo, Model model) throws Exception {
		if (vo.getTitle().equals("") || vo.getContent().equals("")) {
			return "redirect:/board/modify/?bno="+bno+"&emptyBox=true";
		}
		boardService.update(vo);
		return "redirect:/board/listPage";
	}

	public String getId(HttpSession session) throws Exception {
		UserVO vo = (UserVO) session.getAttribute("userVO");
		String id = null;
		try {
			id = vo.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@RequestMapping(value = "commentDelete", method = RequestMethod.POST)
	@ResponseBody
	public void postCommentDelete(@RequestParam Map<String, String> param) throws Exception{
		String cno = param.get("cno");
		commService.commentDelete(cno);
	}
	
	@RequestMapping(value = "commentModify", method = RequestMethod.POST)
	@ResponseBody
	public void postCommentModify(@RequestParam Map<String, String> param) throws Exception{
		int cno = Integer.parseInt(param.get("cno"));
		String content = param.get("content");
		Map map = new HashMap();
		map.put("cno", cno);
		map.put("comm", content);
		commService.commentModify(map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/chooseGenre", method = RequestMethod.POST)
	public Object postChooseGenre(Model model, @RequestParam Map<String, String> param) throws Exception {
		String genre = param.get("genre");
		String [] genres = param.get("genre").split("#");
		
		Map map = new HashMap();
		int count;
		genre = genre.replace("#", "");
		if(genre.equals("total")) {
			genre = null;
			map.put("genres", genre);
			count = boardService.count(map);
		}
		else {
			map.put("genres", genres);
			count = boardService.count2(genres);
		}
		
		int currentPage = Integer.parseInt(param.get("currentPage"));
		int lastPage = (int) Math.ceil((double) (count) / 10);
		int startPage = currentPage % 10 == 0 ? currentPage - 9 : currentPage - (currentPage % 10) + 1;
		int endPage = startPage + 9 >= lastPage ? lastPage : startPage + 9;
		map.put("startNum", currentPage);
		
		List list = boardService.listPage(map);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("startPage", startPage);
		data.put("endPage", endPage);
		data.put("lastPage", lastPage);
		data.put("currentPage", currentPage);
		return data;
	}

	@RequestMapping(value = "/listPage*", method = RequestMethod.GET)
	public String getListPages(HttpSession session,
			@RequestParam(required = false, value = "currentPage", defaultValue = "1") long currentPage,
			@RequestParam(required = false, value = "genre") String listGenre, Model model, UserVO vo)
			throws Exception {
		Map map = new HashMap();
		map.put("listGenre", listGenre);
		int count = boardService.count(map);
		int lastPage = (int) Math.ceil((double) (count) / 10);
		if (currentPage > lastPage) {
			currentPage = lastPage;
			return "redirect:/board/listPage?currentPage=" + lastPage;
		}
		int currPage = pagingList(currentPage, lastPage, model);
		List selectGenreList = genreService.selectedGenreList();
		
		
		map.put("startNum", currPage);
		List list = boardService.listPage(map);
		List genreList = genreService.selectedGenreList();
		
		model.addAttribute("genreList", genreList);
		model.addAttribute("listPage", list);
		model.addAttribute("genre", listGenre);
		model.addAttribute("isAdmin", isAdmin);
		return "board/listPage";
	}
	
	// 모든 리스트 페이징 간소화용도
	public int pagingList(long currentPage, int lastPage, Model model) {
		int currPage = (int) currentPage; // 현재페이지
		currPage = currPage > lastPage ? lastPage : currPage; // 페이지 범위 벗어나면 마지막 페이지로 지정
		int startPage = currPage % 10 == 0 ? currPage - 9 : currPage - (currPage % 10) + 1; // 이전다음 버튼 눌렀을 때 이동페이지 뒷자리
		int endPage = startPage + 9 >= lastPage ? lastPage : startPage + 9;
		// 나열가능한 페이지 정하는 변수 : 10개 페이지 가능한데 마지막 게시물
		model.addAttribute("currentPage", currPage);
		model.addAttribute("startNum", startPage);
		model.addAttribute("endNum", endPage);
		model.addAttribute("lastPage", lastPage);
		return currPage;
	}
	

	public boolean isAdmin(HttpSession session) {
		UserVO vo = (UserVO) session.getAttribute("userVO");
		boolean isAdmin = vo.getStatus().equals("admin") ? true : false;
		return isAdmin;
	}

	// 게시물 검색
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String getSearch(@RequestParam(required = false, value = "currentPage", defaultValue = "1") int currentPage,
			Model model, HttpServletRequest req) throws Exception {
		Map map = search1(currentPage, req);
		List list = boardService.search(map);
		int searchCount = boardService.searchCount(map); // 검색한 내용물의 개수
		search2(searchCount, model, req, currentPage);
		List genreList = genreService.selectedGenreList();
		
		model.addAttribute("genreList", genreList);
		
		model.addAttribute("listPage", list);
		return "board/search";
	}

	// url 변수 변경 시 해당 정보가 존재하지 않으면 이동되는 페이지
	@RequestMapping(value = "/noSuchPage", method = RequestMethod.GET)
	public String getNoSuchPage(Model model) throws Exception {
		return "board/noSuchPage";
	}

	// 모든 search 페이지 간소화용도 1
	public Map search1(int currentPage, HttpServletRequest req) {
		String search = req.getParameter("search");
		String contain = req.getParameter("contain");
		Map map = new HashMap();
		map.put("search", search);
		map.put("contain", contain);
		map.put("startNum", currentPage);

		return map;
	}

	// 모든 search 페이지 간소화용도 2
	public void search2(int searchCount, Model model, HttpServletRequest req, int currentPage) {
		String search = req.getParameter("search");
		String contain = req.getParameter("contain");

		int startNum = currentPage % 10 == 0 ? currentPage - 9 : currentPage - (currentPage % 10) + 1;
		int lastPage = (int) Math.ceil((double) (searchCount) / 10);
		int endNum = startNum + 9 >= lastPage ? lastPage : startNum + 9;

		model.addAttribute("searchingWay", search);
		model.addAttribute("searchWord", contain);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startNum", startNum);
		model.addAttribute("endNum", endNum);
		model.addAttribute("lastPage", lastPage);
	}

}
