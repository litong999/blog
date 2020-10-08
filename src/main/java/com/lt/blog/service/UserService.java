package com.lt.blog.service;

import com.lt.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
