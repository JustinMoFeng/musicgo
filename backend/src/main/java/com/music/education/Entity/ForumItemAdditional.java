package com.music.education.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumItemAdditional {
    private int id;
    private int forum_item_id;

    private String file_name;

    private String file_type;

    private String file_url;

    private Boolean is_delete;
}
