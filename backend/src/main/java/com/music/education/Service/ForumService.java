package com.music.education.Service;

import com.music.education.Entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ForumService {
    int addForumItem(ForumItem forumItem);

    @Transactional
    List<ResponseForumItem> getForumItem();


    @Transactional
    int addForumAdditional(List<ForumItemAdditional> additionalList);

    ResponseDetailForumItem getForumItemById(int id);

    int addForumCritic(ForumItemCritic forumCritic);

    List<ResponseForumItem> getMyForumItem(Integer id);

    void updateReplyNum(int forumItemId);
}
