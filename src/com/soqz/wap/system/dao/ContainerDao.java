package com.soqz.wap.system.dao;

import com.soqz.wap.system.core.ContainerInfo;

public interface ContainerDao {
	
	/**
	 * 获取对应ssuid下的id
	 * 
	 * 时间:2016-03-21
	 * 
	 * 时间:2016-03-30修改
	 * 
	 * 作者:HMH
	 * 
	 * @param ssuid 时间戳
	 * 
	 * @param type 类型
	 * 
	 * @return Long 存在返回对应值，不存在为0
	 */
	public Long getIdBySsuidToLong(ContainerInfo containerInfo);
	
	/**
	 * 放入新的ssuid数据并返回对应的ssuid
	 * 
	 * 时间:2015-04-24
	 * 
	 * 时间:2016-03-30修改
	 * 
	 * 作者:HMH
	 * 
	 * 
	 * @param id 用户或者员工编号
	 * 
	 * @param type 类型
	 * 
	 * @return Long 0失败,其他成功
	 */
	public Long insertUid(ContainerInfo containerInfo);
	
	/**
	 * 变更对应id下的ssuid数据
	 * 
	 * 时间:2015-04-24
	 * 
	 * 时间:2016-03-30修改(预留用视需求决定)
	 * 
	 * 作者:HMH
	 * 
	 * @param ssuid 时间戳
	 * 
	 * @param id 用户或者员工编号
	 * 
	 * @param type 类型
	 * 
	 * @return boolean true:成功;false：失败
	 */	
	public boolean upUid(ContainerInfo containerInfo);
	
	/**
	 * 删除对应id下的ssuid数据
	 * 
	 * 时间:2015-04-24
	 * 
	 * 时间:2016-03-30修改
	 * 
	 * 作者:HMH
	 * 
	 * @param ssuid 时间戳
	 * 
	 * @param id 用户或者员工编号
	 * 
	 * @param type 类型
	 * 
	 * @return int 0:无数据可删;1:删除数据;2异常
	 */
	public boolean delUid(ContainerInfo containerInfo);
	
	
	
}
