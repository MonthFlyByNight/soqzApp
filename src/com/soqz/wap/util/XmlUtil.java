package com.soqz.wap.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(XmlUtil.class.getName());
	
	/**
	 * 解析xml转换为map格式
	 * 
	 * 适用范围:通用xml解析
	 * 
	 * @param xml
	 * @return
	 */
	public static Map<String, String> readerStringXml(String xml) {
		Document doc = null;
		Map<String,String> map = new LinkedHashMap<String,String>();
		try {
			// 读取并解析XML文档
			// SAXReader就是一个管道，用一个流的方式，把xml文件读出来
			// 
			// SAXReader reader = new SAXReader(); //User.hbm.xml表示你要解析的xml文档
			// Document document = reader.read(new File("User.hbm.xml"));
			// 下面的是通过解析xml字符串的
			 doc = DocumentHelper.parseText(xml);
			
			 Element rootElt = doc.getRootElement(); // 获取根节点
			 
//			 System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
			 			 
			 Iterator i = rootElt.elementIterator();
			 
			 while(i.hasNext()){
				 
				 Element recordEle = (Element) i.next();
				 map.put(recordEle.getName(), recordEle.getText());
//				 System.out.println(recordEle.getName()+":"+recordEle.getText());
				 
			 }
			 
		} catch (Exception e) {
			log.info(ToolsUtil.getDayTime()+" xml解析异常 XmlUtil.readerStringXml("+xml+")"+e.toString());
		}
		return map;
	}
}
