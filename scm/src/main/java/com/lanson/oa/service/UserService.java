package com.lanson.oa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanson.eoa.dao.SupplyItemDAO;
import com.lanson.eoa.dao.UserDAO;
import com.lanson.oa.dao.PerGroupDAO;
import com.lanson.oa.dao.SupUserDAO;
import com.lanson.oa.pojo.PerGroup;
import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.pojo.SupplyItem;

import net.sf.json.JSONArray;

@Service
public class UserService {
	@Autowired
	private SupplyItemDAO dao;
	@Autowired
	private SupUserDAO userDao;
	@Autowired
	private PerGroupDAO pDao;
	
	
	
	
	public  String supplyList(){
                List<SupplyItem> list= dao.selectSupply();
		   	    JSONArray  arr=JSONArray.fromObject(list);
		    	return arr.toString();
		}
	
	public String  permissionList(){
		List<PerGroup>  list=pDao.selectPerList();
		   JSONArray  arr=JSONArray.fromObject(list);
	    	return arr.toString();
	}
	
	
	
	@Transactional(value="loa",rollbackFor=Exception.class)
	public  int  addUser(SupUser user,int perId){
		int   flag;
		SupUser  user1=userDao.selectUserByUsername(user.getUsername());
		if(user1!=null){
			flag=3;
		}else{
			SupUser selectUser=null;
			if(user.getSupplyId()!=-1){
			 selectUser=  userDao.selectUserBySupplyId(user.getSupplyId());
			}
			if(selectUser!=null){
				flag=2;
			}else{
					userDao.addUser(user);
					userDao.insertPermission(user.getId(), perId);
					flag=1;
			}
		}
		return flag;
	}
	

	public int updatePassword(String oldPassword,String newPassword,SupUser user){
	  SupUser puser=userDao.selectSupUser(user.getUsername(), oldPassword);
			if(puser==null){
				return 2;
			}else{
			  return 	userDao.updatePassowrdById(newPassword, user.getId());
			}
	}
	
	public  String userList(){
		        List<SupUser> users=userDao.selectUserPermissionList();
		        JSONArray json=JSONArray.fromObject(users);
				return json.toString();
	}
	
	@Transactional(value="loa",rollbackFor=Exception.class)
	public int   userEdit(SupUser user){
		
		SupUser selectUser=userDao.selectUserById(user.getId());
		if(selectUser.getUsername().trim().equals(user.getUsername().trim())){
			userDao.updateById(user);
			userDao.updatePermission(user.getId(), user.getPerId());
		}else{
			SupUser selectUser1 =userDao.selectUserByUsername(user.getUsername());
			if(selectUser1==null){
				userDao.updateById(user);
				userDao.updatePermission(user.getId(), user.getPerId());
			}else{
				return 1;
			}
		}
		return 0;
	}
	
		
		
			
	}


