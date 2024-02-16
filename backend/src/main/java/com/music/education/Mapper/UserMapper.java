package com.music.education.Mapper;

import com.music.education.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user(username, password, nickname) values(#{username}, #{password}, #{nickname})")
    int addUser(User user);

    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    @Select("select * from user where id = #{id}")
    User getUserById(Integer id);
}
