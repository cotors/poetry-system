package com.chen.mapper;

import com.chen.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface userMapper {

    User findById(String username);

    void register(User user);

    void deleteById(Integer id);

    void update(User user);

    List<User> selectByName(String name);

    void insert(User user);

    List<User> select();

    void updateByPwd(String username, String md5String);
}
