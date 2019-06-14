package com.itrip.dao;

import com.itrip.pojo.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-dao.xml");
        UserMapper bean = ctx.getBean(UserMapper.class);
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("from",0);
        params.put("pageSize",5);
        List<User> users = bean.getUserListByMap(params);

        System.out.println(users.size());
    }
}
