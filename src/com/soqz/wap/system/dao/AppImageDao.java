package com.soqz.wap.system.dao;

import com.soqz.wap.system.core.AppImage_info;

public interface AppImageDao {
	
	/**
	 * ��ȡapp��Ҫ��ͼƬ
	 * 
	 * ʱ��:2016-02-01
	 * 
	 * ����:HMH
	 * 
	 * @param system_info
	 * @return
	 */
	public String getAppImage(AppImage_info appImage_info);
	
}
