package com.music.education.Mapper;

import com.music.education.Entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface ForumMapper {

    @Insert("insert into forum(title, content, author, time, reply_num, like_num, book_link) values(#{title}, #{content}, #{author}, #{time}, #{reply_num}, #{like_num}, #{book_link})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Transactional
    int addForumItem(ForumItem forumItem);

    @Select("select forum.id id, title, content, nickname author_name, avatar_url author_avatar, time time, reply_num reply_num, like_num like_num from forum, user where forum.author = user.id order by time desc")
    List<ResponseForumItem> getForumItem();

    @Select("select * from forum_additional where forum_item_id = #{id}")
    List<ForumItemAdditional> getForumAdditionalById(int id);

    @Insert("<script>" +
            "insert into forum_additional(forum_item_id, file_name, file_type, file_url, is_delete) values" +
            "<foreach collection='list' item='item' index='index' separator=','>" +
            "(#{item.forum_item_id}, #{item.file_name}, #{item.file_type}, #{item.file_url}, #{item.is_delete})" +
            "</foreach>" +
            "</script>")
    @Transactional
    int addForumAdditional(List<ForumItemAdditional> additionalList);

    @Select("select forum.id id, title, content, nickname author_name, avatar_url author_avatar, time time, reply_num reply_num, like_num like_num, book_link from forum, user where forum.author = user.id and forum.id = #{id}")
    ResponseDetailForumItem getForumItemById(int id);

    @Select("select forum_critic.id id, forum_critic.critic_content critic_content, nickname critic_author_name, avatar_url critic_author_avatar, critic_time from forum_critic, user where forum_critic.critic_author_id = user.id and forum_critic.forum_item_id = #{id}")
    List<ResponseForumItemCritic> getForumCriticById(int id);

    @Insert("insert into forum_critic(forum_item_id, critic_author_id, critic_content, critic_time) values(#{forum_item_id}, #{critic_author_id}, #{critic_content}, #{critic_time})")
    int addForumCritic(ForumItemCritic forumCritic);

    @Select("select forum.id id, title, content, nickname author_name, avatar_url author_avatar, time time, reply_num reply_num, like_num like_num from forum, user where forum.author = user.id and forum.author = #{id} order by time desc")
    List<ResponseForumItem> getMyForumItem(Integer id);

    @Update("update forum set reply_num = reply_num + 1 where id = #{forumItemId}")
    void updateReplyNum(int forumItemId);
}
