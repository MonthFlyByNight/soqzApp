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
			
			//��ά������
			String info = qRcode.getInfo();
			
			//���Ե�ַ
			String  path = qRcode.getPath();
			
			//��Ե�ַ
			String url = qRcode.getUrl();
			
			//����
			Integer width = qRcode.getWidth();
			
			//�߶�
			Integer height = qRcode.getHeight();
			
			//ͼƬ����
			String imageName = qRcode.getImageName();
			
			//ͼƬ����
			String imageType = qRcode.getImageType();
			
			qRcodeResult =  QRcodeImag(info,path,url,width,height,imageName,imageType);
			
//			qRcode.setDownload(qrcodename);
			
			}

			return qRcodeResult;
			
		}
	
		/**
		 * ���ɶ�ά��ͼƬ
		 * @param info ��ά����Ϣ,path ����·��,mainUrl ������,width��,height��,imageNameͼƬ����,imageTypͼƬ����
		 * @return
		 */
		private QRcodeResult QRcodeImag(String info,String path,String url,Integer width,Integer height,String imageName,String imageTyp){
		
			//		String qrcodename ="";
		
			QRcodeResult qRcodeResult = null;
		
			try {
			 
					qRcodeResult = new QRcodeResult();
			 
					//��ȡ��ǰ���·��
					String imagePath = path;
		
					//��ȡ��������Ϊ��ַͷ
					String headurl = url;

					MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		
					Map hints = new HashMap();
		
					hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		 
					BitMatrix bitMatrix = multiFormatWriter.encode(info, BarcodeFormat.QR_CODE, width, height,hints);
		 
					//��ά��ר��ͼƬ����
					String qrcodeImg = imageName + "." + imageTyp;
		 
					File file = new File(imagePath,qrcodeImg);
		 
					//�����ɵĶ�ά�������ļ���
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
