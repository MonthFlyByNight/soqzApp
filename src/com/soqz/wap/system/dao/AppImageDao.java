package com.soqz.wap.system.dao;

import com.soqz.wap.system.core.AppImage_info;

public interface AppImageDao {
	
	/**
	 * 获取app需要的图片
	 * 
	 * 时间:2016-02-01
	 * 
	 * 作者:HMH
	 * 
	 * @param system_info
	 * @return
	 */
	public String getAppImage(AppImage_info appImage_info);
	
}
