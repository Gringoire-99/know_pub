package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.PostActionMapper;
import com.gg.kp_common.entity.po.PostAction;
import com.gg.kp_common.service.PostActionService;
import org.springframework.stereotype.Service;

@Service
public class PostActionServiceImpl extends ServiceImpl<PostActionMapper, PostAction> implements PostActionService {

}
