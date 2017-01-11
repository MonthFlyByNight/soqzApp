package com.soqz.wap.system.dao;

import com.soqz.wap.system.core.QRcode;
import com.soqz.wap.system.core.QRcodeResult;

public interface QRcodeDao {

		
	/**
	 * 获取二维码地址信息
	 * 
	 * 时间:2016-03-16
	 * 
	 * 作者:HMH
	 * 
	 * @param 
	 * 
	 * @return
	 */
	public QRcodeResult QRcodeInfo(QRcode qRcode);
	
}
