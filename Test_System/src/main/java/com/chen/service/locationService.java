package com.chen.service;

import com.chen.entity.poetry;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface locationService {
    List<poetry> findByCondition(String province, String city);

    PageInfo<poetry> find(String province, String city, Integer pageNum, Integer pageSize);

    void insert(poetry poetry);

    void update(poetry poetry);

    void delete(Integer id);
}
