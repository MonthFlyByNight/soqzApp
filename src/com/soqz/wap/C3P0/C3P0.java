package com.soqz.wap.C3P0;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.PreparedStatement;
import com.soqz.wap.util.ToolsUtil;


/**
 * C3P0移植原com.thinkersoft.jdbc.mysql.DBconn
 * 修改了DBconn内获取Connection的方法。C3P0配置文件自导模式读取
 * 
 * @author HMH
 *
 */
public class C3P0 {//1
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(C3P0.class.getName());
	
	//自导模式寻找根目录下的配置文件
	private static ComboPooledDataSource _ds = new ComboPooledDataSource(); 
	
	public  Connection conn = null;
	
	ResultSet rs = null;
	
	int rsi = 0;
	

	public C3P0(){
		
		init();
		
		monitoringC3P0_Resources("new open");
		
	}
	
	private void init(){
		
		try {
			
			conn = _ds.getConnection();

		} catch (Exception e) {
			
//			System.out.println(log.getName());
//			System.out.println(log.getResourceBundleName());
			log.info(new java.util.Date() + "C3P0 conn打开失败"+e.toString());
			
		}
		
	}
	
	
	public ResultSet executeQuery(String sql) throws Exception {
        // System.err.println("DBconn.executeQuery:"+sql);
        String errMsg = "";
        try {
            if (sql.indexOf("load_file(") != -1 
                    || sql.indexOf("union") != -1 
                    || sql.indexOf("database()") != -1 
                    //|| sql.indexOf("1=1") != -1
                    ) {//安全起见，未找到解决原因之前不放开这两个功能
                errMsg = " 非法SQL ";
                throw new Exception();
            }

//            connection();
            //sql = new String(sql.getBytes("gbk"),"iso8859-1");
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (Exception ex) {
            System.err.println(new java.util.Date() + errMsg + sql + " DBconn.executeQuery:" + ex.getMessage());
            throw ex;
            //return null;
            // throw new Exception();
        }
        //System.err.println(new java.util.Date().getTime() - i);
        return rs;
    }

    public void executeUpdate(String sql) throws Exception {

        try {
//            sql = new String(sql.getBytes("gbk"),"iso-8859-1");
            //sql = new String(sql);
            //sql = StringReplace.replace(sql,"\"","″");
            //connection();
            Statement stmt = conn.createStatement();
            //System.out.println(sql);
            rsi = stmt.executeUpdate(sql);
            
        } catch (Exception ex) {
            System.err.println(new java.util.Date() + sql + "DBconn.executeUpdate:" + ex.getMessage());
            rsi = 0;
            throw ex;
        }
    }
    
    public int executeUpdateTOnum(String sql) throws Exception {
    	
    	int num = 0;

        try {
//            sql = new String(sql.getBytes("gbk"),"iso-8859-1");
            //sql = new String(sql);
            //sql = StringReplace.replace(sql,"\"","″");
            //connection();
            Statement stmt = conn.createStatement();
            //System.out.println(sql);
            num = stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.err.println(new java.util.Date() + sql + "DBconn.executeUpdate:" + ex.getMessage());
            num = 0;
            throw ex;
        }
        
        return num;
    }

    public void executeUpdateArray(String sql[]) {

        try {
            //connection();
            Statement stmt = conn.createStatement();
            for (int i = 0; i < sql.length; i++) {
                stmt.executeUpdate(sql[i]);
            }

        } catch (Exception ex) {
            System.err.println(new java.util.Date() + "DBconn.executeUpdate:" + ex.getMessage());
        }
    }

    public int executeUpdateR(String sql) {//这方法有问题，不能用，是想回调自动增量的值
        int i = -1;
        try {
            //connection();
            Statement stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_UPDATABLE);
            //System.out.println(sql);
            stmt.executeUpdate(sql);

            ResultSet rs = stmt.getResultSet();
            // getGeneratedKeys();
            ResultSetMetaData md = rs.getMetaData();
            int length = md.getColumnCount();
            System.out.println(length);
            if (rs.next()) {
                // Retrieve the auto generated key(s).
                //i = rs.getInt(1);
                //return key;
            }
        } catch (Exception ex) {
            System.err.println(new java.util.Date() + sql + "DBconn.executeUpdateR:" + ex.getMessage());
        }
        return i;
    }
    
    public String executeUpdateGetAutoIncrement(String sql) {//返回自动增量的值
        Serializable ret = null;
        try {
            //connection();
             PreparedStatement  state=  (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);     
             ResultSet rs=null;
             state.executeUpdate();
             rs = state.getGeneratedKeys();
             if (rs.next()) {
                 ret = (Serializable) rs.getObject(1);
             }      
        } catch (Exception ex) {
            System.err.println(new java.util.Date() + sql + "DBconn.executeUpdateGetAutoIncrement:" + ex.getMessage());
        }
        return ret.toString();
    }

    //获得自动增量的最新记录
    public String getAutoIncrement(String table, String field) {
        String sql = "select " + field + " from " + table + " ORDER BY " + field + " desc limit 1";
        try {
            //connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                field = rs.getString(field);
            }
        } catch (Exception ex) {
            System.err.println(new java.util.Date() + "DBconn.executeQuery:" + ex.getMessage());
        }
        //System.out.println(field);
        return field;
    }
    
	/**
	 * 自增主键主键插入值后获取自增ID
	 * 
	 * 时间:2014-09-25
	 * 
	 * 作者:HMH
	 * @param sql
	 * @return
	 */
	public int insertIntoDB(String sql){
		Statement state = null;
		ResultSet rs = null;
		int key = -1;
		try{

			state = conn.createStatement();
            
			state.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			
			rs = state.getGeneratedKeys();
			if(rs.next()){
				key = rs.getInt(1);
			}
			
			return key;
		}catch (SQLException e) {
			System.err.println(new java.util.Date() + "DBconn.insertIntoDB:" + e.getMessage());
			return key;
		}finally{
			
			if(rs!=null){
				
				try{
					
					rs.close();
					
				}catch(Exception ex){
					
					
				}
				
			}
			
		}
	}

    public String[][] getTable() throws Exception {
        if (rs == null) {
            throw new Exception(new java.util.Date() + "getTable Error:请先执行SQL操作语句");
        }
        return null;
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() throws SQLException {
    	
        if (rs != null) {
            rs.close();
        }
        if (conn != null) {
        	
            conn.close();
        }
       // _ds.close();
        monitoringC3P0_Resources("close");
        
    }
    
  public void close(String info) throws SQLException {
//    	System.out.println(info);
        if (rs != null) {
            rs.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public int getRSlength() {
        return rsi;
    }
    
    public void monitoringC3P0_Resources(String state){
    	
    	try {
    		
    		C3P0Log.logResult("time:"+ToolsUtil.getDayTime()+" DB操作状态:"+state+";"+" 池中总连接数:"+_ds.getNumConnections()
//    				+"; 目前最大连接数:"+_ds.getMaxPoolSize()+"; 目前最小连接数:"+_ds.getMinPoolSize()
    				+"; 当前使用中连接数:"+_ds.getNumBusyConnections()+" 当前池中空闲连接数"+_ds.getNumIdleConnections());
    		
//    		System.out.println("DB操作状态:"+state);
    		
//    		System.out.println();
//    		
//    	    System.out.println("池中连接数:"+_ds.getNumConnections());
//    		
//    		System.out.println("使用中连接数:"+_ds.getNumIdleConnections());
//    		
//    		System.out.println("池中空闲连接数"+_ds.getNumBusyConnections());
    		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
    	
    }


    
    public static void main(String[] args) {
    	
//    	C3P0 c = new C3P0();
//    	try {
//			c.executeQuery("select * from user_info");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		AddressDaoImpl addressDaoImpl = new AddressDaoImpl();
//		
//		Address_Info addressInfo = (Address_Info) addressDaoImpl.findInfoById("1");
//    	int num=c.insertIntoDB("insert into address (user_id,phone,linkman,address,time) values('12345678901','12345678901','啦啦','测试',now())");
////    	String info=c.executeUpdateGetAutoIncrement("insert into address (user_id,phone,linkman,address,time) values('12345678901','12345678901','啦啦','测试',now())");
//    	String i=Integer.toString(num);
//    	
//    	System.out.println(i);
    	
//    	c.monitoringC3P0_Resources();
    	
//    	DBUtil db = new DBUtil();
    	
	}
}
