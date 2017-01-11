/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soqz.wap.util;

//import com.thinkersoft.jdbc.mysql.DBconn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 敏感词过滤
 * @author Dsmart <dsmart at 30buy email:yuliujian@30buy.com>
 */
public class IllegalWordsFilter {

    /** 直接禁止的 */
    private HashMap keysMap = null;
    private int matchType = 1;            // 1:最小长度匹配 2：最大长度匹配

    /**
     * 私有化构造函数
     */
    private IllegalWordsFilter() {

        //初始化敏感词库
        initfiltercode();

    }

    /**
     * 内部静态类
     */
    private static class IllegalWordsFilterHandler {

        public static IllegalWordsFilter filter = new IllegalWordsFilter();
    }

    /**
     * 外部调用实例
     * @return 
     */
    public static IllegalWordsFilter getInstance() {

        return IllegalWordsFilterHandler.filter;

    }

    /**
     * 添加非法词库
     * @param keywords 
     */
    private void addIlleaglwords(List<String> keywords) {

        //添加前清空非法词库
        keysMap = new HashMap();

        for (int i = 0; i < keywords.size(); i++) {
            String key = keywords.get(i).trim();
            HashMap nowhash = null;
            nowhash = keysMap;
            for (int j = 0; j < key.length(); j++) {
                char word = key.charAt(j);
                Object wordMap = nowhash.get(word);
                if (wordMap != null) {
                    nowhash = (HashMap) wordMap;
                } else {
                    HashMap<String, String> newWordHash = new HashMap<String, String>();
                    newWordHash.put("isEnd", "0");
                    nowhash.put(word, newWordHash);
                    nowhash = newWordHash;
                }
                if (j == key.length() - 1) {
                    nowhash.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 检查一个字符串从begin位置起开始是否有keyword符合， 如果有符合的keyword值，返回值为匹配keyword的长度，否则返回零
     * flag 1:最小长度匹配 2：最大长度匹配
     */
    private int checkKeyWords(String txt, int begin, int flag) {
        HashMap nowhash = null;
        nowhash = keysMap;
        int maxMatchRes = 0;
        int res = 0;
        int l = txt.length();
        char word = 0;
        for (int i = begin; i < l; i++) {
            word = txt.charAt(i);
            Object wordMap = nowhash.get(word);
            if (wordMap != null) {
                res++;
                nowhash = (HashMap) wordMap;
                if (((String) nowhash.get("isEnd")).equals("1")) {
                    if (flag == 1) {
                        wordMap = null;
                        nowhash = null;
                        txt = null;
                        return res;
                    } else {
                        maxMatchRes = res;
                    }
                }
            } else {
                txt = null;
                nowhash = null;
                return maxMatchRes;
            }
        }
        txt = null;
        nowhash = null;
        return maxMatchRes;
    }

    /**
     * 返回txt中关键字的列表
     */
    public List<String> getIlleaglHtml(String txt) {
        List<String> list = new ArrayList<String>();
        int l = txt.length();
        for (int i = 0; i < l;) {
            int len = checkKeyWords(txt, i, matchType);
            if (len > 0) {
                String tt = "<font color='#ff0000'>" + txt.substring(i, i + len) + "</font>";
                list.add(tt);
                i += len;
            } else {
                i++;
            }
        }
        txt = null;
        return list;
    }

    public List<String> getIlleaglWords(String txt) {
        List<String> list = new ArrayList<String>();
        int l = txt.length();
        for (int i = 0; i < l;) {
            int len = checkKeyWords(txt, i, matchType);
            if (len > 0) {

                list.add(txt.substring(i, i + len));
                i += len;
            } else {
                i++;
            }
        }
        txt = null;
        return list;
    }

    /**
     * 仅判断txt中是否有关键字
     */
    public boolean isContentHasIlleaglWords(String txt) {
    	System.out.println("txt:"+txt);
        for (int i = 0; i < txt.length(); i++) {
            int len = checkKeyWords(txt, i, 1);
            if (len > 0) {
                return true;
            }
        }
        txt = null;
        return false;
    }

    /**
     * 初始化敏感词列表
     * */
    private void initfiltercode() {

        List<String> keywords = new ArrayList<String>();

        DBUtil db = null;

        ResultSet rs = null;

        try {

            db = new DBUtil();

            String sql = "select keywords from illegal order by frequency desc";
            
            rs =  db.executeQuery(sql);
            

            keywords = ToolsUtil.resultSetToList(rs);

        } catch (Exception ex) {

            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (rs != null) {

                try {

                    rs.close();

                } catch (Exception ex) {

                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);

                }

            }

            if (db != null) {

                try {

                   db.close();

                } catch (Exception ex) {

                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        addIlleaglwords(keywords);

    }

    /**
     * 由数据库变更而引起的词库的更新
     * 
     * 需求:后台管理可以添加或修改敏感词库  需要更新词库
     * 
     * 适用范围：后台更新敏感词库的时候
     * 
     * 作者:Dsmart
     * 
     * 时间：2013.12.18
     * 
     * */
    public void initCaseByChange() {

        initfiltercode();

    }
}
