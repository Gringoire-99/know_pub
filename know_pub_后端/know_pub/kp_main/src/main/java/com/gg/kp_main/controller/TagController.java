package com.gg.kp_main.controller;

import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.vo.TagVo;
import com.gg.kp_common.service.TagService;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;
    @GetMapping("/tags")
    public Result<Page<TagVo>> getTags(@ApiParam PageParams params) {
        return tagService.getTags(params);
    }
}
