package com.itrip.service.impl;

import com.itrip.pojo.User;
import com.itrip.utils.Page;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-dao.xml", "applicationContext-biz.xml");
        UserServiceImpl bean = ctx.getBean(UserServiceImpl.class);
        Map<String,Object> params = new HashMap<String, Object>();
        Page<User> pagedUser = bean.getPagedUserByMap(1, 5, params);

        System.out.println(pagedUser.getData().size());
    }
}
