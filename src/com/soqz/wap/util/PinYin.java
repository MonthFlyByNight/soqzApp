package com.soqz.wap.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * ����תƴ��������
 * 
 * ���÷�Χ:�޶�������������תƴ��
 * 
 * ʱ��:2014-09-18
 * 
 * @author HMH
 *
 */
public class PinYin {
	
	private String pinyinName = "";
 
    public static String getPinYin(String strs) {
 
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);//���Ǵ�������Ĵ�������
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);//���Ǵ�������Ĵ�������
        char[] ch = strs.trim().toCharArray();
        StringBuffer buffer = new StringBuffer("");
 
        try {
            for (int i = 0; i < ch.length; i++) {
                // unicode��bytesӦ��Ҳ����.
                if (Character.toString(ch[i]).matches("[\u4e00-\u9fa5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                            ch[i], format);
                    buffer.append(temp[0]);// :���"?"�Ѿ����������������3��ʱ����ʾmyeclipse8.6��eclipse
//                    buffer.append("");
                } else {
                    buffer.append(Character.toString(ch[i]));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
 
    private String HanyuToPinyin(String name){
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = 
                                           new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray
                                           (nameChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } 
        }
        return pinyinName;
    }
    
    public static void main(String[] args) {
//        String chstrs = "�����! Good homesick!";
    	String chstrs = "լ����";
        System.out.println("����:" + chstrs);
        System.out.println("�����" + getPinYin(chstrs));
        
//        System.out.println(new PinYin().HanyuToPinyin("˳��"));
    }
 
}