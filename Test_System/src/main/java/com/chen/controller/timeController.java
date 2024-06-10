package com.chen.controller;

import com.chen.entity.Result;
import com.chen.entity.poetry;
import com.chen.service.timeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/time")
public class timeController {

    @Autowired
    private timeService timeService;

    @GetMapping("/findByCondition")
    public Result findByCondition(@RequestParam String preTime,
                                  @RequestParam String fixTime,
                                  @RequestParam(required = false) String name){
        List<poetry> poetryList = timeService.findByCondition(preTime,fixTime,name);
        return Result.success(poetryList);
    }

    @GetMapping("/find")
    public Result find (@RequestParam(required = false) String preTime,
                        @RequestParam(required = false) String fixTime,
                        @RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<poetry> poetryList = timeService.find(preTime,fixTime,name,pageNum,pageSize);
        return Result.success(poetryList);
    }

    @GetMapping("/descPublish")
    public Result descPublish(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(required = false) String name){
        PageInfo<poetry> poetryPageInfo = timeService.descPublish(pageNum,pageSize,name);
        return Result.success(poetryPageInfo);
    }

    @GetMapping("/ascPublish")
    public Result ascPublish(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name){
        PageInfo<poetry> poetryPageInfo = timeService.ascPublish(pageNum,pageSize,name);
        return Result.success(poetryPageInfo);
    }

    //出版时间
    @PostMapping("/insert")
    public Result insert(@RequestBody poetry poetry){
        timeService.insert(poetry);
        return Result.success();
    }

    //出版时间
    @PutMapping("/update")
    public Result update(@RequestBody poetry poetry){
        timeService.update(poetry);
        return Result.success();
    }

    //出版时间
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        timeService.delete(id);
        return Result.success();
    }

}
