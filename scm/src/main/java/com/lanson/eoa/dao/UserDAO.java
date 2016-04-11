package com.lanson.eoa.dao;


import com.lanson.oa.pojo.User;

public interface UserDAO {

	/**
	 * OA中的编号
	 * @param workCode
	 * @return
	 */
	 int selectIdByWorkCode(String workCode);

	User selectUserDept(int userId);
	
}
