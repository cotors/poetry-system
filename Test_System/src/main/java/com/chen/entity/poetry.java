package com.chen.entity;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class poetry {
    private Integer id;
    private String paperName;
    private String periods;
    private String time;
    private String columnName;
    private String board;
    private String supplementName;
    private String literary;
    private String author;
    private String workName;
    private String content;
    private String avatar;
    private String liberary;
    private String subject;
    private String province;
    private String city;
    private String region;
    private BigInteger number; //用于统计数量总和，实现数据可视化
}
