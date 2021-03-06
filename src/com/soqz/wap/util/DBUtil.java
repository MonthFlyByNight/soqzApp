package com.soqz.wap.util;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import com.soqz.wap.C3P0.C3P0;

/**
 * 继承自DBconn。修改更新和插入方式。以获取布尔类型结果
 * 
 * 修改时间:2014-09-24
 * 
 * 修改内容:修改继承类。改为继承C3P0。实现数据库连接池管理
 * @author HMH
 */
public class DBUtil extends C3P0{
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(DBUtil.class.getName());
	
//	 Connection conn = null;
//	
	ResultSet rs=null;
	
	public DBUtil(){
		
		super();
		
	}
	
	/**
	 * 查询所有数据包括单独数据
	 * 
	 * 适用范围:通用
	 * 
	 * 时间:2014-06-18
	 * 
	 * 作者:HMH
	 * @param sql
	 * @return ResultSet rs(数据集)
	 */
	public  ResultSet FindAll(String sql){
		try {
			
			rs=executeQuery(sql);
			
		} catch (Exception e) {
			
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
//			log.info(new java.util.Date()+"\t 数据查询异常	DBUtil.FindAll("+sql+"):"+e.toString());
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		//返回结果
		return rs;
	}
	
	/**
	 * 插入/更新数据
	 * 
	 * 适用范围:通用
	 * 
	 * 时间:2014-06-20
	 * 
	 * 作者:HMH
	 * @param sql
	 * @return boolean b
	 */
	public boolean insertOrUpdateInfo(String sql){
		
		boolean b = false;
		
		try {
			
			executeUpdate(sql);
			//super.close();
			b = true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
			
//			log.info(new java.util.Date()+"\t 数据插入异常	DBUtil.insertOrUpdateInfo("+sql+"):"+e.toString());

		}
		return b;
	}
	
	/**
	 * 插入/更新数据并返回执行条数
	 * 
	 * 适用范围:通用
	 * 
	 * 时间:2014-06-20
	 * 
	 * 作者:HMH
	 * @param sql
	 * @return boolean b
	 */
    public int insertOrUpdateInfoTONum(String sql){
		
    	int i = 0;
    			
		try {
			
			 i = executeUpdateTOnum(sql);

			
		}catch(MySQLIntegrityConstraintViolationException e){
			
			i = 0;
			
		} catch (Exception e) {
			
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
			
//			log.info(new java.util.Date()+"\t 数据插入异常	DBUtil.insertOrUpdateInfoTONum("+sql+"):"+e.toString());

			
		}
		
		return i;
	}
	
	/**
	 * 批量插入或更新数据
	 * 
	 * 适用范围:通用
	 * 
	 * 时间:2014-09-05
	 * 
	 * 作者:HMH
	 * @param sql
	 */
	public void executeUpdateArray(List<String> sql) {
        try {
        	
//            connection();
            
            conn=getConnection();
            
            Statement stmt = conn.createStatement();
            
            for (int i = 0; i < sql.size(); i++) {
            	
                stmt.executeUpdate(sql.get(i));
            }

        } catch (Exception ex) {
        	
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), ex);
//            System.err.println(new java.util.Date() + "DBconn.executeUpdate:" + ex.getMessage());
        }
    }
	
	public static void main(String[] args) {
		DBUtil db = new DBUtil();
//		String sql = "insert into phone_codes (PHONE,CODES,DATETIME,IP) values ('"+123+"','"+123+"',now(),'"+ 123 +"')";
//		String sql = "delete from phone_codes";
		
//		for(int i=0;i<10;i++){
			
//			System.out.println("1");
//			boolean b=db.insertOrUpdateInfo(sql);
			
//			System.out.println("1:"+b);
			
//		}
		
//		System.out.println(b);
		
		
		try {
//			db.close();
		} catch (Exception e) {
			// TODO: handle exceptions
		}
		
		System.out.println("\t db over \n");
		System.out.println("db over1 \n");
		
	}
}
