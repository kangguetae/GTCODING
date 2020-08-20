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
		List managerList = userService.managerList();
		
		model.addAttribute("managerList", managerList);
		model.addAttribute("genreList", genreList);
		model.addAttribute("list", list);
		return "management/adminPage";
	}
	
	@RequestMapping(value="/authority", method = RequestMethod.POST)
	public String postAuthority(HttpServletRequest request) throws Exception{
		String id = request.getParameter("user");
		userService.empowerment(id);
		return "redirect:/management/adminPage";
	}
	
	@RequestMapping(value="/deprivation", method = RequestMethod.POST)
	public String postDisprivation(HttpServletRequest request) throws Exception{
		String id = request.getParameter("manager");
		userService.deprivation(id);
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
		model.addAttribute("status", status);
		return status;
	}
	
	@RequestMapping(value="/addGenre", method = RequestMethod.GET)
	public String getAddGenre() throws Exception{
		return "management/addGenre";
	}
	
	@RequestMapping(value="/addGenre", method = RequestMethod.POST)
	public String postAddGenre(GenreVO vo, HttpServletRequest request) throws Exception{

		if(vo.getGenreEng().equals("") || vo.getGenreEng()==""||vo.getGenreEng().equals(null)
				||vo.getGenreEng()==null||vo.getGenreKor().equals("")||vo.getGenreKor()==""
				||vo.getGenreKor()==null||vo.getGenreKor().equals(null)) {
			return "redirect:/management/adminPage?genreNull=true";
		}
			
		
			
		genreService.addGenre(vo);
		return "redirect:/management/adminPage";
	}
	
	@RequestMapping(value="/deleteGenre", method = RequestMethod.POST)
	public String postDeleteGenre(HttpServletRequest request, GenreVO vo) throws Exception{
		String genre = request.getParameter("genreDelete");
		System.out.println(genre);
//		System.out.println(vo.getGenreEng());
		genreService.deleteGenre(genre);
//		boardService.delChoosedGenreBoard(genre);
		
		return "redirect:/management/adminPage";
	}
	
	@RequestMapping(value="/selectGenre", method=RequestMethod.POST)
	public String postSelectGenre(HttpServletRequest request) throws Exception{
		String [] checkList = request.getParameterValues("genres");
		for(String i : checkList) {
			System.out.println(i);	
		}
		genreService.genreSelectT(checkList);
		genreService.genreSelectF(checkList);
		
		
		return "redirect:/management/adminPage";
	}
}
