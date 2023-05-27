package com.gg.kp_common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    IPage<QuestionVo> getQuestionVoPage(IPage<?> page);

}
