package com.soqz.wap.pcore;


import com.soqz.wap.util.ResultJson;

import net.sf.json.JSONObject;

public class ProcessCore {
	
	//状态0失败；1成功;2时间戳失效
	private Integer state;
	
	//提示信息
	private String msg;
	
	//提示信息是否展示0是不展示;1是展示
	private Integer info;
	
	//处理结果
	private Object result;
	
	public ProcessCore(String msg){
		
		this.state=0;
		
		this.msg = msg;
		
		//默认不展示
		this.info = 0;
		
	}

	public Integer getState() {
		return state;
	}
	
	/**
	 * 状态0失败；1成功;2时间戳失效
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getInfo() {
		return info;
	}
	
	/**
	 * 提示信息是否展示0是不展示;1是展示
	 * @param info
	 */
	public void setInfo(Integer info) {
		this.info = info;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public String toJson(){
		
//		String result = "";
		
		ResultJson resultJson = new ResultJson();
		
		JSONObject json = resultJson.processJSON(this);
		
		if(this.state==0 || result==null){//state=0失败,result不需要结果的时候 1
//			System.out.println("state:"+(this.state==0));
//			System.out.println("result:"+(result==null));
			json.remove("result");
			
		}

		return json.toString();
		
	}
	
	public static void main(String[] args) {
		
		
	}
	
}
