package com.music.education.Controller;

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


}
