package com.soqz.wap.system.core;

public class ContainerInfo {
	
	//对应请求编号
	private Long id;
	
	//对应更换ssuid
	private Long ssuid;
	
	//对应的机器码
	private String clientid;
	
	//对应机器类型
	private Integer client;
	
	//对应的交换类型
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSsuid() {
		return ssuid;
	}

	public void setSsuid(Long ssuid) {
		this.ssuid = ssuid;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
