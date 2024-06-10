package com.chen.service.impl;

import com.chen.entity.poetry;
import com.chen.mapper.timeMapper;
import com.chen.service.timeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class timeServiceImpl implements timeService {

    @Autowired
    private timeMapper timeMapper;

    @Override
    public List<poetry> findByCondition(String preTime,String fixTime,String name) {
        List<poetry> poetryList = timeMapper.findByCondition(preTime,fixTime,name);
        return poetryList;
    }

    @Override
    public PageInfo<poetry> find(String preTime, String fixTime, String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<poetry> poetryList = this.findByCondition(preTime, fixTime,name);
        return PageInfo.of(poetryList);
    }

    @Override
    public List<poetry> desc(String name) {
        List<poetry> poetryList = timeMapper.desc(name);
        return poetryList;
    }

    @Override
    public List<poetry> asc(String name) {
        List<poetry> poetryList = timeMapper.asc(name);
        return poetryList;
    }

    @Override
    public PageInfo<poetry> descPublish(Integer pageNum, Integer pageSize,String name) {
        PageHelper.startPage(pageNum,pageSize);
        List<poetry> list = this.desc(name);
        return PageInfo.of(list);
    }

    @Override
    public PageInfo<poetry> ascPublish(Integer pageNum, Integer pageSize,String name) {
        PageHelper.startPage(pageNum,pageSize);
        List<poetry> list = this.asc(name);
        return PageInfo.of(list);
    }

    @Override
    public void insert(poetry poetry) {
        timeMapper.insert(poetry);
    }

    @Override
    public void update(poetry poetry) {
        timeMapper.update(poetry);
    }

    @Override
    public void delete(Integer id) {
        timeMapper.delete(id);
    }
}
