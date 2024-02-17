package com.music.education.Service.ServiceImpl;

import com.music.education.Entity.*;
import com.music.education.Mapper.ForumMapper;
import com.music.education.Service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumMapper forumMapper;

    @Override
    public int addForumItem(ForumItem forumItem) {
        return forumMapper.addForumItem(forumItem);
    }

    @Override
    @Transactional
    public List<ResponseForumItem> getForumItem() {
        List<ResponseForumItem> forumList = forumMapper.getForumItem();
        return forumList;
    }

    @Override
    @Transactional
    public int addForumAdditional(List<ForumItemAdditional> additionalList) {
        return forumMapper.addForumAdditional(additionalList);
    }

    @Override
    public ResponseDetailForumItem getForumItemById(int id) {
        ResponseDetailForumItem forumItem = forumMapper.getForumItemById(id);
        List<ForumItemAdditional> additionalList = forumMapper.getForumAdditionalById(id);
        if(additionalList==null || additionalList.isEmpty()){
            forumItem.setAdditionalList(new ArrayList<ForumItemAdditional>());
        }else {
            forumItem.setAdditionalList(additionalList);
        }
        List<ResponseForumItemCritic> criticList = forumMapper.getForumCriticById(id);
        if(criticList==null || criticList.isEmpty()){
            forumItem.setCriticList(new ArrayList<ResponseForumItemCritic>());
        }else {
            forumItem.setCriticList(criticList);
        }
        return forumItem;
    }

    @Override
    public int addForumCritic(ForumItemCritic forumCritic) {
        return forumMapper.addForumCritic(forumCritic);
    }
}
