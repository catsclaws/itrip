package com.itrip.utils;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;

import java.io.IOException;

public class UserAgentUtil {
    static UASparser uasParser = null;

    private static void initUASparser() {
        try {
            uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备类型
     * @param agent
     * @return
     */
    public static String getDeviceType(String agent){
//        UserAgentInfo.UNKNOWN
        try{
            initUASparser();
            UserAgentInfo agentInfo = uasParser.parse(agent);
            String deviceType = agentInfo.getDeviceType();
            return deviceType;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否是移动设备
     * @param agent
     * @return
     */
    public static boolean isMobile(String agent) {
        boolean flag = false;
        String[] keywords = { "Android", "iPhone", "iPod", "iPad","Windows Phone", "MQQBrowser" };
        // 排除 Windows 桌面系统
        if (!agent.contains("Windows NT")
                || (agent.contains("Windows NT") && agent
                .contains("compatible; MSIE 9.0;"))) {
            // 排除 苹果桌面系统
            if (!agent.contains("Windows NT") && !agent.contains("Macintosh")) {
                for (String item : keywords) {
                    if (agent.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        //360浏览器
        //String str="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36";
        //google浏览器
        //String str = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";
        //iphone Safari浏览器
        String str = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";

        System.out.println(str);
        try {
            initUASparser();
            UserAgentInfo userAgentInfo = UserAgentUtil.uasParser.parse(str);
            System.out.println("操作系统名称：" + userAgentInfo.getOsFamily());//
            System.out.println("操作系统：" + userAgentInfo.getOsName());//
            System.out.println("浏览器名称：" + userAgentInfo.getUaFamily());//
            System.out.println("浏览器版本：" + userAgentInfo.getBrowserVersionInfo());//
            System.out.println("设备类型：" + userAgentInfo.getDeviceType());
            System.out.println("浏览器:" + userAgentInfo.getUaName());
            System.out.println("类型：" + userAgentInfo.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
