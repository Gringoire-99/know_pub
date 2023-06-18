package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Article;
import com.gg.kp_common.entity.vo.ArticleVo;
import com.gg.kp_common.entity.vo.Policy;
import com.gg.kp_common.entity.vo.save.NewArticle;
import com.gg.kp_common.entity.vo.save.UpdateArticle;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService extends IService<Article> {
    Result<Page<ArticleVo>> getArticleList(PageParams pageParams, String userId);

    Result<Policy> uploadImage(String articleId);

    Result<Integer> saveScript(UpdateArticle article);

    Result<Integer> publishArticle(UpdateArticle article);
}
