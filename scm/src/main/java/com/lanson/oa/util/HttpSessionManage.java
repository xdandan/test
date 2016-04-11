package com.lanson.oa.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpSessionManage {

	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Object value) {
		HttpSession session=request.getSession();
		session.setAttribute(name, value);
	}

	public void loginout(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session=request.getSession(false);
		if(session!=null){
			session.invalidate();
		}
	}

	public Object getAttribute(HttpServletRequest request, String name) {
		HttpSession session=request.getSession(false);
		if(session!=null){
		return	session.getAttribute(name);
		}
		return null;
	}
   
}
