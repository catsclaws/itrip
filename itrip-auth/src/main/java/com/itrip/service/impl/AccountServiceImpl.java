package com.itrip.service.impl;

import com.itrip.dao.UserMapper;
import com.itrip.pojo.User;
import com.itrip.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    UserMapper userMapper;

    @Override
    public User checkLogin(String userCode, String userPassword) throws Exception{
        User user = userMapper.getUserByUserCode(userCode);
        if (user != null && user.getUserPassword().equals(userPassword)) {
            return user;
        }else{
            return null;
        }
    }
}
