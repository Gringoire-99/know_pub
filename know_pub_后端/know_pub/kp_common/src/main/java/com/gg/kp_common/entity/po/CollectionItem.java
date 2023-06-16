package com.gg.kp_common.entity.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionItem {
    public static final String POST = "post";
    public static final String QUESTION = "question";
    public static final String ARTICLE = "article";
    public static final String TOPIC = "topic";
    public static final String COLLECTION = "collection";
    @Schema(description = "id", required = true)
    private String id;
    @Schema(description = "收藏夹id", required = true)
    private String collectionId;
    @Schema(description = "目标id", required = true)
    private String targetId;
    @Schema(description = "枚举类型-回答 问题 文章 话题 收藏夹", required = true)
    private String type;
    @Schema(description = "用户id", required = true)
    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "删除标志, 0:未删除, 1:已删除", defaultValue = "0")
    @TableLogic(value = "0", delval = "1")
    private int delFlag;
    @Schema(description = "状态, 0:正常, 1:禁用", defaultValue = "0")
    private int status;

}
