package com.itrip.service;

import com.itrip.pojo.User;
import com.itrip.utils.Page;

import java.util.Map;

public interface UserService {
    Page<User> getPagedUserByMap(Integer pageIndex, Integer pageSize, Map<String,Object> params) throws Exception;
}
