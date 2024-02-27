package com.music.education.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForumItem {
    private int id;
    private String title;
    private String content;
    private String author_name;
    private String author_avatar;
    private Timestamp time;
    private int reply_num;
    private int like_num;

    @Override
    public String toString() {
        return "ResponseForumItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author_name='" + author_name + '\'' +
                ", author_avatar='" + author_avatar + '\'' +
                ", time=" + time +
                ", reply=" + reply_num +
                ", like=" + like_num +
                '}';
    }
}
