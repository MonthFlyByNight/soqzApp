package com.soqz.wap.system.dao;


import com.soqz.wap.pcore.ProcessCore;
import com.soqz.wap.system.core.PhoneCode;

public interface PhoneCodeDao {
	
	/**
	 * ��ȡ��֤��
	 * 
	 * ʱ��:2015-11-09
	 * 
	 * ʱ��:2016-03-30�޸�
	 * 
	 * ����:HMH
	 * 
	 * @param phoneCode
	 * 
	 * @return
	 */
	public String getCode(PhoneCode phoneCode);
	
	/**
	 * ��֤��֤��
	 * 
	 * ʱ��:2015-11-09
	 * 
	 * ʱ��:2016-03-30�޸�
	 * 
	 * ����:HMH
	 * 
	 * @param phoneCode
	 * 
	 * @return
	 */
	public String verifyCode(PhoneCode phoneCode);
	
}
