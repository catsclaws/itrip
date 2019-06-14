package com.itrip.controller;

import com.alibaba.fastjson.JSON;
import com.itrip.dto.Dto;
import com.itrip.pojo.User;
import com.itrip.service.UserService;
import com.itrip.utils.ConstantsUtil;
import com.itrip.utils.DtoUtil;
import com.itrip.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BizController {
    @Resource
    UserService userService;

    @RequestMapping(value="/display",method = RequestMethod.GET,produces ={"application/json;charset=UTF-8"})
    @ResponseBody
    public Object display(
             @RequestParam(value="name",required = false) String userName,
             @RequestParam(value="page",defaultValue = "1") Integer pageIndex) throws Exception{

        Map<String,Object> params = new HashMap<>();

        params.put("userName",userName);
        Integer pageSize = ConstantsUtil.PAGE_SIZE;
        Page<User> pagedUsers = userService.getPagedUserByMap(pageIndex, pageSize, params);
        System.out.println(pagedUsers.getData().size());
        Dto dto = DtoUtil.returnDataSuccess(pagedUsers);

        return JSON.toJSONString(dto);
    }
}
