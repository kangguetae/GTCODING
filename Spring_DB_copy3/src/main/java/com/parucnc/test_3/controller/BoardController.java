package com.parucnc.test_3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.CommentVO;

import com.parucnc.test_3.service.BoardServiceImpl;
import com.parucnc.test_3.service.CommentServiceImpl;


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
	
	// 댓글 기능 추가 (로그인한사람이름으로) 예제x 시간되면 검색기능  ---- 댓글에도 페이징 (정말 시간 남으면) 
	
	// 목록 표시 (그냥 전체 게시글 나열)
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String getList(Model model, BoardVO vo) throws Exception{
		List list = boardService.list(vo);
		model.addAttribute("list", list);
		return "board/list";
	}
	
	// 게시물 작성 창으로 이동
	@RequestMapping(value="/write", method = RequestMethod.GET)
	public String getWrite(Model model) throws Exception{
		model.addAttribute("userID", homeCon.userID);
		
		
		return "board/write";
	}
	
	// 게시물 열람 + 조회수 증가 + 댓글 출력 
	@RequestMapping(value="/view", method = RequestMethod.GET)
	public String getView(@RequestParam("bno") long bno, CommentVO commVo, Model model) throws Exception{

		BoardVO vo; 
		try {
			if(bno > Integer.MAX_VALUE) {
				System.out.println("max보다 큰 long");
				return "redirect:/board/noSuchPage";
			}
			else {
				System.out.println("아님");
			}
			vo = boardService.view(bno);
			vo.getContent();
		}
		catch(Exception e) {
			return "redirect:/board/noSuchPage";
		}
		
		
		model.addAttribute("view", vo);
		boardService.viewUpdate(bno);
		
		List comment = commService.showComment(commVo);
				
		model.addAttribute("comment", comment);
		return "board/view";
	}
	
	// 댓글작성
	@RequestMapping(value="/view", method = RequestMethod.POST)
	public String postView(@RequestParam("bno")int bno, CommentVO vo, Model model) throws Exception{
		commService.insert(vo);
		return "redirect:/board/view?bno="+bno;
	}
	
	// 게시물 작성
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String postWrite(BoardVO vo, Model model) throws Exception{
//		System.out.println("5678");
		boardService.write(vo);
//		System.out.println("1234");
		return "redirect:/board/listPage";
	}
	
	// 게시물 삭제
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String postDelete(@RequestParam("bno") int bno, Model model) throws Exception{
		boardService.delete(bno);
		return "redirect:/board/listPage";
	}
	
	// 게시물 수정창으로 이동
	@RequestMapping(value="/modify", method = RequestMethod.GET)
	public String getModify(@RequestParam("bno")int bno, Model model) throws Exception{
		model.addAttribute("userID", homeCon.userID);
		BoardVO vo =boardService.view(bno);
		
		model.addAttribute("list", vo);
		return "board/modify";
	}
	
	// 게시물 수정
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo, Model model) throws Exception{
		boardService.update(vo);
		return "redirect:/board/listPage";
	}
	
	// 게시물 목록 (페이징) 
	@RequestMapping(value="/listPage", method = RequestMethod.GET)
	public String getListPage(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage, Model model) throws Exception{
		int count = boardService.count();
		int lastPage = (int) Math.ceil((double)(count)/10);
		if(currentPage > lastPage) {
			currentPage = lastPage;
			return "redirect:/board/listPage?currentPage="+lastPage;
		}
		int currPage = list(currentPage, lastPage, model);
		
		List list = boardService.listPage(currPage);
		model.addAttribute("listPage", list);

		return "board/listPage";
	}
	
//	@RequestMapping(value="/listPage*", method=RequestMethod.GET)
//	public String getListPages(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage,
//			@RequestParam(value = "genre")String listGenre, Model model) throws Exception{
//		System.out.println(listGenre);
//		int count = boardService.count(listGenre);
//		System.out.println(count);
//		int lastPage = (int) Math.ceil((double)(count)/10);
//		if(currentPage > lastPage) {
//			currentPage = lastPage;
//			return "redirect:/board/listPage?currentPage="+lastPage;
//		}
//		int currPage = list(currentPage, lastPage, model);
//		Map map = new HashMap();
//		map.put("listGenre", listGenre);
//		map.put("startNum", currPage);
//		
//		
//		List list = boardService.listPage(map);
//		model.addAttribute("listPage", list);
//		
//		model.addAttribute("genre", listGenre);
//		
//		return "board/listPage";
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	// 게시물 검색
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String getSearch(@RequestParam(required = false, value = "currentPage", defaultValue="1")int currentPage, Model model, HttpServletRequest req) throws Exception{
		Map map = search1(currentPage, req);
		List list = boardService.search(map);
		int searchCount = boardService.searchCount(map);
		search2(searchCount, model, req, currentPage);
		model.addAttribute("listPage", list);
		return "board/search";
	}
	
	// url 변수 변경 시 해당 정보가 존재하지 않으면 이동되는 페이지
	@RequestMapping(value="/noSuchPage", method=RequestMethod.GET)
	public String getNoSuchPage(Model model) throws Exception{
		return "board/noSuchPage";
	}
	
	@RequestMapping(value="/listPage_question", method=RequestMethod.GET)
	public String getListPage_Question(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage, Model model) throws Exception{
		int count = boardService.count_question();
		int lastPage = (int) Math.ceil((double)(count)/10);
		if(currentPage > lastPage) {
			currentPage = lastPage;
			return "redirect:/board/listPage_question?currentPage="+lastPage;
		}
		int currPage = list(currentPage, lastPage, model);
		
		List list = boardService.listPage_question(currPage);
		model.addAttribute("listPage", list);
		return "board/listPage_question";
	}
	
	@RequestMapping(value="/listPage_chat", method=RequestMethod.GET)
	public String getListPage_Chat(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage, Model model) throws Exception{
		int count = boardService.count_chat();
		int lastPage = (int) Math.ceil((double)(count)/10);
		if(currentPage > lastPage) {
			currentPage = lastPage;
			return "redirect:/board/listPage_chat?currentPage="+lastPage;
		}
		
		int currPage = list(currentPage, lastPage, model);
		
		List list = boardService.listPage_chat(currPage);
		model.addAttribute("listPage", list);

		
		
		return "board/listPage_chat";
	}

	@RequestMapping(value="/listPage_announcement", method=RequestMethod.GET)
	public String getListPage_Announcement(@RequestParam(required = false, value = "currentPage", defaultValue="1")long currentPage,Model model) throws Exception{
		int count = boardService.count_announcement();
		int lastPage = (int) Math.ceil((double)(count)/10);
		if(currentPage > lastPage) {
			currentPage = lastPage;
			return "redirect:/board/listPage_announcement?currentPage="+lastPage;
		}
		int currPage = list(currentPage, lastPage, model);
		
		List list = boardService.listPage_announcement(currPage);
		model.addAttribute("listPage", list);
		return "board/listPage_announcement";
	}
	
	//모든 리스트 페이징 간소화용도
	public int list(long currentPage, int lastPage, Model model) {
		
		int currPage = (int)currentPage; // 현재페이지
		currPage = currPage > lastPage ? lastPage : currPage; // 페이지 범위 벗어나면 마지막 페이지로 지정
		int startPage = currPage % 10 == 0  
				? currPage - 9 :currPage - (currPage % 10) + 1; // 이전다음 버튼 눌렀을 때 이동페이지 뒷자리 1페이지로 옮기기 위한 숫자
		int endPage = startPage + 9 >= lastPage ? lastPage : startPage + 9;
		//나열가능한 페이지 정하는 변수 : 10개 페이지 가능한데 마지막 게시물  
		model.addAttribute("currentPage", currPage);
		model.addAttribute("startNum", startPage);
		model.addAttribute("endNum", endPage);
		model.addAttribute("lastPage", lastPage);
		return currPage;
	}
	
	//모든 search 페이지 간소화용도 1
	public Map search1(int currentPage, HttpServletRequest req) {
		String search = req.getParameter("search");
		String contain = req.getParameter("contain");
		Map map = new HashMap();
		map.put("search", search);
		map.put("contain", contain);
		map.put("startNum", currentPage);

		return map;
	}
	//모든 search 페이지 간소화용도 2
	public void search2(int searchCount, Model model, HttpServletRequest req, int currentPage) {
		String search = req.getParameter("search");
		String contain = req.getParameter("contain");
		
		int startNum = currentPage % 10 == 0 ? currentPage - 9 :currentPage - (currentPage % 10) + 1;		
		int lastPage = (int) Math.ceil((double)(searchCount)/10);		
		int endNum = startNum + 9 >= lastPage ? lastPage : startNum + 9;
		
		model.addAttribute("searchingWay", search);
		model.addAttribute("searchWord", contain);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startNum", startNum);
		model.addAttribute("endNum", endNum);
		model.addAttribute("lastPage", lastPage);
	}
	
	
}
