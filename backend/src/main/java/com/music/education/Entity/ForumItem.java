package com.music.education.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumItem {
    private int id;
    private String title;
    private String content;
    private int author;
    private Timestamp time;
    private int reply_num;
    private int like_num;
    private String book_link;

    @Override
    public String toString() {
        return "ForumItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", time=" + time +
                ", reply_num=" + reply_num +
                ", like_num=" + like_num +
                ", book_link=" + book_link +
                '}';
    }
}
