package com.lanson.oa.dao;



import java.util.List;

import com.lanson.oa.pojo.AttendTime;

public interface AttendTimeDAO {
	
	
	/**
	 * 添加出勤时间和信息外键
	 * @param list
	 * @return
	 */
    public int batchAddTime(List<AttendTime> list);
    
    
    public int selectTime(int attendId, String time);
}
