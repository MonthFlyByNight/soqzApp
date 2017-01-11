package com.soqz.wap.system.dao.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.soqz.wap.system.core.QRcode;
import com.soqz.wap.system.core.QRcodeResult;
import com.soqz.wap.system.dao.QRcodeDao;
import com.soqz.wap.util.MatrixToImageWriter;
import com.soqz.wap.util.ToolsUtil;

public class QRcodeDaoImpl implements QRcodeDao{

	@Override
	public QRcodeResult QRcodeInfo(QRcode qRcode) {
		
		QRcodeResult  qRcodeResult = null;
		
		if(qRcode!=null){
			
			//二维码内容
			String info = qRcode.getInfo();
			
			//绝对地址
			String  path = qRcode.getPath();
			
			//相对地址
			String url = qRcode.getUrl();
			
			//长度
			Integer width = qRcode.getWidth();
			
			//高度
			Integer height = qRcode.getHeight();
			
			//图片名称
			String imageName = qRcode.getImageName();
			
			//图片类型
			String imageType = qRcode.getImageType();
			
			qRcodeResult =  QRcodeImag(info,path,url,width,height,imageName,imageType);
			
//			qRcode.setDownload(qrcodename);
			
			}

			return qRcodeResult;
			
		}
	
		/**
		 * 生成二维码图片
		 * @param info 二维码信息,path 绝对路径,mainUrl 主域名,width长,height高,imageName图片名称,imageTyp图片类型
		 * @return
		 */
		private QRcodeResult QRcodeImag(String info,String path,String url,Integer width,Integer height,String imageName,String imageTyp){
		
			//		String qrcodename ="";
		
			QRcodeResult qRcodeResult = null;
		
			try {
			 
					qRcodeResult = new QRcodeResult();
			 
					//获取当前存放路径
					String imagePath = path;
		
					//获取主域名作为地址头
					String headurl = url;

					MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		
					Map hints = new HashMap();
		
					hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		 
					BitMatrix bitMatrix = multiFormatWriter.encode(info, BarcodeFormat.QR_CODE, width, height,hints);
		 
					//二维码专属图片名称
					String qrcodeImg = imageName + "." + imageTyp;
		 
					File file = new File(imagePath,qrcodeImg);
		 
					//将生成的二维码存放至文件内
					MatrixToImageWriter.writeToFile(bitMatrix, imageTyp, file);

					if(info!=null){
				 
						qRcodeResult.setQrcodeInfo(info);
				 
						qRcodeResult.setQrcodeUrl(headurl+qrcodeImg);
				 
					}
			 
			 
				} catch (WriterException e) {
			
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
			
			
				}catch(Exception e){
			
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ToolsUtil.getDayTime(), e);
			
			
				}
		
				return qRcodeResult;
			
			}

}
