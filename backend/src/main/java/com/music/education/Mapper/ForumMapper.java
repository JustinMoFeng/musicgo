package com.music.education.Mapper;

import com.music.education.Entity.ForumItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumMapper {

    @Insert("insert into forum_item(title, content, author, time, reply_num, like_num, type) values(#{title}, #{content}, #{author}, #{time}, #{reply}, #{like}, #{type})")
    int addForumItem(ForumItem forumItem);
}
