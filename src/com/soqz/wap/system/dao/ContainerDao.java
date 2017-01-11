package com.soqz.wap.system.dao;

import com.soqz.wap.system.core.ContainerInfo;

public interface ContainerDao {
	
	/**
	 * ��ȡ��Ӧssuid�µ�id
	 * 
	 * ʱ��:2016-03-21
	 * 
	 * ʱ��:2016-03-30�޸�
	 * 
	 * ����:HMH
	 * 
	 * @param ssuid ʱ���
	 * 
	 * @param type ����
	 * 
	 * @return Long ���ڷ��ض�Ӧֵ��������Ϊ0
	 */
	public Long getIdBySsuidToLong(ContainerInfo containerInfo);
	
	/**
	 * �����µ�ssuid���ݲ����ض�Ӧ��ssuid
	 * 
	 * ʱ��:2015-04-24
	 * 
	 * ʱ��:2016-03-30�޸�
	 * 
	 * ����:HMH
	 * 
	 * 
	 * @param id �û�����Ա�����
	 * 
	 * @param type ����
	 * 
	 * @return Long 0ʧ��,�����ɹ�
	 */
	public Long insertUid(ContainerInfo containerInfo);
	
	/**
	 * �����Ӧid�µ�ssuid����
	 * 
	 * ʱ��:2015-04-24
	 * 
	 * ʱ��:2016-03-30�޸�(Ԥ�������������)
	 * 
	 * ����:HMH
	 * 
	 * @param ssuid ʱ���
	 * 
	 * @param id �û�����Ա�����
	 * 
	 * @param type ����
	 * 
	 * @return boolean true:�ɹ�;false��ʧ��
	 */	
	public boolean upUid(ContainerInfo containerInfo);
	
	/**
	 * ɾ����Ӧid�µ�ssuid����
	 * 
	 * ʱ��:2015-04-24
	 * 
	 * ʱ��:2016-03-30�޸�
	 * 
	 * ����:HMH
	 * 
	 * @param ssuid ʱ���
	 * 
	 * @param id �û�����Ա�����
	 * 
	 * @param type ����
	 * 
	 * @return int 0:�����ݿ�ɾ;1:ɾ������;2�쳣
	 */
	public boolean delUid(ContainerInfo containerInfo);
	
	
	
}
