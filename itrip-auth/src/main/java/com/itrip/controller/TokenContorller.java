package com.itrip.controller;

import com.itrip.dto.Dto;
import com.itrip.service.TokenService;
import com.itrip.utils.DtoErrorCode;
import com.itrip.utils.DtoUtil;
import com.itrip.vo.TokenVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/api")
public class TokenContorller {

    @Resource
    TokenService tokenService;

    @RequestMapping(value="/retoken",method = RequestMethod.POST,produces ={"application/json;charset=UTF-8"})
    @ResponseBody
    public Dto refreshToken(HttpServletRequest request) {
        //从请求中获取token和user-agent
        String token = request.getHeader("token");
        String agent = request.getHeader("user-agent");
        try {
            //验证token
            if(!tokenService.validateToken(agent,token)){
                return DtoUtil.returnFail("token无效", DtoErrorCode.AUTH_TOKEN_INVALID);
            }
            //判断是否到达置换时间（超过保护期）
            if (!tokenService.checkTokenOvertime(token)){
                return DtoUtil.returnFail("token未超时，无需置换", DtoErrorCode.AUTH_REPLACEMENT_TIME_PROTECTE);
            }
            //置换token
            String newToken = tokenService.refreshToken(agent, token);
            //创建vo
            Date now = new Date();
            TokenVO tokenVO = new TokenVO();
            tokenVO.setGenTime(now.getTime());
            tokenVO.setExpTime(now.getTime() + TokenService.TOKEN_TIMEOUT * 1000);
            tokenVO.setToken(token);
            //返回dto
            return DtoUtil.returnDataSuccess(tokenVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("置换token失败",DtoErrorCode.AUTH_UNKNOWN);
        }
    }
}
