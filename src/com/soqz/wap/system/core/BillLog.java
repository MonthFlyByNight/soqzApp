package com.soqz.wap.system.core;

public class BillLog {
	
	//����Ա
	private Long op;
	
	//������
	private Long orderid;
	
	//ip
	private String ip;
	
	//��������
	private Integer type;
	
	//��ע
	private String remark = "�����쳣";

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
	 * 1:�ͻ��µ���2��ɨ������3�����ͷ���;4����������;5:��ɶ���;6:ɾ��������7�����ö�����
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		
		//���Ӹ�������������
		if(type==1){
			
			remark = "�ͻ��µ�";
			
		}else if(type==2){
			
			remark = "ɨ����";
			
		}else if(type==3){
			
			remark = "���ͷ���";
			
		}else if(type==4){
			
			remark = "��������";
			
		}else if(type==5){
			
			remark = "��ɶ���";
			
		}else if(type==6){
			
			remark = "ɾ������";
			
		}else if(type==7){
			
			remark = "���ö���";
			
		}
		
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
