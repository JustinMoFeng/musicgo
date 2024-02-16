package com.music.education.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ForumItem {
    private int id;
    private String title;
    private String content;
    private String author;
    private Timestamp time;
    private int reply;
    private int like;
    private int type;
}
