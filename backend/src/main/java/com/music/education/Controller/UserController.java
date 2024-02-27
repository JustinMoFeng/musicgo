package com.music.education.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.music.education.Entity.Result;
import com.music.education.Entity.User;
import com.music.education.Service.UserService;
import com.music.education.Utils.FileUtils;
import com.music.education.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        user.setAvatar_url("");
        int num = userService.addUser(user);
        if(num > 0){
            return Result.success("注册成功","null");
        }else if(num==-1){
            return Result.error("用户名已存在");

        }else{
            return Result.error("注册失败");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User trueUser = userService.getUserByUsername(user.getUsername());
        if(trueUser == null){
            return Result.error("用户名不存在");
        }else if(!trueUser.getPassword().equals(user.getPassword())){
            return Result.error("用户名或密码错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("username",trueUser.getUsername());
        map.put("id",trueUser.getId());
        String token = JwtUtils.generateJwt(map);
        return Result.success("登录成功",token);

    }

    @GetMapping("/myInfo")
    public Result getMyInfo(@RequestHeader("me_token") String token){
        System.out.println(token);
        Map<String, Object> map = JwtUtils.parseJwt(token);
        if(map == null){
            return Result.error("token无效");
        }
        User user = userService.getUserById((Integer) map.get("id"));
        JSONObject res = new JSONObject();
        res.put("id", user.getId());
        res.put("username", user.getUsername());
        res.put("nickname", user.getNickname());
        res.put("password","");
        if(user.getAvatar_url() != null) {
            res.put("avatar_url", user.getAvatar_url());
        }else {
            res.put("avatar_url", "");
        }
        return Result.success("获取成功",res.toString());
    }

    @PostMapping("/updateInfoWithoutAvatar")
    public Result updateInfo(@RequestHeader("me_token") String token, @RequestBody User user){
        Map<String, Object> map = JwtUtils.parseJwt(token);
        if(map == null){
            return Result.error("token无效");
        }
        user.setId((Integer) map.get("id"));
        int num = userService.updateUser(user);
        if(num > 0){
            return Result.success("修改成功","null");
        }else{
            return Result.error("修改失败");
        }
    }

    @PostMapping("/updateAvatar")
    @Transactional
    public Result updateAvatar(@RequestHeader("me_token") String token, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        Map<String, Object> map = JwtUtils.parseJwt(token);
        if(map == null){
            return Result.error("token无效");
        }
        User user = new User();
        user.setId((Integer) map.get("id"));
        // 上传头像
        String filePath = "C:\\Users\\ROG\\Desktop\\images\\";
        String fileName = UUID.randomUUID() +"&"+ avatar.getOriginalFilename();
        // 获取文件类型
        try{
            FileUtils.uploadFile(avatar.getBytes(), filePath, fileName);
            user.setAvatar_url("http://192.168.1.6/images/"+fileName);
            int num = userService.updateUserAvatar(user);
            if(num > 0){
                return Result.success("修改成功","null");
            }else{
                return Result.error("修改失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            return Result.error("上传失败");
        }


    }


}
