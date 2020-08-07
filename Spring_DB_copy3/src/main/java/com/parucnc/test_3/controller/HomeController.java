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
	@RequestMapping(value = {"/", "/loginError"}, method = RequestMethod.GET)
	public String home(@CookieValue(value = "remID", required = false) Cookie remID, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(required = false, value = "logOut", defaultValue = "0") String logOut, Locale locale,
			Model model) throws Exception{
		if(request.getRequestURI().equals("/loginError")) {
			//loginError로 들어왔을 경우
		}
		boolean checkBoxIsClicked = false;
		
		if(remID != null) {
			int uNum = Integer.parseInt(remID.getValue());
			System.out.println(remID.getValue());
			UserVO vo = service.remID(uNum);
			model.addAttribute("vo", vo);
			checkBoxIsClicked = true;
			
		}
		
		model.addAttribute("checked", checkBoxIsClicked);
		
		try {
			UserVO userVO = (UserVO)session.getAttribute("userVO");
			if(userVO != null) {
				return "user/login";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}
	
	/* 로그아웃 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(HttpSession session, Model model) throws Exception {
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postLogin(@CookieValue(value = "remID", required = false) Cookie remID,HttpServletRequest request, HttpServletResponse response, HttpSession session, UserVO vo,
			Model model) throws Exception {
		
		UserVO userVO = service.loginCheck(vo);
		boolean isAdmin = userVO.getStatus().equals("admin") ? true : false;
//		Cookie [] cookie = request.getCookies();
//		String id = null;
//		boolean find = false;
		
		String checkBox = request.getParameter("check");
		Cookie cookie;
		System.out.println(checkBox);
		try {
			userVO.getId();
		} catch (Exception e) {
			return "redirect:/?idErr=true";
		}

		if (vo.getPw().equals(userVO.getPw())) {
			if(checkBox != null) {
				String uno = ""+userVO.getUno();
				cookie = new Cookie("remID", uno);
				cookie.setMaxAge(60*60*24*30); // 30일
				cookie.setPath("/");
				response.addCookie(cookie);
			    model.addAttribute("cookie",cookie);
			}
			else {
				if(remID != null) {
					cookie = new Cookie("remID", "");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
//		    logger.info(cookie.getValue());
		    
			
//			this.userID = userVO.getId();
			model.addAttribute("isAdmin", isAdmin);
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
