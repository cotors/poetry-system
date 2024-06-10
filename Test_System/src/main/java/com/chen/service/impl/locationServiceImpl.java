package com.chen.service.impl;

import com.chen.entity.poetry;
import com.chen.mapper.locationMapper;
import com.chen.service.locationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class locationServiceImpl implements locationService {

    @Autowired
    private locationMapper locationMapper;

    @Override
    public List<poetry> findByCondition(String province, String city) {
        List<poetry> poetryList = locationMapper.findByCondition(province,city);
        return poetryList;
    }

    @Override
    public PageInfo<poetry> find(String province, String city, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<poetry> poetryList = this.findByCondition(province, city);
        return PageInfo.of(poetryList);
    }

    @Override
    public void insert(poetry poetry) {
        locationMapper.insert(poetry);
    }

    @Override
    public void update(poetry poetry) {
        locationMapper.update(poetry);
    }

    @Override
    public void delete(Integer id) {
        locationMapper.delete(id);
    }
}
