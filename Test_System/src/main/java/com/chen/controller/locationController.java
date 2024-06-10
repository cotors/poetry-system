package com.chen.controller;

import com.chen.entity.Result;
import com.chen.entity.poetry;
import com.chen.service.locationService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class locationController {

    @Autowired
    private locationService locationService;

    @GetMapping("/findByCondition")
    public Result findByCondition(@RequestParam(required = false) String province,
                                  @RequestParam(required = false) String city){
        List<poetry> poetryList = locationService.findByCondition(province,city);
        return Result.success(poetryList);
    }

    @GetMapping("/find")
    public Result find(@RequestParam(required = false) String province,
                       @RequestParam(required = false) String city,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<poetry> poetryPageInfo = locationService.find(province,city,pageNum,pageSize);
        return Result.success(poetryPageInfo);
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody poetry poetry){
        locationService.insert(poetry);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody poetry poetry){
        locationService.update(poetry);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        locationService.delete(id);
        return Result.success();
    }

}
