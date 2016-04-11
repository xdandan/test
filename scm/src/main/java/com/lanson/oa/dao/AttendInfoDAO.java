package com.lanson.oa.dao;



import java.util.List;

import com.lanson.oa.pojo.AttendInfo;
import com.lanson.oa.pojo.AttendTime;

public interface AttendInfoDAO {
	/**
	 * 添加出勤信息
	 * @param attendInfo
	 * @return
	 */
	public int addInfo(AttendInfo attendInfo);
	/**
	 * 通过userid和日期查询是否存在
	 * @param attendInfo
	 * @return
	 */
    public  AttendInfo selectId(AttendInfo attendInfo);
}
