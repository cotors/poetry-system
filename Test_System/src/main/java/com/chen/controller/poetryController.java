package com.chen.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import com.chen.entity.Result;
import com.chen.entity.poetry;
import com.chen.service.poetryService;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("poetry")
public class poetryController {

    //获取当前项目的根路径
    private static final String filepath = System.getProperty("user.dir") + "/poetryFile/";

    @Autowired
    private poetryService poetryService;

    @GetMapping("/find")
    public Result find(@RequestParam(required = false) String liberary,
                       @RequestParam(required = false) String subject,
                       @RequestParam(required = false) String name,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize){
           PageInfo<poetry> poetryList = poetryService.find(liberary,subject,name,pageNum,pageSize);
           return  Result.success(poetryList);
    }

    @GetMapping("/findByCondition")
    public Result findByCondition(@RequestParam(required = false) String liberary,
                                  @RequestParam(required = false) String subject,
                                  @RequestParam(required = false) String name){
           List<poetry> list = poetryService.findByCondition(liberary,subject,name);
           return Result.success(list);
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody poetry poetry){
        poetryService.insert(poetry);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody poetry poetry){
        poetryService.update(poetry);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        poetryService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        //判断该路劲是否存在
        if (!FileUtil.exist(filepath)){
            FileUtil.mkdir(filepath);
        }

        //获取图片的全类名(包括后缀名)
        String originalFilename = file.getOriginalFilename();
        //拼接一个新的名称,避免图片名称相同覆盖
        String fileName = System.currentTimeMillis() + "." + FileUtil.extName(originalFilename);
        //当前项目存放图片的路劲拼接图片名称
        String realFile = filepath + fileName;
        //建立文件
        File realFiles = new File(realFile);
        //上传文件
        file.transferTo(realFiles);

        String url = "http://localhost:8080/poetry/download/" + fileName;

        return Result.success(url);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        String realFile = filepath + fileName;
        //用hutools工具类读取文件，转换成字节流
        byte[] bytes = FileUtil.readBytes(realFile);
        ServletOutputStream os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }

    @GetMapping("/pillar")
    public Result pillar(){
        List<Dict> chartsList = poetryService.findByCharts();
        return Result.success(chartsList);
    }

    @GetMapping("/pie")
    public Result pie(){
        List<Dict> picList = poetryService.findByPie();
        return Result.success(picList);
    }

    @GetMapping("/location")
    public Result location(){
        List<Dict> list = poetryService.findByLocation();
        return Result.success(list);
    }

}
