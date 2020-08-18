package com.parucnc.test_3.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parucnc.test_3.domain.GenreVO;
import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.service.BoardServiceImpl;
import com.parucnc.test_3.service.GenreServiceImpl;
import com.parucnc.test_3.service.UserServiceImpl;


@Controller
@RequestMapping("/management/*")
public class ManagementController {
	
	@Inject
	private GenreServiceImpl genreService;
	@Inject
	private UserServiceImpl userService;
	@Inject
	private BoardServiceImpl boardService;
	
	@RequestMapping(value="/adminPage", method = RequestMethod.GET)
	public String getManagement(HttpSession session, Model model) throws Exception{
		int	isAdmin = isAdmin(session, model);
		if(isAdmin < 0) {
			return "home";
		}
		else if(isAdmin < 1) {
			return "redirect:/board/listPage";
		}
		List list = userService.userList();
		List genreList = genreService.genreList();
		model.addAttribute("genreList", genreList);
		model.addAttribute("list", list);
		return "management/adminPage";
	}
	
	@RequestMapping(value="/authority", method = RequestMethod.POST)
	public String post(HttpServletRequest request, Model model) throws Exception{
		String id = request.getParameter("user");
		userService.empowerment(id);
		System.out.println(id);
		return "redirect:/management/adminPage";
	}
		
	public int isAdmin(HttpSession session, Model model) {
		UserVO vo = (UserVO)session.getAttribute("userVO");
		int status;
		try {
			status = vo.getStatus().equals("admin")? 2 : 1;
			status = vo.getStatus().equals("user")? 0 : status;
		}
		catch (Exception e) {
			status = -1;
		}
//		int status2 = vo.getStatus().equals("manager")? status : 0;
//		boolean isAdmin = vo.getStatus().equals("admin") ? true : false;
		model.addAttribute("status", status);
		return status;
	}
	
	@RequestMapping(value="/addGenre", method = RequestMethod.GET)
	public String getAddGenre() throws Exception{
		return "management/addGenre";
	}
	
	@RequestMapping(value="/addGenre", method = RequestMethod.POST)
	public String postAddGenre(GenreVO vo, HttpServletRequest request) throws Exception{
		String newGenre = request.getParameter("newGenre");
		genreService.addGenre(vo);
		
		return "redirect:/management/adminPage";
	}
	
	@RequestMapping(value="/deleteGenre", method = RequestMethod.POST)
	public String postDeleteGenre(HttpServletRequest request, GenreVO vo) throws Exception{
		//genreService.deleteGenre(vo);
		String genre = request.getParameter("genreDelete");
		boardService.delChoosedGenreBoard(genre);
		
		return "redirect:/management/adminPage";
	}
}
