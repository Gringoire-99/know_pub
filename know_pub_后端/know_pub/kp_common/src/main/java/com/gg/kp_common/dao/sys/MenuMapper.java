package com.gg.kp_common.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gg.kp_common.entity.sys.Menu;
import com.gg.kp_common.utils.Authority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(String id);
}
