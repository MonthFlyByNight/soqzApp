package com.soqz.wap.system.dao;

import com.soqz.wap.system.core.QRcode;
import com.soqz.wap.system.core.QRcodeResult;

public interface QRcodeDao {

		
	/**
	 * ��ȡ��ά���ַ��Ϣ
	 * 
	 * ʱ��:2016-03-16
	 * 
	 * ����:HMH
	 * 
	 * @param 
	 * 
	 * @return
	 */
	public QRcodeResult QRcodeInfo(QRcode qRcode);
	
}
