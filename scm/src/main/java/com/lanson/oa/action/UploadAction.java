package com.lanson.oa.action;

import java.io.File;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lanson.oa.service.UploadService;



@Controller
public class UploadAction {
	@Autowired
	private  UploadService uploadService;
	
	
	
	
	@RequestMapping(value="file.do",method=RequestMethod.GET)
	public String index(){
		return "upload.html";
	}
	
	
	
	
	
	@RequestMapping(value="upload.do",method=RequestMethod.POST)
    public void uploadFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
  
		DiskFileItemFactory factory = 
			new DiskFileItemFactory();
		ServletFileUpload sfu = 
			new ServletFileUpload(factory);
		List<FileItem> items =
			sfu.parseRequest(request);
		for(int i=0;i<items.size();i++){
			FileItem curr = items.get(i);
			if(curr.isFormField()){
				//
			}else{
				String path = request.getSession().getServletContext().getRealPath("upload");
				String fileName = curr.getName();
				//��ĳЩ����ϵͳ�ϣ�fileName�������·��
				fileName = fileName.substring(
						fileName.lastIndexOf("\\") + 1);
				//������·��
				String  servicePath=path + "\\"+ "" + fileName;
				
				curr.write(new File(servicePath));
				//�����ϴ����ļ�
				uploadService.anlyExcel(servicePath);
				
				
				
				//���⣬��Ҫ���ϴ�����Ƭ�ļ����Ʊ��浽���ݿ�
				response.setContentType("text/HTML;charset=UTF-8");//
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			response.getWriter().write("{\"success\":true,\"fileName\":\""+fileName+"\"}");//
		   }
		}
}
}
