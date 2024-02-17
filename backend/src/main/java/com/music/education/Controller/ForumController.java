package com.music.education.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.music.education.Entity.*;
import com.music.education.Service.ForumService;
import com.music.education.Utils.FileUtils;
import com.music.education.Utils.JwtUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @PostMapping("/add")
    @Transactional
    public Result addForumItem(@RequestParam("forumBody") String forumItemString, @RequestHeader("me_token") String token, @RequestParam("forumFile") List<MultipartFile> files) throws IOException {

        List<ForumItemAdditional> additionalList = new ArrayList<>();

        ForumItem forumItem = JSONObject.parseObject(forumItemString, ForumItem.class);

        Map<String, Object> map = JwtUtils.parseJwt(token);
        if(map == null){
            return Result.error("token无效");
        }
        forumItem.setAuthor((Integer) map.get("id"));
        forumItem.setReply_num(0);
        forumItem.setLike_num(0);
        forumItem.setTime(new Timestamp(System.currentTimeMillis()));
        try{
            int num = forumService.addForumItem(forumItem);
            String filePath = "C:\\Users\\ROG\\Desktop\\images\\";
            if(num > 0){
                int id = forumItem.getId();
                for(MultipartFile file : files){
                    ForumItemAdditional additional = new ForumItemAdditional();
                    String fileName = UUID.randomUUID() +"&"+ file.getOriginalFilename();
                    // 获取文件类型
                    String fileType = fileName.substring(fileName.lastIndexOf("."));
                    FileUtils.uploadFile(file.getBytes(), filePath, fileName);
                    additional.setForum_item_id(id);
                    additional.setFile_name(fileName);
                    additional.setFile_type(fileType);
                    additional.setIs_delete(false);
                    additional.setFile_url("http://localhost:8080/images/"+fileName);
                    additionalList.add(additional);
                }
                if(additionalList.size() > 0){
                    int t = forumService.addForumAdditional(additionalList);
                    if(t <= 0){
                        return Result.error("附加信息上传失败");
                    }else{
                        return Result.success("发表成功","null");
                    }
                }else{
                    return Result.success("发表成功","null");
                }
            }else {
                return Result.error("发表失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error("发表失败");
        }
    }

    @GetMapping("/get/{pageNum}/{pageSize}")
    public Result getForumItem(@RequestHeader("me_token") String token, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        Map<String, Object> map = JwtUtils.parseJwt(token);
        if(map == null){
            return Result.error("token无效");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ResponseForumItem> forumItems = forumService.getForumItem();
        if(forumItems != null){
            PageInfo list = new PageInfo(forumItems);
            JSONObject res = new JSONObject();
            System.out.println(list.getList());
            res.put("list", list.getList());
            res.put("size", list.getSize());
            res.put("total", list.getTotal());
            return Result.success("查询成功", res.toString());
        }else{
            return Result.error("查询失败");
        }

    }

    @GetMapping("/getById/{id}")
    public Result getForumItemById(@PathVariable("id") int id, @RequestHeader("me_token") String token){
        Map<String, Object> map = JwtUtils.parseJwt(token);
        if(map == null){
            return Result.error("token无效");
        }
        ResponseDetailForumItem forumItem = forumService.getForumItemById(id);
        if(forumItem != null){
            return Result.success("查询成功", JSONObject.toJSONString(forumItem));
        }else{
            return Result.error("查询失败");
        }
    }

    @PostMapping("/addCritic")
    public Result addForumCritic(@RequestBody ForumItemCritic forumCritic, @RequestHeader("me_token") String token){
        Map<String, Object> map = JwtUtils.parseJwt(token);
        if(map == null){
            return Result.error("token无效");
        }
        forumCritic.setCritic_author_id((Integer) map.get("id"));
        forumCritic.setCritic_time(new Timestamp(System.currentTimeMillis()));
        int num = forumService.addForumCritic(forumCritic);
        if(num > 0){
            return Result.success("评论成功","null");
        }else{
            return Result.error("评论失败");
        }
    }


}
