package com.itrip.utils;

import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties props;

    /**
     * load properties file
     * @param fileName
     */
    private static void loadPropertiesFile(String fileName) {
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get property
     * @param fileName
     * @param key
     * @return
     */
    public static String getProperty(String fileName,String key) {
        loadPropertiesFile(fileName);
        return props.getProperty(key);
    }
}
