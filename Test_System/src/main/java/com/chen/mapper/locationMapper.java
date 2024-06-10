package com.chen.mapper;

import com.chen.entity.poetry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface locationMapper {
    List<poetry> findByCondition(String province, String city);

    void insert(poetry poetry);

    void update(poetry poetry);

    void delete(Integer id);
}
