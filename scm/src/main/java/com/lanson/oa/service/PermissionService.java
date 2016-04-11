package com.lanson.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanson.oa.dao.PerGroupDAO;

@Service
public class PermissionService {
	@Autowired
	private  PerGroupDAO dao;
	
	
	public  boolean   selectPermission(int userId,int perId){
     	boolean	flag=true;
		int i=dao.selectPermission(userId, perId);
		if(i==0){
			return false;
		}
		return flag;
	}
	

}
