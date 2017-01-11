package com.soqz.wap.system.dao;

import com.soqz.wap.system.core.BillLog;

public interface BillActionDao {
	
	/**
	 * 订单操作记录
	 * 
	 * 时间:2016-03-21
	 * 
	 * 作者:HMH
	 * 
	 * @param billLog
	 * 
	 * @return
	 */
	public boolean billActionLog(BillLog billLog);
	
}
