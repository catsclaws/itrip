package com.itrip.service.impl;

import com.itrip.dao.UserMapper;
import com.itrip.pojo.User;
import com.itrip.service.UserService;
import com.itrip.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public Page<User> getPagedUserByMap(Integer pageIndex, Integer pageSize, Map<String, Object> params) throws Exception {
        Integer count = userMapper.getUserCountByMap(params);

        params.put("from",(pageIndex-1)*pageSize);
        params.put("pageSize",pageSize);
        List<User> users = userMapper.getUserListByMap(params);

        Page<User> pagedUsers = new Page<>(pageIndex,pageSize,count,users);

        return pagedUsers;
    }
}
