package com.gg.kp_main.controller;

import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.vo.ArticleVo;
import com.gg.kp_common.entity.vo.Policy;
import com.gg.kp_common.entity.vo.save.NewArticle;
import com.gg.kp_common.entity.vo.save.UpdateArticle;
import com.gg.kp_common.service.ArticleService;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/article-list")
    public Result<Page<ArticleVo>> articleList(@ApiParam PageParams pageParams, @RequestParam String userId) {
        return articleService.getArticleList(pageParams, userId);
    }
    @PatchMapping("/upload-image")
    @PreAuthorize("hasRole('USER')")
    public Result<Policy> uploadImage(@ApiParam String articleId) {
        return articleService.uploadImage(articleId);
    }
    @PatchMapping("/save-script")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> saveScript(@RequestBody UpdateArticle article) {
        return articleService.saveScript(article);
    }
    @PatchMapping("/publish-article")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> publishArticle(@RequestBody UpdateArticle article) {
        return articleService.publishArticle(article);
    }
}
