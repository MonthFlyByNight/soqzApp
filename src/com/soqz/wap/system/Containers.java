package com.soqz.wap.system;

import com.soqz.wap.pcore.ProcessCore;
import com.soqz.wap.system.dao.ContainerDao;
import com.soqz.wap.system.dao.impl.ContainerDaoImpl;

public class Containers {
	
	ContainerDao ContainerDao = new ContainerDaoImpl();
	
	/**
	 * 检测当前ssuid是否可用
	 * 
	 * @param ssuid
	 * 
	 * @param type
	 * 
	 * @return boolean true存在;false不存在
	 */
	public  boolean checkUid(Long ssuid,String type){
		
//		boolean b = ContainerDao.chenckUid(ssuid, type);
		
		return false;
		
	}
	
	/**
	 * 提示信息
	 * @return
	 */
	public static String returnInfo(){

		return "{\"state\":\"false\",\"result\":\"请返回登录\"}";
		
	}
	
	/**
	 * 根据时间戳获取id
	 * 
	 * 时间:2015-05-25
	 * 
	 * 作者:HMH
	 * 
	 * @param ssuid
	 * @param type
	 * @return int
	 */
	public int getId(Long ssuid,String type){
		
//		int id = ContainerDao.getIdBySsuid(ssuid, type);
		
		return 0;
		
	}
	
	/**
	 * 根据时间戳获取id
	 * 
	 * 时间:2016-03-21
	 * 
	 * 作者:HMH
	 * 
	 * @param ssuid
	 * @param type
	 * @return Long
	 */
	public Long getLongId(Long ssuid,String type){
		
//		Long id = ContainerDao.getIdBySsuidToLong(ssuid, type);
		
		return 0l;
		
	}
	
	/**
	 * 时间戳失效后提示信息
	 * 
	 * 时间:2016-03-02
	 * 
	 * 作者:HMH
	 * 
	 * @return state=2时间戳失效
	 */
	public static String returnOverdueInfo(){
		 
		ProcessCore processCore = new ProcessCore("请返回登录！");
		
		processCore.setState(2);
		
		return processCore.toJson();
	}
	
}
