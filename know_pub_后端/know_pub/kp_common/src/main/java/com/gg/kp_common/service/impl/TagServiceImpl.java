package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.TagMapper;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Tag;
import com.gg.kp_common.entity.vo.TagVo;
import com.gg.kp_common.service.TagService;
import com.gg.kp_common.utils.BeanCopyUtils;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.PageUtils;
import com.gg.kp_common.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public Result<Page<TagVo>> getTags(PageParams params) {
        String keyword = params.getKeyword();
        LambdaQueryWrapper<Tag> lqw = new LambdaQueryWrapper<>();

        lqw.like(keyword != null, Tag::getName, keyword);

        IPage<Tag> tagIPage = this.baseMapper.selectPage(new PageUtils<Tag>().getPage(params), lqw);

        List<Tag> records = tagIPage.getRecords();

        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(records, TagVo.class);


        Page<TagVo> page = new Page<>(tagIPage.getTotal(), tagIPage.getSize(), tagVos);
        return Result.ok(page);
    }
}
