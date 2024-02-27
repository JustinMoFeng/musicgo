package com.music.education.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDetailForumItem {
    private int id;
    private String title;
    private String content;
    private String author_name;
    private String author_avatar;
    private Timestamp time;
    private int reply;
    private int like;
    private List<ForumItemAdditional> additionalList;

    private List<ResponseForumItemCritic> criticList;
    private String book_link;

    @Override
    public String toString() {
        return "ResponseDetailForumItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author_name='" + author_name + '\'' +
                ", author_avatar='" + author_avatar + '\'' +
                ", time=" + time +
                ", reply=" + reply +
                ", like=" + like +
                ", additionalList=" + additionalList +
                ", criticList=" + criticList +
                '}';
    }
}
