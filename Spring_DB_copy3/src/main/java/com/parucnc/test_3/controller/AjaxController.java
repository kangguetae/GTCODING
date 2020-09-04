package com.parucnc.test_3.controller;

import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.service.UserServiceImpl;

@Controller
@RequestMapping("/ajax/*")
public class AjaxController extends BoardController{
	
	@Inject
	BoardController b;
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String test()throws Exception{
		
		return "";
	}
	
}
