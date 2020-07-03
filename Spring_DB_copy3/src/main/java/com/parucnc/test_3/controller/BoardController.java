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
import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.service.BoardService;
import com.parucnc.test_3.service.BoardServiceImpl;
import com.parucnc.test_3.service.CommentServiceImpl;
import com.parucnc.test_3.service.UserService;
import com.parucnc.test_3.service.UserServiceImpl;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private CommentServiceImpl commService; 
	@Inject
	private BoardServiceImpl boardService;
	
	// 댓글 기능 추가 (로그인한사람이름으로) 예제x 시간되면 검색기능  ---- 댓글에도 페이징 (정말 시간 남으면) 
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String getList(Model model, BoardVO vo) throws Exception{
		List list = boardService.list(vo);
		model.addAttribute("list", list);
		return "board/list";
	}
	
	@RequestMapping(value="/write", method = RequestMethod.GET)
	public String getWrite(Model model) throws Exception{
		return "board/write";
	}
	
	
	@RequestMapping(value="/view", method = RequestMethod.GET)
	public String getView(@RequestParam("bno") int bno, CommentVO commVo, Model model) throws Exception{
		BoardVO vo = boardService.view(bno);
		model.addAttribute("view", vo);
		boardService.viewUpdate(bno);
		List comment = commService.showComment(commVo);
		for(int i = 0; i < comment.size(); i++) {
			System.out.println(comment.get(i));
		}
		model.addAttribute("comment", comment);
		return "board/view";
	}
	@RequestMapping(value="/view", method = RequestMethod.POST)
	public String postView(@RequestParam("bno")int bno, CommentVO vo, Model model) throws Exception{
		commService.insert(vo);
		return "redirect:/board/view?bno="+bno;
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String postWrite(BoardVO vo, Model model) throws Exception{
//		System.out.println("5678");
		boardService.write(vo);
//		System.out.println("1234");
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String postDelete(@RequestParam("bno") int bno, Model model) throws Exception{
		boardService.delete(bno);
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.GET)
	public String getModify(@RequestParam("bno")int bno, Model model) throws Exception{
		BoardVO vo =boardService.view(bno);
		
		model.addAttribute("list", vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo, Model model) throws Exception{
		boardService.update(vo);
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value="/listPage", method = RequestMethod.GET)
	public String getListPage(@RequestParam(required = false, value = "currentPage", defaultValue="1")int currentPage, Model model) throws Exception{
		
		int count = boardService.count();
		
		
		int startNum = currentPage % 10 == 0 ? currentPage - 9 :currentPage - (currentPage % 10) + 1;
		
		int lastPage = (int) Math.ceil((double)(count)/10);
		
		int endNum = startNum + 9 >= lastPage ? lastPage : startNum + 9;

//		System.out.println(startNum);
//		System.out.println(lastPage);
//		System.out.println("-----------");
		List list = boardService.listPage(currentPage);
		model.addAttribute("listPage", list);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startNum", startNum);
		model.addAttribute("endNum", endNum);
		model.addAttribute("lastPage", lastPage);
		
		
		
		return "board/listPage";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String postListPage(@RequestParam(required = false, value = "currentPage", defaultValue="1")int currentPage, Model model, HttpServletRequest req) throws Exception{
		String search = req.getParameter("search");
		String contain = req.getParameter("contain");
		Map map = new HashMap();
		map.put("search", search);
		map.put("contain", contain);
		map.put("startNum", currentPage);
		
		int searchCount = boardService.searchCount(map);
		int startNum = currentPage % 10 == 0 ? currentPage - 9 :currentPage - (currentPage % 10) + 1;		
		int lastPage = (int) Math.ceil((double)(searchCount)/10);		
		int endNum = startNum + 9 >= lastPage ? lastPage : startNum + 9;
		
		List list  = boardService.search(map);

		model.addAttribute("listPage", list);
		model.addAttribute("searchingWay", search);
		model.addAttribute("searchWord", contain);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startNum", startNum);
		model.addAttribute("endNum", endNum);
		model.addAttribute("lastPage", lastPage);
		return "board/search";
	}
	
//	@RequestMapping(value="/search", method=RequestMethod.GET)
//	public String getSearch(Model model) throws Exception{
//		
//		logger.info("123");
//		
//		
//		return "board/search";
//	}
}
