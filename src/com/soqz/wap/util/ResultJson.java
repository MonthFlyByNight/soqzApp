package com.soqz.wap.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import net.sf.json.util.CycleDetectionStrategy;

//import com.soqz.wap.core.QRcode;

public class ResultJson {

    /**
     * ��¼���ݴ��� ʱ��:2014-06-23 ����:HMH
     *
     * @param phone �ֻ���
     * @param state �ж�״̬
     * @param result ��ʾ��Ϣ
     * @return
     */
    public String logingProcessinfo(String phone, String state, String result) {

        JSONObject loginJson = (JSONObject) buildJson("JSONObject");

        loginJson.put("phone", phone);

        loginJson.put("state", state);

        loginJson.put("result", result);

        return loginJson.toString();
    }

    /**
     * ��Ʒ���ݴ��� ʱ��:2014-06-23 ����:HMH
     *
     * @param productList
     * @return
     */
    public String productProcessinfo(List<?> productList) {

        JSONObject productJson = (JSONObject) buildJson("JSONObject");

        productJson.put("productInfo", productList);

        return productJson.toString();
    }

    /**
     * ͨ�ø������ݴ��� ���÷�Χ:ͨ�� ʱ��:2014-06-30 ����:HMH
     *
     * @param productList
     * @return
     */
    public String jsonInfo(String key, Object object) {

        JSONObject json = (JSONObject) buildJson("JSONObject");

        json.put(key, object);

        return json.toString();
    }
    
    /**
     * ͨ�ø������ݴ��� ���÷�Χ:ͨ�� ʱ��:2014-06-30 ����:HMH
     *
     * @param Object
     * @return
     */
    public String jsonInfo(Object object) {

        //JSONObject json = (JSONObject) buildJson("JSONObject");
        JSONObject json = JSONObject.fromObject(object);
        //json.put(key, list);
        
        return json.toString();
    }

    /**
     * ͨ�ü������ݴ��� ���÷�Χ:ͨ�� ʱ��:2014-07-02 ����:HMH
     *
     * @param productList
     * @return
     */
    public String jsonInfo(String key, List<?> list) {

        JSONObject json = (JSONObject) buildJson("JSONObject");

        json.put(key, list);

        return json.toString();
    }

    /**
     * ͨ�����ֶ����ݴ��� ���÷�Χ:ͨ�� ʱ��:2014-07-02 ����:HMH
     *
     * @param productList
     * @return
     */
    public String jsonInfo(String[] key, Object[] info) {

        JSONObject json = (JSONObject) buildJson("JSONObject");

        for (int i = 0; i < key.length; i++) {

            json.put(key[i], info[i]);
        }

        return json.toString();
    }
    
    /**
     * ȫ�����ݴ���
     * 
     * ʱ��:2016-01-07
     * 
     * ����:HMH
     * @param object
     * @return
     */
    public JSONObject processJSON(Object object) {

        //JSONObject json = (JSONObject) buildJson("JSONObject");
        JSONObject json = JSONObject.fromObject(object);
        //json.put(key, list);
        
        return json;
    }

    /**
     * ÿ����������ʱ����ʵ����json
     *
     * ���÷�Χ:json����
     *
     * ʱ��:2014-06-19
     *
     * ����:HMH
     *
     * @return
     */
    private Object buildJson(String start) {

//        JsonConfig jsonConfig = JsonConfig.getInstance();

//        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
    	
    	JsonConfig jsonConfig = new JsonConfig();
    	 
    	jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
    	 
        if ("JSONObject".equals(start)) {

            JSONObject jsonObject = new JSONObject();

            return jsonObject;

        } else if ("JSONArray".equals(start)) {

            JSONArray jsonArray = new JSONArray();

            return jsonArray;
        }

        return null;
    }

    public static void main(String[] args) {
//        QRcode qrcode = new QRcode();
////		JSONObject json = JSONObject.fromObject(qrcode);
//        JSONObject json = new JSONObject();
//        json.put("qrcodeInfo", qrcode);
//        System.out.println(json);

    }

}