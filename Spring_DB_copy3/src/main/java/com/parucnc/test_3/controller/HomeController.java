package com.parucnc.test_3.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.service.BoardService;
import com.parucnc.test_3.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	public static String userID;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Inject
	private UserService service;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request,  HttpServletResponse response, @RequestParam(required=false, value="logOut", defaultValue="0")String logOut,Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Cookie[] cookies = request.getCookies();
		if(logOut.equals("1")) {
			Cookie cookie = new Cookie("id", null);
			response.addCookie(cookie);
		}
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, UserVO vo, Model model) throws Exception {

		UserVO userVO = service.loginCheck(vo);
		
			
		
		
//		Cookie [] cookie = request.getCookies();
//		String id = null;
//		boolean find = false;
		
		
		Cookie cookie;
		
		
		
		try {
			userVO.getId();
		} catch (Exception e) {
			return "redirect:/?idErr=true";
		}

		if (vo.getPw().equals(userVO.getPw())) {
			
			cookie = new Cookie("id", userVO.getId());
			cookie.setMaxAge(60*20);
			cookie.setPath("/");
			response.addCookie(cookie);
			 
		    model.addAttribute("cookie",cookie);
			
		    logger.info(cookie.getValue());
		    
			
			this.userID = userVO.getId();
			session.setAttribute("userVO", userVO);
			
			return "user/login";
		}

		return "redirect:/?pwErr=true";
	}

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	 public String getList(Model model) throws Exception {
//	  
//	  List list1 = null;
//	  list1 = service.list();
//	  
//	  System.out.println(list1);
//	  model.addAttribute("list1", list1);    // 내일 와서 시도 ㄱㄱ
//	  
//	  return "home";
//	 }

}
