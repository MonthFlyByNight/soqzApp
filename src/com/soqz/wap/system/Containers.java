package com.soqz.wap.system;

import com.soqz.wap.pcore.ProcessCore;
import com.soqz.wap.system.dao.ContainerDao;
import com.soqz.wap.system.dao.impl.ContainerDaoImpl;

public class Containers {
	
	ContainerDao ContainerDao = new ContainerDaoImpl();
	
	/**
	 * ��⵱ǰssuid�Ƿ����
	 * 
	 * @param ssuid
	 * 
	 * @param type
	 * 
	 * @return boolean true����;false������
	 */
	public  boolean checkUid(Long ssuid,String type){
		
//		boolean b = ContainerDao.chenckUid(ssuid, type);
		
		return false;
		
	}
	
	/**
	 * ��ʾ��Ϣ
	 * @return
	 */
	public static String returnInfo(){

		return "{\"state\":\"false\",\"result\":\"�뷵�ص�¼\"}";
		
	}
	
	/**
	 * ����ʱ�����ȡid
	 * 
	 * ʱ��:2015-05-25
	 * 
	 * ����:HMH
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
	 * ����ʱ�����ȡid
	 * 
	 * ʱ��:2016-03-21
	 * 
	 * ����:HMH
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
	 * ʱ���ʧЧ����ʾ��Ϣ
	 * 
	 * ʱ��:2016-03-02
	 * 
	 * ����:HMH
	 * 
	 * @return state=2ʱ���ʧЧ
	 */
	public static String returnOverdueInfo(){
		 
		ProcessCore processCore = new ProcessCore("�뷵�ص�¼��");
		
		processCore.setState(2);
		
		return processCore.toJson();
	}
	
}
