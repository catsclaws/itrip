package com.itrip.controller;

import com.alibaba.fastjson.JSON;
import com.itrip.dto.Dto;
import com.itrip.pojo.User;
import com.itrip.service.UserService;
import com.itrip.utils.ConstantsUtil;
import com.itrip.utils.DtoUtil;
import com.itrip.utils.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BizController {
    @Resource
    UserService userService;


//    @ApiIgnore
    @ApiOperation(value="分页显示用户",httpMethod = "GET",
                  protocols = "HTTP",produces = "application/json",
                  response=Object.class,notes = "根据姓名、页码分页显示用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form",required = false,value="姓名",name="name"),
            @ApiImplicitParam(paramType = "form",required = true,value="页码",name="page",defaultValue = "1")
    })
    @RequestMapping(value="/display",method = RequestMethod.GET,produces ={"application/json;charset=UTF-8"})
    @ResponseBody
    public Dto display(
//             @ApiParam(required = false,name="name",value="姓名")
             @RequestParam(value="name",required = false) String userName,
//             @ApiParam(required = true,name="page",value="页码",defaultValue = "1")
             @RequestParam(value="page",defaultValue = "1") Integer pageIndex) throws Exception{

        Map<String,Object> params = new HashMap<>();

        params.put("userName",userName);
        Integer pageSize = ConstantsUtil.PAGE_SIZE;
        Page<User> pagedUsers = userService.getPagedUserByMap(pageIndex, pageSize, params);
        System.out.println(pagedUsers.getData().size());
        Dto dto = DtoUtil.returnDataSuccess(pagedUsers);

        return dto;
    }
}
