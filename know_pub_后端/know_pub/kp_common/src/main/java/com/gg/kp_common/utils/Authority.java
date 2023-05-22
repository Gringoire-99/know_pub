package com.gg.kp_common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {
    private String perms;
    @Override
    public String getAuthority() {
        return perms;
    }
}
