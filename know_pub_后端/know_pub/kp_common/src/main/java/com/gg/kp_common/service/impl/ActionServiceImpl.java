package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.ActionMapper;
import com.gg.kp_common.entity.po.Action;
import com.gg.kp_common.service.ActionService;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements ActionService {

}
