package com.luo.model.posting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comment")
public class Comment implements Serializable {
    @Id
    private String id;
    private Long userId;
    private Integer postId;
    private String username;
    private String content;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer replyCount;
    private Integer status;
    private String parentId;
    private Integer createTime;
    private Integer updateTime;
}
