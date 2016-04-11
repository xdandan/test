package com.lanson.oa.action.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.UserService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;

@Controller
public class UserAction {
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private HttpSessionManage session;
	
	@RequestMapping(value="set/add.do")
	private  String setUser(){
		return "webpage/set/user_add.jsp";
	}
  
	@RequestMapping(value="user/supply.do")
    public  void supplyList(HttpServletResponse response) throws IOException{
    	response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//{count:2,root:[{id:1,orderNo:'教师录用公示 '},{id:2,orderNo:'教师录用公示 '},{id:3,orderNo:'教师录用公示 '},{id:4,orderNo:'教师录用公示 '}]}
	    response.getWriter().write(userService.supplyList());
    }
	@RequestMapping(value="user/permissed.do")
    public  void permissionList(HttpServletResponse response) throws IOException{
    	response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//{count:2,root:[{id:1,orderNo:'教师录用公示 '},{id:2,orderNo:'教师录用公示 '},{id:3,orderNo:'教师录用公示 '},{id:4,orderNo:'教师录用公示 '}]}
	    response.getWriter().write(userService.permissionList());
    }
	
	@RequestMapping(value="user/add.do",method=RequestMethod.POST)
	public  void  useradd(HttpServletResponse response,SupUser user,int perId) throws IOException{
	
		int  status = 0;
		status=userService.addUser(user,perId);
		
    	response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	    response.getWriter().write("{\"success\":true,\"msg\":"+status+"}");

	}
	

	
	@RequestMapping(value="index/password.do")
	public String  password(){
		return "/webpage/index/change_password.jsp";
	}
	
	@RequestMapping(value="password/chang.do")
	public void  changePassword(HttpServletRequest request,HttpServletResponse  response,String oldPassword,String newPassword) throws IOException{
	 SupUser user=	(SupUser) session.getAttribute(request, Common.sessionUser);
	  int status=userService.updatePassword(oldPassword,newPassword,user);
	  	response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	    response.getWriter().write("{\"success\":true,\"msg\":"+status+"}");
        }
	
	@RequestMapping(value="set/user_manage.do")
	public String  userManage(){
		return "webpage/set/user_manage.jsp";
        }
	
	/**
	 * 编辑用户信息
	 * @param response
	 * @param user
	 * @throws IOException
	 */
	@RequestMapping(value="user/edit.do")
	public  void  userEdit(HttpServletResponse response,SupUser user) throws IOException{
    	response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	    response.getWriter().write("{\"success\":true,\"msg\":"+userService.userEdit(user)+"}");

	}
	/**
	 * 向页面发送json用户列表
	 * @param response
	 * @param orderNo
	 * @param start
	 * @param limit
	 * @throws IOException
	 */
	 @RequestMapping(value="user/userlist.do")
	    public  void userList(HttpServletResponse response) throws IOException{
	    	response.setContentType("application/x-json;charset=UTF-8");//
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		    response.getWriter().write(userService.userList());
	    }
	
	
	
	
}