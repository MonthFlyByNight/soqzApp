package com.soqz.wap.C3P0;

import java.io.FileWriter;
import java.io.IOException;

import com.soqz.wap.util.ToolsUtil;

	/**
	 *
	 * @author Dsmart <yuliujian@30buy.com>
	 */
	public class C3P0Log {
		

	    /** 
	     * д��־��������ԣ�����վ����Ҳ���ԸĳɰѼ�¼�������ݿ⣩
	     * @param sWord Ҫд����־����ı�����
	     */
	    public static void logResult(String sWord) {

	        FileWriter writer = null;
	        try {
//	            writer = new FileWriter("D:\\soqzLog\\C3P0Log\\" + "unionpay_log_" + System.currentTimeMillis() + ".txt",true);
	        	writer = new FileWriter("D:\\soqzLog\\C3P0Log\\" + "unionpay_log_" + ToolsUtil.getDay(0) + ".txt",true);
	            writer.write(sWord+"\r\n");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (writer != null) {
	                try {
	                    writer.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	    
	    public static void main(String[] args) {
	    	logResult("123");
		}
	}


