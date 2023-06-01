package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewPost {

    private String question;

    private String questionId;

    private String content;

    private String cover = "";

    private String images = "";
}
