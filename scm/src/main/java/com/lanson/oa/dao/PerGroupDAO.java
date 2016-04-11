package com.lanson.oa.dao;

import java.util.List;

import com.lanson.oa.pojo.PerGroup;






public interface PerGroupDAO {
	
public int selectPermission(int userId, int perId);

public  List<PerGroup>   selectPerList();

}
