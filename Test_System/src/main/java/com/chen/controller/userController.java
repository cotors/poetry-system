package com.chen.controller;

import cn.hutool.core.io.FileUtil;
import com.chen.entity.Result;
import com.chen.entity.User;
import com.chen.service.userService;
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
import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {

    //获取当前项目根路径 E:\IDEA Test\order
    private static final String filePath = System.getProperty("user.dir") + "/files/";

    @Autowired
    private userService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
      String token = userService.login(user);
      return Result.success(token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
      userService.register(user);
      return Result.success();
    }

    @PostMapping("/add")
    public Result add(@RequestBody User user){
        userService.insert(user);
        return Result.success();
    }

    @GetMapping("/select/{username}")
    public Result selectByUsername(@PathVariable String username){
        User user = userService.selectByUsername(username);
        return Result.success(user);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    //条件查询
    @GetMapping("/selectAll")
    public Result selectAll(String name){
        List<User> userList = userService.selectAll(name);
        return Result.success(userList);
    }

    //查询全部
    @GetMapping("/all")
    public Result select(){
        List<User> userList = userService.select();
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectByPage(String name,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<User> userList = userService.selectByPage(name,pageNum,pageSize);
        return Result.success(userList);
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        if (!FileUtil.exist(filePath)){
            FileUtil.mkdir(filePath);
        }

        String originalFilename = file.getOriginalFilename();

        String fileName = System.currentTimeMillis() + "." + FileUtil.extName(originalFilename);

        String realFilePath = filePath + fileName;
        File localFile = new File(realFilePath);
        file.transferTo(localFile);
        String url = "http://localhost:8080/user/download/" + fileName;
        return Result.success(url);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        String realFilePath = filePath + fileName;
        byte[] bytes = FileUtil.readBytes(realFilePath);
        ServletOutputStream os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }

    @GetMapping("/updateByPwd")
    public Result updateByPwd(@RequestParam(required = false) String username,
                              @RequestParam(required = false) String oldPassword,
                              @RequestParam(required = false) String newPassword){
        userService.updateByPwd(username,oldPassword,newPassword);
        return Result.success();
    }

}
