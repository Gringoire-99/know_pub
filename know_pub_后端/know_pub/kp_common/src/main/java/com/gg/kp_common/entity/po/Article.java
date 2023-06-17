package com.gg.kp_common.entity.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Article {
    @Schema(description = "id", required = true)
    private String id;

    @Schema(description = "标题", required = true)
    private String title;

    @Schema(description = "内容(富文本)", required = true)
    private String content;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "图片，使用+分隔")
    private String images;

    @Schema(description = "标签名，使用+分隔")
    private String tags;
    @Schema(description = "点赞数", defaultValue = "0")
    private int likeCount;

    @Schema(description = "用户id", required = true)
    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "状态,0:正常,1:禁用", defaultValue = "0")
    private int status;

    @Schema(description = "状态,0:已发布,1:未发布", defaultValue = "0")
    private int isPublish;

    @Schema(description = "评论数", defaultValue = "0")
    private int commentCount;

    @Schema(description = "浏览数", defaultValue = "0")
    private int viewCount;
    @TableLogic(value = "0", delval = "1")
    @Schema(description = "删除标志,0:未删除,1:已删除", defaultValue = "0")
    private int delFlag;
}
