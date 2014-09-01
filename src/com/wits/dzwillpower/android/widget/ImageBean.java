package com.wits.dzwillpower.android.widget;

public class ImageBean {
	private String imgname;
	private String imgurl;
	private String imgpicture;
	public String getImgname() {
		return imgname;
	}
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getImgpicture() {
		return imgpicture;
	}
	public void setImgpicture(String imgpicture) {
		this.imgpicture = imgpicture;
	}
	public ImageBean(String imgname, String imgurl) {
		super();
		this.imgname = imgname;
		this.imgurl = imgurl;
	}
	
	
	

}
