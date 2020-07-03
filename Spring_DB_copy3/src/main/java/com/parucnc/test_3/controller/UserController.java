package com.parucnc.test_3.controller;

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
		try{
			System.out.println("123");
			service.insertUser(vo);
		}
		catch(Exception e) {
			System.out.println("asd");
			return "redirect:/user/signUp?existIdErr=true";
		}
		return "home";
	}
	
//	@RequestMapping(value="/list", method = RequestMethod.GET)
//	public String getList(Model model) throws Exception{
//		return "board/list";
//	}
//	
//	@RequestMapping(value="/write", method = RequestMethod.GET)
//	public String getWrite(Model model) throws Exception{
//		return "board/write";
//	}
	
	
//	@RequestMapping(value="/loginCheck", method=RequestMethod.POST)
//	public String postLogin(UserVO vo, Model model) throws Exception{
//		
//		int pw = service.loginCheck(vo);
//		
//		System.out.println(vo.getPw()+" "+pw);
//		
//		return "user/login";
//	}
}
