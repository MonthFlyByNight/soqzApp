package com.soqz.wap.system.core;

public class BillLog {
	
	//操作员
	private Long op;
	
	//订单号
	private Long orderid;
	
	//ip
	private String ip;
	
	//操作类型
	private Integer type;
	
	//备注
	private String remark = "操作异常";

	public Long getOp() {
		return op;
	}

	public void setOp(Long op) {
		this.op = op;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getType() {
		return type;
	}
	
	/**
	 * 1:客户下单；2：扫码打包；3：派送分配;4：订单派送;5:完成订单;6:删除订单；7：重置订单；
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		
		//增加个新增订单操作
		if(type==1){
			
			remark = "客户下单";
			
		}else if(type==2){
			
			remark = "扫码打包";
			
		}else if(type==3){
			
			remark = "派送分配";
			
		}else if(type==4){
			
			remark = "订单派送";
			
		}else if(type==5){
			
			remark = "完成订单";
			
		}else if(type==6){
			
			remark = "删除订单";
			
		}else if(type==7){
			
			remark = "重置订单";
			
		}
		
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
