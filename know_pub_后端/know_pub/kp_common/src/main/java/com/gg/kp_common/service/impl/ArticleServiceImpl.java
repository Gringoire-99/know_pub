package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.ArticleMapper;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Article;
import com.gg.kp_common.entity.vo.ArticleVo;
import com.gg.kp_common.service.ArticleService;
import com.gg.kp_common.utils.BeanCopyUtils;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.PageUtils;
import com.gg.kp_common.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public Result<Page<ArticleVo>> getArticleList(PageParams pageParams, String userId) {
        IPage<Article> page = new PageUtils<Article>().getPage(pageParams);
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getUserId, userId);
        IPage<Article> articleIPage = this.page(page, lqw);
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(articleIPage.getRecords(), ArticleVo.class);
        Page<ArticleVo> articleVoPage = new Page<>(articleIPage.getTotal(), articleIPage.getSize(), articleVos);
        return Result.ok(articleVoPage);
    }

    @Override
    public Result<Integer> uploadImage(MultipartFile file, String articleId) {
        return Result.ok(0);
    }
}
