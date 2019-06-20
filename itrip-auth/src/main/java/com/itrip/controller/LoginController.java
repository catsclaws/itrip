package com.itrip.controller;

import com.itrip.dto.Dto;
import com.itrip.pojo.User;
import com.itrip.service.AccountService;
import com.itrip.service.TokenService;
import com.itrip.utils.DtoErrorCode;
import com.itrip.utils.DtoUtil;
import com.itrip.vo.TokenVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/api")
public class LoginController {

    @Resource
    AccountService accountService;
    @Resource
    TokenService tokenService;

    @RequestMapping(value="/dologin",method = RequestMethod.POST,produces ={"application/json;charset=UTF-8"})
    @ResponseBody
    public Dto doLogin(
            @RequestParam(value="name",required = true) String useCode,
            @RequestParam(value="pwd",required = true) String userPassword,
            HttpServletRequest request) throws Exception{
        //验证登录
        User user = accountService.checkLogin(useCode, userPassword);
        //登录成功
        if (user != null){
            String agent = request.getHeader("user-agent");
            System.out.println("login：user-agent:"+agent);
            //生成token
            Date now = new Date();
            String token = tokenService.generateToken(agent, now, user);
            //保存token到redis
            if (!tokenService.saveToken(token,user)){
                return DtoUtil.returnFail("redis异常",DtoErrorCode.AUTH_UNKNOWN);
            }
            //创建vo
            TokenVO tokenVO = new TokenVO();
            tokenVO.setGenTime(now.getTime());
            tokenVO.setExpTime(now.getTime() + TokenService.TOKEN_TIMEOUT * 1000);
            tokenVO.setToken(token);
            //返回dto
            return DtoUtil.returnDataSuccess(tokenVO);
        }else {
            return DtoUtil.returnFail("登录失败", DtoErrorCode.AUTH_ILLEGAL_USERCODE);
        }
    }

    @RequestMapping(value="/logout",method = RequestMethod.GET,produces ={"application/json;charset=UTF-8"})
    @ResponseBody
    public Dto logout(HttpServletRequest request) throws Exception{
        //从请求中获取token和user-agent
        String token = request.getHeader("token");
        String agent = request.getHeader("user-agent");
        //验证token
        if(!tokenService.validateToken(agent,token)){
            return DtoUtil.returnFail("token无效", DtoErrorCode.AUTH_TOKEN_INVALID);
        }
        //删除token
        if(!tokenService.deleteToken(token)){
            return DtoUtil.returnFail("redis异常", DtoErrorCode.AUTH_UNKNOWN);
        }
        //（预留）删除其他token相关的数据...
        return DtoUtil.returnSuccess();
    }
}
