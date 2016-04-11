package com.lanson.scm.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.LoginService;
import com.lanson.oa.service.PermissionService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.CookieUtils;
import com.lanson.oa.util.HttpSessionManage;

public class ScmInterceptor extends   HandlerInterceptorAdapter{
	
	
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean flag=true;
		String uri=getURI(request);
	    SupUser user=	(SupUser) session.getAttribute(request, Common.sessionUser);
		if(isAllowURI(uri)){
			return flag;
		}else{
				if(user==null){
													//是否存在cookie
								   Cookie cookie=	CookieUtils.getCookie(request, Common.authId);
						      if(cookie!=null){
															String authId=cookie.getValue();
															//与数据库中的信息对应
																 user=	loginService.selectUserByAuthId(authId);
																if(user==null){
																	response.sendRedirect(getLoginURL(request));
																	flag= false;
																}else{
																session.setAttribute(request, response, Common.sessionUser, user);
																flag= true;
																}
														}else{
														response.sendRedirect(getLoginURL(request));
														flag=false;
														}
							           }
		        }
		//权限验证
		if(flag==true){
			if(uri.contains(adminPermission)){
				flag=perService.selectPermission(user.getId(), 1);
				if(!flag){
					response.sendRedirect(getNoPermissionURL(request));
				}
			}
			
		}
		
		
		return flag;
	}
	
	

	
	/**
	 * 获取/后的字符串
	 * @param request
	 * @return
	 */
		private String getURI(HttpServletRequest request) {
			UrlPathHelper helper = new UrlPathHelper();
			String uri = helper.getOriginatingRequestUri(request);
			String ctxPath = helper.getOriginatingContextPath(request);
			int start = 0, i = 0, count = 0;
			if (!StringUtils.isBlank(ctxPath)) {
				count++;
			}
			while (i < count && start != -1) {
				start = uri.indexOf('/', start + 1);
				i++;
			}
			return uri.substring(start);
		}
		/**
		 * 不需要验证的do请求
		 * @param uri
		 * @return
		 */
		private boolean isAllowURI(String uri) {
			if(allowUrls!=null){
				for(String allowUrl:allowUrls){
					if(uri.equals(allowUrl)){
						return true;
					}
				}
			}
			return false;
		}
		/**
		 * 返回登陆路径
		 * @param request
		 * @return
		 */
		private String getLoginURL(HttpServletRequest request) {
			StringBuilder buff = new StringBuilder();
			if (loginUrl.startsWith("/")) {
				String ctx = request.getContextPath();
				if (!StringUtils.isBlank(ctx)) {
					buff.append(ctx);
				}
			}
			buff.append(loginUrl);
			return buff.toString();
		}
		/**
		 * 返回403页面
		 * @param request
		 * @return
		 */
		private String getNoPermissionURL(HttpServletRequest request) {
			StringBuilder buff = new StringBuilder();
			if (noPermissionUrl.startsWith("/")) {
				String ctx = request.getContextPath();
				if (!StringUtils.isBlank(ctx)) {
					buff.append(ctx);
				}
			}
			buff.append(noPermissionUrl);
			return buff.toString();
		}
		
		
	public void postHandle(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			ModelAndView modelandview) throws Exception {
	}

	public void afterCompletion(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			Exception exception) throws Exception {
	}

	@Autowired
   private   LoginService loginService;
	@Autowired
	private PermissionService perService;
	
	
	private String[] allowUrls;

	private String loginUrl;
	
	private String noPermissionUrl;

   private String adminPermission;
	@Autowired
	private HttpSessionManage session;

	@Autowired
	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}
@Autowired
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
@Autowired
public void setNoPermissionUrl(String noPermissionUrl) {
	this.noPermissionUrl = noPermissionUrl;
}

@Autowired
public void setAdminPermission(String adminPermission) {
	this.adminPermission = adminPermission;
}

	
}
