package com.chen.service.impl;

import cn.hutool.core.lang.Dict;
import com.chen.entity.poetry;
import com.chen.mapper.poetryMapper;
import com.chen.service.poetryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class poetryServiceImpl implements poetryService {

    @Autowired
    private poetryMapper poetryMapper;

    @Override
    public PageInfo<poetry> find(String liberary, String subject, String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<poetry> poetryList = this.findByCondition(liberary, subject, name);
        return PageInfo.of(poetryList);
    }

    @Override
    public List<poetry> findByCondition(String liberary, String subject,String name) {
        List<poetry> poetryList = poetryMapper.findByCondition(liberary,subject,name);
        return poetryList;
    }

    @Override
    public void insert(poetry poetry) {
        poetryMapper.insert(poetry);
    }

    @Override
    public void update(poetry poetry) {
        poetryMapper.update(poetry);
    }

    @Override
    public void deleteById(Integer id) {
        poetryMapper.deleteById(id);
    }

    @Override
    public List<poetry> findAll() {
        List<poetry> poetryList = poetryMapper.findAll();
        return poetryList;
    }

    @Override
    public List<Dict> findByCharts() {

        List<poetry> list = this.findAll();

        List<Dict> pillarList = new ArrayList<>();

        Set<String> dates = list.stream().map(poetry::getLiberary).collect(Collectors.toSet());
        for (String date : dates){
            BigInteger sum = list.stream().filter(poetry -> poetry.getLiberary().equals(date)).
                    map(poetry::getNumber).reduce(BigInteger::add).orElse(BigInteger.ZERO);
            Dict dict = Dict.create();
            Dict pillar = dict.set("date", date).set("value", sum);
            pillarList.add(pillar);
        }
        return pillarList;
    }

    @Override
    public List<Dict> findByPie() {
        List<poetry> list = this.findAll();
        List<Dict> pieList = new ArrayList<>();
        //获取到指定字段的每一个值(collect(Collectors.toSet()):去除重复的字段)
        Set<String> dates = list.stream().map(poetry::getSubject).collect(Collectors.toSet());
        for (String date : dates){
            BigInteger number = list.stream().filter(poetry -> poetry.getSubject().equals(date))
                    .map(poetry::getNumber).reduce(BigInteger::add).orElse(BigInteger.ZERO);
            Dict dict = Dict.create();
            Dict pie = dict.set("name", date).set("value", number);
            pieList.add((pie));
        }
        return pieList;
    }

    @Override
    public List<Dict> findByLocation() {
        List<poetry> list = this.findAll();
        List<Dict> dictList = new ArrayList<>();
        //获取到每个城市的名字(去重)
        Set<String> dates = list.stream().map(poetry::getCity).collect(Collectors.toSet());
        for (String date : dates){
            BigInteger number = list.stream().filter(poetry -> poetry.getCity().equals(date))
                    .map(poetry::getNumber).reduce(BigInteger::add).orElse(BigInteger.ZERO);
            Dict dict = Dict.create();
            Dict location = dict.set("name", date).set("value", number);
            dictList.add(location);
        }
        return dictList;
    }

}
