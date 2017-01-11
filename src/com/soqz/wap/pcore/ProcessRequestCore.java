package com.soqz.wap.pcore;

public class ProcessRequestCore {
	
	//请求头
	private String act; 
	
	//时间戳
	private Long no;
	
	//页面
	private Integer page = 1;
	
	//数量(这个一般不让传)
	private Integer pageSize = 10;
	
	//查询字段
	private String selectText;
	
	//城市编号
	private Integer cityid = 1;
	
	//ip
	private String ip;

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSelectText() {
		return selectText;
	}

	public void setSelectText(String selectText) {
		this.selectText = selectText;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
}
