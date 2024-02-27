package com.music.education.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForumItemCritic {
    private int id;
    private int forum_item_id;
    private String critic_author_name;
    private String critic_author_avatar;
    private String critic_content;
    private Timestamp critic_time;


}
