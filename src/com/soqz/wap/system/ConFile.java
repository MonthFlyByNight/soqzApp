package com.soqz.wap.system;

import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConFile {
    static String CONFIG_BUNDLE_NAME = "com.soqz.wap.system.appsystem";
	static java.util.PropertyResourceBundle configBundle = null;

    private ConFile() {
    }

    private static class ConFileHandler {

        private static PropertyResourceBundle getConfigBundle() {

            if (configBundle == null) {

                try {

                    //读取配置文件参数
                    configBundle = (PropertyResourceBundle) java.util.PropertyResourceBundle.getBundle(CONFIG_BUNDLE_NAME);

                } catch (Exception ex) {

                    Logger.getLogger(ConFile.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

            return configBundle;
        }
    }

    public static String getString(String s) { //获得配置文件字段

        return ConFileHandler.getConfigBundle().getString(s);
    }
}
