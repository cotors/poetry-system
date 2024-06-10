package com.chen.mapper;

import com.chen.entity.poetry;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface timeMapper {
    List<poetry> findByCondition(String preTime, String fixTime,String name);

    List<poetry> desc(String name);

    List<poetry> asc(String name);

    void insert(poetry poetry);

    void update(poetry poetry);

    void delete(Integer id);

}
