package com.parucnc.test_3.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.service.BoardService;
import com.parucnc.test_3.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Inject
	private UserService service;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postLogin(UserVO vo, Model model) throws Exception {

		UserVO userVO = service.loginCheck(vo);

		try {
			userVO.getId();
		} catch (Exception e) {
			return "redirect:/?idErr=true";
		}

		if (vo.getPw().equals(userVO.getPw())) {
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
