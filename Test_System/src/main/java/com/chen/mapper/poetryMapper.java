package com.chen.mapper;

import com.chen.entity.poetry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface poetryMapper {
    List<poetry> findByCondition(String liberary, String subject,String name);

    void insert(poetry poetry);

    void update(poetry poetry);

    void deleteById(Integer id);

    List<poetry> findAll();
}
