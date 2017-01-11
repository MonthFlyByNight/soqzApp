package com.soqz.wap.system.core;

public class QRcode {
	
		//二维码内容
		private String info;

		//二维码绝对地址
		private String path;
		
		//二维码相对地址
		private String url;
		
		//二维码图片长度
		private Integer width;
		
		//二维码图片高度
		private Integer height;
		
		//二维码图片名称
		private String imageName;
		
		//二维码图片类型
		private String imageType;

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getWidth() {
			return width;
		}

		public void setWidth(Integer width) {
			this.width = width;
		}

		public Integer getHeight() {
			return height;
		}

		public void setHeight(Integer height) {
			this.height = height;
		}

		public String getImageName() {
			return imageName;
		}

		public void setImageName(String imageName) {
			this.imageName = imageName;
		}

		public String getImageType() {
			return imageType;
		}

		public void setImageType(String imageType) {
			this.imageType = imageType;
		}
		
		
	
}
