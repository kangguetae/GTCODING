package com.parucnc.test_3.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.parucnc.test_3.domain.UserVO;


@Controller
@RequestMapping("/management/*")
public class ManagementController {
	
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
}
