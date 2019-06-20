package com.itrip.service.impl;

import com.alibaba.fastjson.JSON;
import com.itrip.pojo.User;
import com.itrip.service.TokenService;
import com.itrip.utils.MD5Util;
import com.itrip.utils.RedisUtil;
import com.itrip.utils.UserAgentUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String generateToken(String agent, Date genDate, User user) throws Exception {
//        token:PC-3066014fa0b10792e4a762-23-20170531133947-4f6496
//        token:PC-2f63ce230bdebcd262667cd29d875143-5-20190620152221-480ab0
        StringBuilder token = new StringBuilder("token:");

        String deviceType = UserAgentUtil.getDeviceType(agent);
        if (deviceType.equals("Personal computer")) {
            token.append("PC");
        } else if (deviceType.equals("unknown")) {
            if (UserAgentUtil.isMobile(agent)) {
                token.append("MOBILE");
            } else {
                token.append("PC");
            }
        } else {
            token.append("MOBILE");
        }

        token.append(TokenService.SEPARATOR);
        token.append(MD5Util.getMd5(user.getUserCode(), TokenService.MD5_USER_CODE_LENGTH));
        token.append(TokenService.SEPARATOR);
        token.append(user.getId());
        token.append(TokenService.SEPARATOR);
        token.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        token.append(TokenService.SEPARATOR);
        token.append(MD5Util.getMd5(agent, TokenService.MD5_FEATURE_CODE_LENGTH));

        return token.toString();
    }

    @Override
    public boolean saveToken(String token, User user) throws Exception {
        String flag;
        if (token.startsWith("token:PC")) {
            flag = RedisUtil.set(token, TokenService.TOKEN_TIMEOUT, JSON.toJSONString(user));
        } else {
            flag = RedisUtil.set(token, JSON.toJSONString(user));
        }
        return flag.equals("OK");
    }

    @Override
    public boolean validateToken(String agent, String token) throws Exception {
        //token:PC-2f63ce230bdebcd262667cd29d875143-5-20190620152221-480ab0
        //判断redis中是否有token
        if (!RedisUtil.exists(token)) {
            return false;
        }
        //从token中获取最后6位特征码
        String code = token.split(TokenService.SEPARATOR)[4];
        //用md5混淆agent后与特征码对比
        return MD5Util.getMd5(agent, TokenService.MD5_FEATURE_CODE_LENGTH).equals(code);
    }

    @Override
    public boolean deleteToken(String token) throws Exception {
        long cnt = RedisUtil.del(token);
        return cnt >= 1;
    }

    @Override
    public boolean checkTokenOvertime(String token) throws Exception {
        long genTime = new SimpleDateFormat("yyyyMMddHHmmss")
                .parse(token.split(TokenService.SEPARATOR)[3]).getTime();
        long now = new Date().getTime();
        if (now - genTime < TokenService.TOKEN_PROTECTED_DURATION * 1000){
            return false; //未超时
        }
        return true; //已超时，需置换
    }

    @Override
    public String refreshToken(String agent, String token) throws Exception {
        //token:PC-2f63ce230bdebcd262667cd29d875143-5-20190620152221-480ab0
        //从旧token中获取user对象（从redis中对应的key获取json的user）
        String strUser = RedisUtil.get(token);
        User user = JSON.parseObject(strUser,User.class);
        //生成新token
        String newToken = generateToken(agent, new Date(), user);
        //新token存入redis
        saveToken(newToken, user);
        //旧token过期时间延时2分钟
        RedisUtil.set(token,TokenService.TOKEN_DELAY_LIFE,strUser);

        return newToken;
    }
}
