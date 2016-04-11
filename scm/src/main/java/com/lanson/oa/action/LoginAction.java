package com.lanson.oa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.LoginService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;

@Controller
public class LoginAction {
	@Autowired
	private  LoginService loginService;
	@Autowired
	private HttpSessionManage session;
	
	
	@RequestMapping(value="login.do",method=RequestMethod.GET)
	public  String relogin(){
		return "webpage/login/login.jsp";
	}
	
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	public  String loginVerify(HttpServletRequest request,HttpServletResponse response,String username,String password,ModelMap model){
		SupUser user=loginService.selectSupUser(username, password);
		if(user==null){
			String falseStr="’À∫≈ªÚ√‹¬Î¥ÌŒÛ";
			model.addAttribute("faile",falseStr);
			return "webpage/login/login.jsp";
		}
		session.setAttribute(request, response, Common.sessionUser, user);
		loginService.addAuth(request, response, user);
		return "index.html";
	}
	
	@RequestMapping(value="logout.do")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		session.loginout(request, response);
		return "webpage/login/login.jsp";
	}
 
}
