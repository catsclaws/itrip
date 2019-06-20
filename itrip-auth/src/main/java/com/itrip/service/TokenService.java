package com.itrip.service;

import com.itrip.pojo.User;

import java.util.Date;

public interface TokenService {

    String SEPARATOR = "-";
    int TOKEN_TIMEOUT = 7200; //token有效期2小时
    int TOKEN_PROTECTED_DURATION = 5400; //token保护期1.5小时（超过该保护期的需要置换） 注：测试时把该时间改短一些
    int TOKEN_DELAY_LIFE = 120; //token在redis中延时2分钟

    int MD5_USER_CODE_LENGTH = 32;
    int MD5_FEATURE_CODE_LENGTH = 6;
    /**
     * 生成token
     * @param agent
     * @param user
     * @return
     * @throws Exception
     */
    String generateToken(String agent, Date genDate,User user) throws Exception;


    /**
     * 保存token
     * @param token
     * @param user
     * @return
     */
    boolean saveToken(String token,User user) throws Exception;

    /**
     * 验证token（验证redis中是否存在、验证特征码是否正确）
     * @param agent
     * @param token
     * @return
     * @throws Exception
     */
    boolean validateToken(String agent,String token) throws Exception;

    /**
     * 删除token
     * @param token
     * @return
     * @throws Exception
     */
    boolean deleteToken(String token) throws Exception;

    /**
     * 验证token是否超过保护期（判断是否需要置换）
     * @param token
     * @return
     * @throws Exception
     */
    boolean checkTokenOvertime(String token) throws Exception;

    /**
     * 置换token
     * @param agent
     * @param token
     * @return
     * @throws Exception
     */
    String refreshToken(String agent,String token) throws Exception;
}
