package com.chen.service.impl;

import com.chen.entity.User;
import com.chen.exception.CustomException;
import com.chen.mapper.userMapper;
import com.chen.service.userService;
import com.chen.utils.JwtUtil;
import com.chen.utils.Md5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class userServiceImpl implements userService {

    @Autowired
    private userMapper userMapper;

    @Override
    public void register(User user) {
        User dbUser = userMapper.findById(user.getUsername());
        if (dbUser != null){
            throw new CustomException("用户名已存在");
        }
        if (ObjectUtils.isEmpty(user.getName())){
            user.setName(user.getUsername());
        }
        String md5String = Md5Util.getMD5String(user.getPassword());
        user.setPassword(md5String);
        userMapper.register(user);
    }

    @Override
    public String login(User user) {
        User dbUser = userMapper.findById(user.getUsername());
        if (dbUser == null){
            throw new CustomException("用户名不存在");
        }
        if (!Md5Util.getMD5String(user.getPassword()).equals(dbUser.getPassword())){
            throw new CustomException("密码不正确");
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JwtUtil.genToken(claims);
        return token;
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void update(User user) {
        LocalDateTime time = LocalDateTime.now();
        user.setUpdateTime(time);
        userMapper.update(user);
    }

    @Override
    public List<User> selectAll(String name) {
        List<User> userList = userMapper.selectByName(name);
        return userList;
    }

    @Override
    public PageInfo<User> selectByPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = this.selectAll(name);
        return PageInfo.of(userList);
    }

    @Override
    public void insert(User user) {
        if (ObjectUtils.isEmpty(user.getName())){
            user.setName(user.getUsername());
        }
        userMapper.insert(user);
    }

    @Override
    public User selectByUsername(String username) {
        User user = userMapper.findById(username);
        return user;
    }

    @Override
    public List<User> select() {
        List<User> userList = userMapper.select();
        return null;
    }

    @Override
    public void updateByPwd(String username, String oldPassword,String newPassword) {
        User user = userMapper.findById(username);
        if (!Md5Util.getMD5String(oldPassword).equals(user.getPassword())){
            throw new CustomException("原密码不正确");
        }
        String md5String = Md5Util.getMD5String(newPassword);
        userMapper.updateByPwd(username,md5String);
    }


}
