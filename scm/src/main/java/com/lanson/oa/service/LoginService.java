package com.lanson.oa.service;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanson.oa.dao.AuthDAO;
import com.lanson.oa.dao.SupUserDAO;
import com.lanson.oa.pojo.Auth;
import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.CookieUtils;

@Service
public class LoginService {
	@Autowired
	private SupUserDAO dao;
	
	@Autowired
	private AuthDAO authDAO;
	
	/**
	 * 根据账号和密码确定是否存在
	 * @param username
	 * @param password
	 * @return
	 */
	public  SupUser selectSupUser(String username,String password){
		return dao.selectSupUser(username, password);
	}
  /**
   * 登陆成功，添加认证信息
   */
	public void addAuth(HttpServletRequest request,HttpServletResponse response,SupUser user){
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
        Auth auth=authDAO.selectAuthByUserId(user.getId());
        int i=0;
        if(auth==null){
        	auth=new Auth();
        	auth.setUserId(user.getId());
    		auth.setAuthId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
    		auth.setLoginTime(now);
    	    i=	authDAO.insertAuth(auth);
        }else{
          	auth.setUserId(user.getId());
    		auth.setAuthId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
    		auth.setLoginTime(now);
    	    i=	authDAO.updateByUserId(auth);
        }
		
	  if(i==1){
		CookieUtils.addCookie(request, response, Common.authId, auth.getAuthId(),Common.cookieTime,"");
			}	

	}
	
	/**
	 * 根据cookie中的认证ID查询信息
	 */
	public SupUser selectUserByAuthId(String authId){
	     Auth auth=  authDAO.selectAuthById(authId);
	     if(auth==null){
	    	 return null;
	     }
	    return	dao.selectUserById(auth.getUserId());
	}
	
	
	
}
