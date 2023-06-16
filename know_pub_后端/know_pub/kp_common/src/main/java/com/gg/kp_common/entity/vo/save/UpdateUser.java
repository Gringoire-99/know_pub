package com.gg.kp_common.entity.vo.save;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {
    private String name;
    private String realName;
    private String avatar;
    private String description;
    private String industry;
    private String company;
    private String job;
    private String school;
    private String location;
    private String gender;
    private String resume;
    private String background;
}

