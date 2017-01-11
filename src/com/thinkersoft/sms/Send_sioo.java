/*
 * 短信发送平台
 * 
 */
package com.thinkersoft.sms;
import java.util.Arrays;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 *
 * @author luochaoping
 *查询条数：http://210.5.158.31/hy/m?uid=50041&auth=0b183c062fa7a6209f83e499fa7bd383
 */
public class Send_sioo {

    public static String post(String mobile, String msg) throws Exception {
        //public static void main(String[] args) throws Exception {

        HttpClient httpClient = new HttpClient();
        //发送消息
        String url = "http://210.5.158.31/hy/";
        PostMethod postMethod = new PostMethod(url);
        NameValuePair[] data = {
            new NameValuePair("expid", "0"),
            new NameValuePair("uid", "50041"),
            new NameValuePair("auth", "0b183c062fa7a6209f83e499fa7bd383"),
            new NameValuePair("mobile", mobile),
            new NameValuePair("msg", java.net.URLEncoder.encode(msg, "GBK")),};

        postMethod.setRequestBody(data);    

        int statusCode = httpClient.executeMethod(postMethod);

        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            Header locationHeader = postMethod.getResponseHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                System.out.println("The page was redirected to:" + location);
            } else {
                System.err.println("Location field value is null.");
            }
        }
        return statusCode + "";
    }
    
    public static void main(String[] args) throws Exception {
    	Send_sioo send_sioo = new Send_sioo();
    	send_sioo.post("13959118298", "测试");
	}
}
