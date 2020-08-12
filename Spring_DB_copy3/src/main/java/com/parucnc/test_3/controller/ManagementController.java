package com.parucnc.test_3.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parucnc.test_3.domain.GenreVO;
import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.service.GenreServiceImpl;


@Controller
@RequestMapping("/management/*")
public class ManagementController {
	
	@Inject
	private GenreServiceImpl genreService;
	
	@RequestMapping(value="/adminPage", method = RequestMethod.GET)
	public String getManagement(HttpSession session, Model model) throws Exception{
		boolean isAdmin = isAdmin(session);
		model.addAttribute("isAdmin", isAdmin);
		return "management/adminPage";
	}
	
	public boolean isAdmin(HttpSession session) {
		UserVO vo = (UserVO)session.getAttribute("userVO");
		boolean isAdmin = vo.getStatus().equals("admin") ? true : false;
		return isAdmin;
	}
	
	@RequestMapping(value="/addGenre", method = RequestMethod.GET)
	public String getAddGenre() throws Exception{
		
		return "management/addGenre";
	}
	@RequestMapping(value="/addGenre", method = RequestMethod.POST)
	public String postAddGenre(GenreVO vo, HttpServletRequest request) throws Exception{
		String newGenre = request.getParameter("newGenre");
		genreService.addGenre(vo);
		
		return "redirect:/board/listPage";
	}
}
