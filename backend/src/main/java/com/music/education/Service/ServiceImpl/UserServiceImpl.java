package com.music.education.Service.ServiceImpl;

import com.music.education.Entity.User;
import com.music.education.Mapper.UserMapper;
import com.music.education.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        User trueUser = getUserByUsername(user.getUsername());
        if(trueUser != null){
            return -1;
        }
        int num = userMapper.addUser(user);
        return num;
    }

    @Override
    public boolean checkUser(User user) {
        User trueUser = userMapper.getUserByUsername(user.getUsername());
        return trueUser != null && trueUser.getPassword().equals(user.getPassword());
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
