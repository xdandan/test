package com.lanson.oa.dao;

import java.util.List;

import com.lanson.oa.pojo.SupUser;




public interface SupUserDAO {
	
	public SupUser selectSupUser(String username, String password);
	
	public  int    addUser(SupUser user);
	
	public SupUser selectUserBySupplyId(int supplyId);
	
	public SupUser selectUserByUsername(String username);
	
	public  int  updatePassowrdById(String password, int id);
	
	public   List<SupUser>  selectUsers();
	public   List<SupUser>  selectUserPermissionList();
	
	
	public SupUser selectUserById(int id);
	
	public  void updateById(SupUser user);
	
	public void insertPermission(int userId, int perId);
	
	public void updatePermission(int userId, int perId);
	
}
