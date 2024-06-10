package com.chen.service;

import com.chen.entity.poetry;
import com.github.pagehelper.PageInfo;

import java.time.LocalDate;
import java.util.List;

public interface timeService {
    List<poetry> findByCondition(String preTime, String fixTime,String name);

    PageInfo<poetry> find(String preTime, String fixTime, String name, Integer pageNum, Integer pageSize);

    List<poetry> desc(String name);

    List<poetry> asc(String name);

    PageInfo<poetry> descPublish(Integer pageNum, Integer pageSize,String name);

    PageInfo<poetry> ascPublish(Integer pageNum, Integer pageSize,String name);

    void insert(poetry poetry);

    void update(poetry poetry);

    void delete(Integer id);

}
