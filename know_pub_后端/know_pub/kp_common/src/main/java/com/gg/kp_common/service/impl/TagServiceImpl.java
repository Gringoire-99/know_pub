package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.TagMapper;
import com.gg.kp_common.entity.po.Tag;
import com.gg.kp_common.entity.vo.TagVo;
import com.gg.kp_common.service.TagService;
import com.gg.kp_common.utils.BeanCopyUtils;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.PageUtils;
import com.gg.kp_common.utils.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public Result<Map<String, Object>> getTags(PageParams params) {
        String keyword = params.getKeyword();
        LambdaQueryWrapper<Tag> lqw = new LambdaQueryWrapper<>();

        lqw.like(keyword != null, Tag::getName, keyword);

        IPage<Tag> tagIPage = this.baseMapper.selectPage(new PageUtils<Tag>().getPage(params), lqw);

        List<Tag> records = tagIPage.getRecords();

        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(records, TagVo.class);

        HashMap<String, Object> result = new HashMap<>();
        result.put(PageUtils.PAGE, tagVos);
        result.put(PageUtils.TOTAL, tagIPage.getTotal());
        result.put(PageUtils.ROWS, tagVos.size());
        return Result.ok(result);
    }
}
