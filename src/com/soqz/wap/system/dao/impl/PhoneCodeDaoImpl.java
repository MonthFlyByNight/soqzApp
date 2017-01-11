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
		
		ProcessCore processCore = new ProcessCore("获取验证码失败");
		
		DBUtil db = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		if(phoneCode!=null){
			
			try {
				
				db = new DBUtil();
				
				String type = phoneCode.getType();
				
				//手机号
				String phone = phoneCode.getPhone();
				
				//ip
				String ip = phoneCode.getIp();
				
				if("s".equals(type)){
					
					//这里需要查一次员工信息
					sql = "select id from staff where phone='"+phoneCode.getPhone()+"';";
					
					rs = db.executeQuery(sql);
					
					if(!rs.next()){
						
						processCore.setInfo(1);
						
						processCore.setMsg("该员工不存在，无权获取验证码！");
						
					}
					
				}else{
					
					//判断手机有效性
					if(ToolsUtil.validInputPhone(phone)){//有效进行判断
						
						//其他情况下进行验证码获取
						//1检查手机获取次数
						boolean b = checkPhone(phone);
						
						if(!b){//不于2次
							
							b = checkIP(ip);
							
							if(!b){//不大于6次
								
								//进行验证码操作
								String code = code(phoneCode);
								
								if(!"0".equals(code)){
									
									processCore.setMsg("获取验证码成功！");
									
									Map<String,String> map = new LinkedHashMap<String,String>();
									
									map.put("code", code);
									
									processCore.setResult(map);
									
								}
															
							}else{
								
								processCore.setInfo(1);
								
								processCore.setMsg("您当前手机ip注册超过6次，请1小时后再来！");

								
							}
							
						}else{
							
							processCore.setInfo(1);
							
							processCore.setMsg("您获取验证码过于频繁，请1小时后再来！");
							
						}
						
					}else{
						
						processCore.setInfo(1);
						
						processCore.setMsg("请输入正确的手机号码!");

						
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
	 * 确定60分钟同一个号码内小于3次认证
	 * 
	 * @param phone  手机号码
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
	 * 确定60分钟同IP地址内小于6次认证
	 * 
	 * @param ip 手机ip地址
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
					
					if (count > 6) {//小于6次验证
						
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
	 * 根据手机号生成验证码,并记录进数据库
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
					
					Send_sioo.post(phone, "您好！您的当前验证码:" + randStr
							+ ",请于30分钟内登陆验证(拇指外卖)");
					
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
