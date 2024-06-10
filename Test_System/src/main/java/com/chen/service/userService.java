package com.chen.service;

import com.chen.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface userService {

    void register(User user);

    String login(User user);

    void deleteById(Integer id);

    void update(User user);

    List<User> selectAll(String name);

    PageInfo<User> selectByPage(String name, Integer pageNum, Integer pageSize);

    void insert(User user);

    User selectByUsername(String username);

    List<User> select();

    void updateByPwd(String username, String oldPassword,String newPassword);
}
