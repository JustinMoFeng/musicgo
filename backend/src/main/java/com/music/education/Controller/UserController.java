package com.music.education.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.music.education.Entity.Result;
import com.music.education.Entity.User;
import com.music.education.Service.UserService;
import com.music.education.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user){

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


}
