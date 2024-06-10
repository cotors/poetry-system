package com.chen.service;

import cn.hutool.core.lang.Dict;
import com.chen.entity.poetry;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface poetryService {
    PageInfo<poetry> find(String liberary, String subject, String name, Integer pageNum, Integer pageSize);

    List<poetry> findByCondition(String liberary, String subject, String name);

    void insert(poetry poetry);

    void update(poetry poetry);

    void deleteById(Integer id);

    List<Dict> findByCharts();

    List<poetry> findAll();

    List<Dict> findByPie();

    List<Dict> findByLocation();
}
