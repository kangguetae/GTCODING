package com.parucnc.test_3.controller;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.service.UserServiceImpl;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Inject
	private UserServiceImpl service;
	
	@RequestMapping(value="/signUp", method = RequestMethod.GET)
	public String getSignUp(Model model) throws Exception{
		
		return "user/signUp";
	}
	
	@RequestMapping(value="/signUp", method = RequestMethod.POST)
	public String postSignUp(UserVO vo, Model model) throws Exception{
		
		if(vo.getId() == "" || vo.getPw() == "") {
			return "redirect:/user/signUp?blankErr=true";
		}
		else if(vo.getId().length() < 5) {
			return "redirect:/user/signUp?shortIdErr=true";
		}
		else if(vo.getPw().length() < 4) {
			return "redirect:/user/signUp?shortPwErr=true";
		}
		else if(!vo.getId().matches("^[a-zA-Z0-9]*$")||!vo.getPw().matches("^[a-zA-Z0-9]*$")) {
//			System.out.println(Pattern.matches("^[a-zA-Z]*$",vo.getId()));
//			System.out.println(vo.getPw().matches("^[a-zA -Z]*$"));
			return "redirect:/user/signUp?invalidIDErr=true";
			
		}
		try{
			service.insertUser(vo);
		}
		catch(Exception e) {
			System.out.println("asd");
			return "redirect:/user/signUp?existIdErr=true";
		}
		return "home";
	}
	
	@RequestMapping(value="/myPage", method=RequestMethod.GET)
	public String getMyPage() throws Exception{
		return "/user/myPage";
	}
	
}
