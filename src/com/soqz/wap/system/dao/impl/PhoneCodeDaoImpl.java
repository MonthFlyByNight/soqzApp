package com.soqz.wap.system.dao.impl;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.soqz.wap.pcore.ProcessCore;
import com.soqz.wap.system.core.PhoneCode;
import com.soqz.wap.system.dao.PhoneCodeDao;
import com.soqz.wap.util.DBUtil;
import com.soqz.wap.util.ToolsUtil;
import com.thinkersoft.sms.Send_sioo;

public class PhoneCodeDaoImpl implements PhoneCodeDao{

	@Override
	public String getCode(PhoneCode phoneCode) {
		
		return getCodePo(phoneCode).toJson();
		
	}
	
	private ProcessCore getCodePo(PhoneCode phoneCode) {
		
		ProcessCore processCore = new ProcessCore("��ȡ��֤��ʧ��");
		
		DBUtil db = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		if(phoneCode!=null){
			
			try {
				
				db = new DBUtil();
				
				String type = phoneCode.getType();
				
				//�ֻ���
				String phone = phoneCode.getPhone();
				
				//ip
				String ip = phoneCode.getIp();
				
				if("s".equals(type)){
					
					//������Ҫ��һ��Ա����Ϣ
					sql = "select id from staff where phone='"+phoneCode.getPhone()+"';";
					
					rs = db.executeQuery(sql);
					
					if(!rs.next()){
						
						processCore.setInfo(1);
						
						processCore.setMsg("��Ա�������ڣ���Ȩ��ȡ��֤�룡");
						
					}
					
				}else{
					
					//�ж��ֻ���Ч��
					if(ToolsUtil.validInputPhone(phone)){//��Ч�����ж�
						
						//��������½�����֤���ȡ
						//1����ֻ���ȡ����
						boolean b = checkPhone(phone);
						
						if(!b){//����2��
							
							b = checkIP(ip);
							
							if(!b){//������6��
								
								//������֤�����
								String code = code(phoneCode);
								
								if(!"0".equals(code)){
									
									processCore.setMsg("��ȡ��֤��ɹ���");
									
									Map<String,String> map = new LinkedHashMap<String,String>();
									
									map.put("code", code);
									
									processCore.setResult(map);
									
								}
															
							}else{
								
								processCore.setInfo(1);
								
								processCore.setMsg("����ǰ�ֻ�ipע�ᳬ��6�Σ���1Сʱ��������");

								
							}
							
						}else{
							
							processCore.setInfo(1);
							
							processCore.setMsg("����ȡ��֤�����Ƶ������1Сʱ��������");
							
						}
						
					}else{
						
						processCore.setInfo(1);
						
						processCore.setMsg("��������ȷ���ֻ�����!");

						
					}
					
					
					
				}
				
			} catch(Exception e){
				
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
		
		return processCore;
		
	}
	
	/**
	 * ȷ��60����ͬһ��������С��3����֤
	 * 
	 * @param phone  �ֻ�����
	 *           
	 * @return
	 * @throws Exception
	 */
	 private boolean checkPhone(String phone) {
		
		DBUtil db = null;
		
		ResultSet rs=null;
		
		String sql = "";
		
		boolean b = false;
		
		try {
			
			db =  new DBUtil();
			
			sql = "select count(*) from phone_codes where PHONE = '" + phone
					+ "'and `DATETIME` > (now() - INTERVAL '60:0'  MINUTE_SECOND)";
			
			rs = db.executeQuery(sql);
			
			if (rs.next()) {
				
				if (rs.getString(1) != null) {
					
					int count = Integer.parseInt(rs.getString(1));
					
					if (count > 2) {
						
						b =  true;
						
					}
				}
			}
			
			
			
		} catch(Exception e){
			
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
		
		return b;
	}

	/**
	 * ȷ��60����ͬIP��ַ��С��6����֤
	 * 
	 * @param ip �ֻ�ip��ַ
	 *            
	 * @return
	 * @throws 
	 */
	private boolean checkIP(String ip){
		
		DBUtil db = null;
		
		ResultSet rs=null;
		
		String sql = "";
		
		boolean b = false;
		
		try {
			
			db=new DBUtil();
			
			sql = "select count(*) from phone_codes where IP = '" + ip
					+ "'and `DATETIME` > (now() - INTERVAL '60:0'  MINUTE_SECOND)";
			
			rs = db.executeQuery(sql);
			
			if (rs.next()) {
				
				if (rs.getString(1) != null) {
					
					int count = Integer.parseInt(rs.getString(1));
					
					if (count > 6) {//С��6����֤
						
						b =  true;
						
					}
				}
			}
			
		}catch(Exception e){
			
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
		
		return b;
		
	}
	
	/**
	 * �����ֻ���������֤��,����¼�����ݿ�
	 * @param phone
	 * @return
	 */
	private String code(PhoneCode phoneCode){
		
		DBUtil db = null;
		
		String sql = "";
		
		String randStr = "0";
		
		if(phoneCode!=null){

			try {
				
				String phone = phoneCode.getPhone();
				
				String ip = phoneCode.getIp();
				
				randStr = ToolsUtil.randomNum(5);
				
				db = new DBUtil();
				
				sql = "insert into phone_codes (PHONE,CODES,DATETIME,IP) values ('"
						+ phone + "','" + randStr + "',now(),'" + ip + "')";
				
				int num = db.insertOrUpdateInfoTONum(sql);
				
				if(num>0){
					
					Send_sioo.post(phone, "���ã����ĵ�ǰ��֤��:" + randStr
							+ ",����30�����ڵ�½��֤(Ĵָ����)");
					
				}
				
			} catch(Exception e){
				
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
		
		
			}finally{
				
				try {
					
					db.close();
					
				} catch (Exception e) {
					
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
					
				}
				
			}

		}
		
		return randStr;
		
		
	}

	@Override
	public String verifyCode(PhoneCode phoneCode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		
		PhoneCodeDao phoneCodeDao = new PhoneCodeDaoImpl();
		
		PhoneCode phoneCode = new PhoneCode();
		
		phoneCode.setPhone("13959118298");
		
		phoneCode.setIp("127.0.0.1");
		
		phoneCode.setType("u");
		
		String info = phoneCodeDao.getCode(phoneCode);
		
		System.out.println(info);
		
//		System.out.println(ToolsUtil.validInputPhone("13959118298"));
		
	}

	
}
