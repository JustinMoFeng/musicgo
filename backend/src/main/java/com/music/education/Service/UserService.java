package com.music.education.Service;

import com.music.education.Entity.User;

public interface UserService {
    int addUser(User user);

    boolean checkUser(User user);

    User getUserByUsername(String username);
}
