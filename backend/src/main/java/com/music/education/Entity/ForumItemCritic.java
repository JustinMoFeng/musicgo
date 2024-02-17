package com.music.education.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumItemCritic {
    private int id;

    private int forum_item_id;

    private int critic_author_id;

    private String critic_content;

    private Timestamp critic_time;
}
