package com.itrip.service;

import com.itrip.pojo.User;

public interface AccountService {
    User checkLogin(String userCode,String userPassword) throws Exception;
}
