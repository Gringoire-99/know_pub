package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.ArticleMapper;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Article;
import com.gg.kp_common.entity.vo.ArticleVo;
import com.gg.kp_common.entity.vo.OssPolicy;
import com.gg.kp_common.entity.vo.Policy;
import com.gg.kp_common.entity.vo.save.UpdateArticle;
import com.gg.kp_common.feign.OssFeignClient;
import com.gg.kp_common.service.ArticleService;
import com.gg.kp_common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private OssFeignClient ossFeignClient;

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
    @Transactional
    public Result<Policy> uploadImage(String articleId) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getId, articleId);
        Article article = this.getOne(lqw);
        Article temp;
        String id;
        if (Objects.isNull(article)) {
            String userId = SecurityUtils.getId();
            temp = new Article();
            id = UUID.randomUUID().toString();
            temp.setId(id);
            temp.setTitle("临时文章" + id.substring(0, 5));
            temp.setUserId(userId);
            temp.setContent("");
            temp.setIsPublish(EntityConstant.NOT_PUBLISHED);
            this.save(temp);
        } else {
            id = article.getId();
        }
        Result<OssPolicy> policy = ossFeignClient.policy();
        Policy policy1 = new Policy();
        if (Objects.nonNull(policy.getData())) {
            policy1.setOssPolicy(policy.getData());
        } else {
            policy1.setOssPolicy(null);
        }
        policy1.getExtraData().put("articleId", id);
        return Result.ok(policy1);
    }

    @Override
    @Transactional
    public Result<Integer> saveScript(UpdateArticle article) {
        Article temp = BeanCopyUtils.copyBean(article, Article.class);
        temp.setIsPublish(EntityConstant.NOT_PUBLISHED);
        boolean b = this.updateById(temp);
        return Result.ok(b ? 1 : 0);
    }

    @Override
    public Result<Integer> publishArticle(UpdateArticle article) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getId, article.getId());
        lqw.eq(Article::getUserId, SecurityUtils.getId());
        Article published = new Article();
        BeanUtils.copyProperties(article, published);
        published.setIsPublish(EntityConstant.PUBLISHED);
        boolean save = this.saveOrUpdate(published, lqw);
        return Result.ok(save ? 1 : 0);
    }

    @Override
    public Result<ArticleVo> getArticle(String articleId) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getId, articleId);
        Article article = this.getOne(lqw);
        if (Objects.isNull(article)) {
            throw new SystemException("文章不存在");
        }
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);

        return Result.ok(articleVo);
    }
}
