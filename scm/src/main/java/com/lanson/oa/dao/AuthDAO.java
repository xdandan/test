package com.lanson.oa.dao;


import com.lanson.oa.pojo.Auth;




public interface AuthDAO {
	
   int insertAuth(Auth auth);

    /**
     * @param authId
     * @return
     */
   Auth   selectAuthById(String authId);
   int   updateByUserId(Auth auth);
    Auth  selectAuthByUserId(int userId);
	
}
