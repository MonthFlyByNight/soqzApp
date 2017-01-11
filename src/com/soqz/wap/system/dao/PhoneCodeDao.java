package com.soqz.wap.system.dao;


import com.soqz.wap.pcore.ProcessCore;
import com.soqz.wap.system.core.PhoneCode;

public interface PhoneCodeDao {
	
	/**
	 * 获取验证码
	 * 
	 * 时间:2015-11-09
	 * 
	 * 时间:2016-03-30修改
	 * 
	 * 作者:HMH
	 * 
	 * @param phoneCode
	 * 
	 * @return
	 */
	public String getCode(PhoneCode phoneCode);
	
	/**
	 * 验证验证码
	 * 
	 * 时间:2015-11-09
	 * 
	 * 时间:2016-03-30修改
	 * 
	 * 作者:HMH
	 * 
	 * @param phoneCode
	 * 
	 * @return
	 */
	public String verifyCode(PhoneCode phoneCode);
	
}
