package com.parucnc.test_3.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.CommentVO;
import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.domain.User_BoardVO;
import com.parucnc.test_3.service.BoardServiceImpl;
import com.parucnc.test_3.service.CommentServiceImpl;
import com.parucnc.test_3.service.User_BoardServiceImpl;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	private HomeController homeCon;
	@Inject
	private CommentServiceImpl commService;
	@Inject
	private BoardServiceImpl boardService;
	@Inject
	private User_BoardServiceImpl u_bService; 

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
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter writer = response.getWriter();
//            writer.println("<script>alert('로그인이 필요한 서비스입니다.'); </script>");
//            writer.flush();
//            writer.close();
			System.out.println("로그인이 필요한 서비스입니다.");
			return "redirect:/";
		}
		model.addAttribute("userID", id);

		return "board/write";
	}

	@RequestMapping(value = "/likeOrDislike", method = RequestMethod.POST)
	public String postLikeOrDislike(HttpSession session, HttpServletResponse response, 
			HttpServletRequest request, Model model) throws Exception {

		int bno = Integer.parseInt(request.getParameter("bNumber"));
		int lOrDl = Integer.parseInt(request.getParameter("likeOrDislike"));
//		Map map = new HashMap();
//		map.put("lOrDl", lOrDl);
//		map.put("bno", bno);
//		boardService.likeOrDislike(map);
		UserVO userInfo = (UserVO) session.getAttribute("userVO");
//		System.out.println("uno= "+userVO.getUno());		
		Map testMap = new HashMap();
		testMap.put("bno", bno);
		
		
		
		testMap.put("isLike", lOrDl);
		testMap.put("uno", userInfo.getUno());
		User_BoardVO check = u_bService.check_clickedBefore(testMap);
//		List list = u_bService.check_clickedBefore(testMap);
//		if(list.size()==0) {
		
		
		if(check == null) {
			u_bService.u_bMapping(testMap); // u_b테이블에 버튼누른사람 아이디랑 글번호 추가
		}
		
//		else { //추|비추버튼을 이미 누름
//			int which_btn = check.getIsLike(); // 0=dislike 1=like 이전에 누른 버튼이 무슨 버튼인지
//			if(which_btn == isLike) {
//				Map mapForDelete = new HashMap();
//				mapForDelete.put("bno", bno);
//				mapForDelete.put("uno", userInfo.getUno());
//				u_bService.recommDelete(mapForDelete);
//				
//				
//				
//				//u_bmapping 해당 데이터 삭제 
//				// + islike 0이면 dislikecnt-- 1이면 likecnt--
//				System.out.println("같은버튼");
//			}
//			else {
//				System.out.println("다른버튼");
//			}
//			
//			System.out.println(which_btn);
//			
//			/*
//			 * 같은버튼 누르면 추천|비추천 취소
//			 * isLike 확인하고 check안에있는 getIsLike비교해서 같으면 누른버튼cnt--
//			 * 다르면 그버튼 ++ 다른버튼 --하면 되겠네
//			 */
//		}
		
//		try {
//			String cookieNickname = "";
//			
//			Cookie[] cookies = request.getCookies();
//			for (Cookie c : cookies) {
//				if (c.getName().equals("nickname")) {
//					cookieNickname = c.getValue();
//					break;
//				}
//			}
//			
//			
//			String sessionNickname = userVO.getNickname();
//			System.out.println("쿠키: "+cookieNickname);
//			System.out.println("세션: "+sessionNickname);
//			
//			if (!cookieNickname.equals(sessionNickname)) {
//				System.out.println("if");
//				Cookie cookie;
//				cookie = new Cookie("nickname", userVO.getNickname());
//				cookie.setMaxAge(60*60*24);
//				cookie.setPath("/board/view?bno="+bno);
//				System.out.println(cookie.getPath());
//				response.addCookie(cookie);
////			 //UI - 
////		    model.addAttribute("cookie",cookie);
////			
////		    logger.info(cookie.getValue());
////		    
////			
////			this.userID = userVO.getId();
//
////				String lOrDl = request.getParameter("likeOrDislike");
////				Map map = new HashMap();
////				map.put("lOrDl", lOrDl);
////				map.put("bno", bno);
////				boardService.likeOrDislike(map);
//			}
//			else {
//				System.out.println("이미 참여하였습니다.");
//			}
//		} catch (Exception e) {
//			System.out.println("catch");
//		}

//		System.out.println(gb);
		return "redirect:/board/view?bno=" + bno;
	}

	// 게시물 열람 + 조회수 증가 + 댓글 출력
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String getView(HttpServletRequest request, HttpSession session, @RequestParam("bno") long bno,
			CommentVO commVo, Model model) throws Exception {
//		logger.info(((UserVO)session.getAttribute("userVO")).getId());
		String id = null;
		boolean login = false;
		if ((UserVO) session.getAttribute("userVO") != null) {
			login = true;
		}
		model.addAttribute("isLogin", login);
//		try {
		id = getId(session);
//		} catch (Exception e) {
//			System.out.println("로그인이 필요한 서비스입니다.");
//			return "/";
//		}
		BoardVO vo;
		try {
			if (bno > Integer.MAX_VALUE) {
				System.out.println("max보다 큰 long");
				return "redirect:/board/noSuchPage";
			}
			vo = boardService.view(bno);
			vo.getContent();
		} catch (Exception e) {
			return "redirect:/board/noSuchPage";
		}
//		System.out.println(vo.getLikeCnt());
		
		UserVO userInfo = (UserVO) session.getAttribute("userVO");
		boolean alreadyParticipated = false;
		
		Map testMap = new HashMap();
		testMap.put("bno", bno);
		testMap.put("uno", userInfo.getUno());
		User_BoardVO check = u_bService.check_clickedBefore(testMap);
		if(check != null) {
			alreadyParticipated = true;
		}
		
		int countDislike = u_bService.countDislike(testMap);
		int countLike = u_bService.countLike(testMap);
		System.out.println(countLike+" | "+countDislike);
		
		model.addAttribute("countLike", countLike);
		model.addAttribute("countDislike", countDislike);
		model.addAttribute("alreadyParticipated", alreadyParticipated);
		System.out.println(alreadyParticipated);
		model.addAttribute("userId", id);
		model.addAttribute("view", vo);
		
		boardService.viewUpdate(bno);

		List comment = commService.showComment(commVo);

		model.addAttribute("comment", comment);
		return "board/view";
	}

	// 댓글작성
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public String postView(@RequestParam("bno") int bno, CommentVO vo, Model model) throws Exception {
		commService.insert(vo);
		return "redirect:/board/view?bno=" + bno;
	}

	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(BoardVO vo, Model model) throws Exception {
//		System.out.println("5678");
		boardService.write(vo);
//		System.out.println("1234");
		return "redirect:/board/listPage";
	}

	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String postDelete(@RequestParam("bno") int bno, Model model) throws Exception {
		boardService.delete(bno);
		return "redirect:/board/listPage";
	}

	// 게시물 수정창으로 이동
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String getModify(@RequestParam("bno") int bno, Model model) throws Exception {
		model.addAttribute("userID", homeCon.userID);
		BoardVO vo = boardService.view(bno);

		model.addAttribute("list", vo);
		return "board/modify";
	}

	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo, Model model) throws Exception {
		boardService.update(vo);
		return "redirect:/board/listPage";
	}

	// 게시물 목록 (페이징)
//	@RequestMapping(value="/listPage", method = RequestMethod.GET)
//	public String getListPage(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage, Model model) throws Exception{
//		int count = boardService.count();
//		int lastPage = (int) Math.ceil((double)(count)/10);
//		if(currentPage > lastPage) {
//			currentPage = lastPage;
//			return "redirect:/board/listPage?currentPage="+lastPage;
//		}
//		int currPage = list(currentPage, lastPage, model);
//		
//		List list = boardService.listPage(currPage);
//		model.addAttribute("listPage", list);
//
//		return "board/listPage";
//	}
	public String getId(HttpSession session) throws Exception {
		UserVO vo = (UserVO) session.getAttribute("userVO");
		String id = null;
		try {
			id = vo.getId();
		} catch (Exception e) {
			
		}
		return id;
	}

	@RequestMapping(value = "/listPage*", method = RequestMethod.GET)
	public String getListPages(HttpSession session,
			@RequestParam(required = false, value = "currentPage", defaultValue = "1") long currentPage,
			@RequestParam(required = false, value = "genre") String listGenre, Model model, HttpServletRequest request)
			throws Exception {
		String id = null;
		try {
			id = getId(session);
		} catch (Exception e) {
			

		}
		
//		System.out.println(listGenre);
		Map map = new HashMap();
		map.put("listGenre", listGenre);
		int count = boardService.count(map);
//		System.out.println(count);
		int lastPage = (int) Math.ceil((double) (count) / 10);
		if (currentPage > lastPage) {
			currentPage = lastPage;
			return "redirect:/board/listPage?currentPage=" + lastPage;
		}
//		System.out.println("currentage= "+currentPage+" lastPage= "+lastPage);
		int currPage = list(currentPage, lastPage, model);

		map.put("startNum", currPage);

		List list = boardService.listPage(map);
//		System.out.println(list.size());
		model.addAttribute("listPage", list);

		model.addAttribute("genre", listGenre);

		return "board/listPage";

	}

	// 게시물 검색
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String getSearch(@RequestParam(required = false, value = "currentPage", defaultValue = "1") int currentPage,
			Model model, HttpServletRequest req) throws Exception {
		Map map = search1(currentPage, req);
		List list = boardService.search(map);
		int searchCount = boardService.searchCount(map);
		search2(searchCount, model, req, currentPage);
		model.addAttribute("listPage", list);
		return "board/search";
	}

	// url 변수 변경 시 해당 정보가 존재하지 않으면 이동되는 페이지
	@RequestMapping(value = "/noSuchPage", method = RequestMethod.GET)
	public String getNoSuchPage(Model model) throws Exception {
		return "board/noSuchPage";
	}

//	@RequestMapping(value="/listPage_question", method=RequestMethod.GET)
//	public String getListPage_Question(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage, Model model) throws Exception{
//		int count = boardService.count_question();
//		int lastPage = (int) Math.ceil((double)(count)/10);
//		if(currentPage > lastPage) {
//			currentPage = lastPage;
//			return "redirect:/board/listPage_question?currentPage="+lastPage;
//		}
//		int currPage = list(currentPage, lastPage, model);
//		
//		List list = boardService.listPage_question(currPage);
//		model.addAttribute("listPage", list);
//		return "board/listPage_question";
//	}
//	
//	@RequestMapping(value="/listPage_chat", method=RequestMethod.GET)
//	public String getListPage_Chat(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage, Model model) throws Exception{
//		int count = boardService.count_chat();
//		int lastPage = (int) Math.ceil((double)(count)/10);
//		if(currentPage > lastPage) {
//			currentPage = lastPage;
//			return "redirect:/board/listPage_chat?currentPage="+lastPage;
//		}
//		
//		int currPage = list(currentPage, lastPage, model);
//		
//		List list = boardService.listPage_chat(currPage);
//		model.addAttribute("listPage", list);
//
//		
//		
//		return "board/listPage_chat";
//	}
//
//	@RequestMapping(value="/listPage_announcement", method=RequestMethod.GET)
//	public String getListPage_Announcement(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage,Model model) throws Exception{
//		int count = boardService.count_announcement();
//		int lastPage = (int) Math.ceil((double)(count)/10);
//		if(currentPage > lastPage) {
//			currentPage = lastPage;
//			return "redirect:/board/listPage_announcement?currentPage="+lastPage;
//		}
//		int currPage = list(currentPage, lastPage, model);
//		
//		List list = boardService.listPage_announcement(currPage);
//		model.addAttribute("listPage", list);
//		return "board/listPage_announcement";
//	}

	// 모든 리스트 페이징 간소화용도
	public int list(long currentPage, int lastPage, Model model) {

		int currPage = (int) currentPage; // 현재페이지
		currPage = currPage > lastPage ? lastPage : currPage; // 페이지 범위 벗어나면 마지막 페이지로 지정
		int startPage = currPage % 10 == 0 ? currPage - 9 : currPage - (currPage % 10) + 1; // 이전다음 버튼 눌렀을 때 이동페이지 뒷자리
																							// 1페이지로 옮기기 위한 숫자
		int endPage = startPage + 9 >= lastPage ? lastPage : startPage + 9;
		// 나열가능한 페이지 정하는 변수 : 10개 페이지 가능한데 마지막 게시물
		model.addAttribute("currentPage", currPage);
		model.addAttribute("startNum", startPage);
		model.addAttribute("endNum", endPage);
		model.addAttribute("lastPage", lastPage);
		return currPage;
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
