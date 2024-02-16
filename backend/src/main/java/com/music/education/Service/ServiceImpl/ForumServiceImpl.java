package com.music.education.Service.ServiceImpl;

import com.music.education.Entity.ForumItem;
import com.music.education.Mapper.ForumMapper;
import com.music.education.Service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumMapper forumMapper;

    @Override
    public int addForumItem(ForumItem forumItem) {
        return forumMapper.addForumItem(forumItem);
    }
}
