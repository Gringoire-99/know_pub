package com.gg.kp_common.entity.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser {
    @Schema(required = true)
    private String username;
    @Schema(required = true)
    private String password;
}
