package com.lanson.oa.dao;



import java.util.List;

import com.lanson.oa.pojo.AttendInfo;
import com.lanson.oa.pojo.AttendTime;

public interface AttendInfoDAO {
	/**
	 * ��ӳ�����Ϣ
	 * @param attendInfo
	 * @return
	 */
	public int addInfo(AttendInfo attendInfo);
	/**
	 * ͨ��userid�����ڲ�ѯ�Ƿ����
	 * @param attendInfo
	 * @return
	 */
    public  AttendInfo selectId(AttendInfo attendInfo);
}
