package com.itrip.dao;

import com.itrip.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> getUserListByMap(Map<String,Object> params) throws Exception;
    Integer getUserCountByMap(Map<String,Object> params) throws Exception;
    User getUserByUserCode(String userCode) throws Exception;
}
