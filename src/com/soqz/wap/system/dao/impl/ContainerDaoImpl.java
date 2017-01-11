package com.soqz.wap.system.dao.impl;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.soqz.wap.system.core.ContainerInfo;
import com.soqz.wap.system.dao.ContainerDao;
import com.soqz.wap.util.DBUtil;
import com.soqz.wap.util.ToolsUtil;

public class ContainerDaoImpl implements ContainerDao{
	
	@Override
	public Long getIdBySsuidToLong(ContainerInfo containerInfo) {
		
		Long id = 0l;
		
		DBUtil db = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		if(containerInfo!=null){
			
			Long ssuid = containerInfo.getSsuid();
			
			String type = containerInfo.getType();
			
			try {
				
				db = new DBUtil();
				
				//7�������жϳ����ͷ���0
				sql = "select no from container where type='"+type+"' and  uid='"+ssuid+"' and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= datetime";
				
				rs = db.executeQuery(sql);
				
				if(rs.next()){
					
					id = rs.getLong("no");
					
				}
				
			} catch (Exception e) {
				
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
				
			}finally{
				
				try {
					
					if(rs!=null){
						
						rs.close();
						
					}
					
					db.close();
					
				} catch (Exception e) {
					
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
					
				}
				
			}

		}
		
		return id;
	}

	@Override
	public Long insertUid(ContainerInfo containerInfo) {
		
		Long ssuid = 0l;
		
		DBUtil db = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		if(containerInfo!=null){
			
			String type = containerInfo.getType();
			
			//��ȡ���
			Long id = containerInfo.getId();
			
			//��ȡclientid
			String clientid = containerInfo.getClientid();
			
			//��ȡ�ֻ�����
			Integer client = containerInfo.getClient();
			
			try {
				
				db = new DBUtil();
					
				//������temp_ssuid
//				Long temp_ssuid = ToolsUtil.getCurrentTimeMillis();
				
				Long temp_ssuid = 1459325434449l;
				
				containerInfo.setSsuid(temp_ssuid);
//					
//				//��ѯ���ݿ��Ƿ���ڸ�ssuid
				Long db_id = getIdBySsuidToLong(containerInfo);
//					
				if(db_id>0){//����֤���ظ���ssuid�ӿ�����
//						
//					//��������
//					temp_ssuid = ToolsUtil.getCurrentTimeMillis();
					ssuid = temp_ssuid;
//						
				}else{
					
					//����ɾ���������µ�id����������ssuid
					boolean b = delNo(containerInfo);
					
					if(b){
						
						temp_ssuid = ToolsUtil.getCurrentTimeMillis();
						
						sql = "insert into container (no,uid,datetime,type,clientid,client) values("+id+","+temp_ssuid+",now(),'"+type+"','"+clientid+"',"+client+");";

						int num  = db.insertOrUpdateInfoTONum(sql);
								
						if(num>0){
									
							ssuid = temp_ssuid;	
									
						}
						
					}
					
				}
						
				

			} catch (Exception e) {
				
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
				
			}finally{
				
				try {
					
					if(rs!=null){
						
						rs.close();
						
					}
					
					db.close();
					
				} catch (Exception e) {
					
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
					
				}
				
			}

		}
		
		return ssuid;
		
	}

	@Override
	public boolean upUid(ContainerInfo containerInfo) {
		
		// TODO: handle exception
		
		return false;
	}

	@Override
	public boolean delUid(ContainerInfo containerInfo) {
		
		DBUtil db = null;
		
		String sql = "";
		
		boolean b = false;
		
		if(containerInfo!=null){
			
			try {
				
				db = new DBUtil();
				
				Long ssuid = containerInfo.getSsuid();
				
				String type = containerInfo.getType();
				
				sql = "delete from container where uid="+ssuid+" and type='" +type+ "'";
				
				int num = db.insertOrUpdateInfoTONum(sql);
				
				if(num>0){
					
					b = true;
					
				}
				
			} catch (Exception e) {
				
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
				
			}finally{
				
				try {
					
					db.close();
					
				} catch (Exception e) {
					
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
					
				}
				
			}

		}
		
		return b;
		
	}
	
	/**
	 * ���ݱ��ɾ��ָ����ssuid
	 * 
	 * @param id
	 * 
	 * @param type
	 * 
	 * @return
	 */
	private boolean delNo(ContainerInfo containerInfo){
		
		DBUtil db = null;
		
		String sql = "";
		
		boolean b = false;
		
		if(containerInfo!=null){
			
			try {
				
				db = new DBUtil();
				
				Long id = containerInfo.getId();
				
				String type = containerInfo.getType();
				
				sql = "delete from container where no="+id+" and type='" +type+ "'";
				
				int num = db.insertOrUpdateInfoTONum(sql);
				
				if(num>0){
					
					b = true;
					
				}
				
			} catch (Exception e) {
				
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
				
			}finally{
				
				try {
					
					db.close();
					
				} catch (Exception e) {
					
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
					
				}
				
			}

		}
		
		return b;
		
		
	}
	
	public static void main(String[] args) {
		
		ContainerInfo containerInfo = new ContainerInfo();
		
		containerInfo.setId(2l);
		
		containerInfo.setType("s");
		
		containerInfo.setClient(1);
		
		ContainerDao containerDao = new ContainerDaoImpl();
		
		Long ssuid = containerDao.insertUid(containerInfo);
		
		System.out.println(ssuid);
		
		Long id = containerDao.getIdBySsuidToLong(containerInfo);
		
		System.out.println(id);
		
	}

}
