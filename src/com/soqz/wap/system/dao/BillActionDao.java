package com.soqz.wap.system.dao;

import com.soqz.wap.system.core.BillLog;

public interface BillActionDao {
	
	/**
	 * ����������¼
	 * 
	 * ʱ��:2016-03-21
	 * 
	 * ����:HMH
	 * 
	 * @param billLog
	 * 
	 * @return
	 */
	public boolean billActionLog(BillLog billLog);
	
}
