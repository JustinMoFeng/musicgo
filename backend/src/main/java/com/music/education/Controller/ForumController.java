package com.music.education.Controller;

import com.music.education.Entity.ForumItem;
import com.music.education.Entity.Result;
import com.music.education.Service.ForumService;
import com.music.education.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @PostMapping("/add")
    public Result addForumItem(@RequestBody ForumItem forumItem, @RequestHeader("me_token") String token){

        Map<String, Object> map = JwtUtils.parseJwt(token);
        if(map == null){
            return Result.error("token无效");
        }

        int num = forumService.addForumItem(forumItem);
        if(num > 0){
            return Result.success("发表成功","null");
        }else{
            return Result.error("发表失败");
        }
    }

}
