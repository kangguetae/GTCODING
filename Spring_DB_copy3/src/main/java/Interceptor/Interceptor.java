package Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.parucnc.test_3.domain.UserVO;

public class Interceptor extends HandlerInterceptorAdapter{

	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		throws Exception {
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		try {
			String status = userVO.getStatus();
			int s = userVO.getStatus().equals("admin")? 2 : 1;
			
			s = userVO.getStatus().equals("user")? 0 : s;
			request.setAttribute("status", s);
		}
		catch(Exception e) {
			response.sendRedirect("/loginError");
		}
	
		
		return true; //true면 controller로  요청 진행, false면 controller로 진행되지 않고 바로 응답하게 된다.
	}
	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView)
			throws Exception {
		
		try {
			
			System.out.println(request.getAttribute("status"));
			System.out.println("-------");
			int i = (Integer)request.getAttribute("status");
			modelAndView.addObject("status", i);
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
	}
}
