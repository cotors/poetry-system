package com.chen;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.chen.entity.User;
import com.chen.entity.poetry;
import com.chen.mapper.poetryMapper;
import com.chen.mapper.userMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class TestSystemApplicationTests {

    @Autowired
    private userMapper userMapper;

    @Autowired
    private poetryMapper poetryMapper;

    @Test
    void contextLoads() {
        List<poetry> list = poetryMapper.findAll();
        List<Dict> pillarList = new ArrayList<>();

        Set<String> dates = list.stream().map(poetry::getLiberary).collect(Collectors.toSet());
        for (String date : dates){
            BigInteger sum = list.stream().filter(poetry -> poetry.getLiberary().equals(date)).
                    map(poetry::getNumber).reduce(BigInteger::add).orElse(BigInteger.ZERO);
            Dict dict = Dict.create();
            Dict pillar = dict.set("date", date).set("value", sum);
            pillarList.add(pillar);
        }
        System.out.println(pillarList);
    }

    @Test
    void test(){
        List<poetry> list = poetryMapper.findAll();
        List<Dict> pieList = new ArrayList<>();
        //获取到指定字段的每一个值(collect(Collectors.toSet()):去除重复的字段)
        Set<String> dates = list.stream().map(poetry::getSubject).collect(Collectors.toSet());
        for (String date : dates){
            BigInteger number = list.stream().filter(poetry -> poetry.getSubject().equals(date))
                    .map(poetry::getNumber).reduce(BigInteger::add).orElse(BigInteger.ZERO);
            Dict dict = Dict.create();
            Dict pie = dict.set("value", date).set("name", number);
            pieList.add((pie));
        }
        System.out.println(pieList);
    }

}
