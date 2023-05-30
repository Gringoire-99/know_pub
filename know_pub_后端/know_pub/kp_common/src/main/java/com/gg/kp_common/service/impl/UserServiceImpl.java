package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.UserMapper;
import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.po.UserDetail;
import com.gg.kp_common.entity.vo.*;
import com.gg.kp_common.feign.OssFeignClient;
import com.gg.kp_common.service.UserService;
import com.gg.kp_common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private OssFeignClient ossFeignClient;

    public Result<Integer> testFeign() {
      return Result.ok(ossFeignClient.test());
    }

    @Override
    public Result<UserVo> login(User user) {
//      需要被验证的对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//      使用manager验证对象
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }
//      获取查询到的具体对象和生成的token
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

//      将token保存到redis,并生成视图对象返回
        String id = userDetail.getUser().getId();
        String jwt = JwtUtil.createJWT(id);
        redisCache.setCacheObject("userId:" + id, userDetail);
        UserInfoDetailVo userInfoDetailVo = new UserInfoDetailVo();
        BeanUtils.copyProperties(userDetail.getUser(), userInfoDetailVo);
        UserVo userVo = new UserVo(jwt, userInfoDetailVo);
        return Result.ok(userVo);
    }

    @Override
    public Result<UserInfoDetailVo> infoDetail(String userId) {
        User user = getUserById(userId);

        UserInfoDetailVo userInfoDetailVo = new UserInfoDetailVo();
        BeanUtils.copyProperties(user, userInfoDetailVo);
        return Result.ok(userInfoDetailVo);
    }

    @Override
    public Result<?> logout() {

        String userid = SecurityUtils.getId();
        redisCache.deleteObject("userId:" + userid);
        return Result.ok("退出成功");
    }

    @Override
    public Result<?> register(RegisterUser user) {
        String username = user.getUsername();
        String password = user.getPassword();


        ValidationUtils.validate()
                .validateEmpty(username, password)
                .ValidateUsername(username)
                .validatePassword(password);


        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone, username).or().eq(User::getEmail, username);

        if (!Objects.isNull(this.baseMapper.selectOne(lqw))) {
            throw new SystemException("该用户已注册");
        }


        User u = new User(username, password);
        if (username.contains("@")) {
            u.setEmail(username);
        } else {
            u.setPhone(username);
        }
        String id = JwtUtil.getUUID();
        u.setId(id);
        u.setName("用户_" + id.substring(0, 10));
        u.setPassword(new BCryptPasswordEncoder().encode(password));
        this.baseMapper.insert(u);
        this.baseMapper.addUserRole(u.getId());

        return Result.ok("注册成功");
    }

    @Override
    public Result<UserInfoShortVo> infoShort(String userId) {
        User user = getUserById(userId);
        UserInfoShortVo shortVo = new UserInfoShortVo();
        BeanUtils.copyProperties(user, shortVo);
        return Result.ok(shortVo);
    }

    @Override
    public Result<UserPostCardVo> postCard(String userId) {
        User user = getUserById(userId);
        UserPostCardVo postCardVo = new UserPostCardVo();
        BeanUtils.copyProperties(user, postCardVo);
        String selfId = SecurityUtils.getId();
        if (selfId == null || selfId.equals(userId)) {
            postCardVo.setIsFollowed(false);
        } else {
            Integer followed = this.baseMapper.isFollowed(userId, selfId);
            postCardVo.setIsFollowed(followed == 1);
        }
        return Result.ok(postCardVo);
    }

    @Override
    public Result<Integer> follow(String userId) {
        String selfId = SecurityUtils.getId();
        if (selfId != null && selfId.equals(userId)) {
            throw new SystemException("不能关注自己");
        }
        Integer followed = this.baseMapper.isFollowed(userId, selfId);
        int result;
        if (followed == 1) {
            this.baseMapper.unfollow(userId, selfId);
            result = 0;
        } else {
            this.baseMapper.follow(userId, selfId);
            result = 1;
        }
        return Result.ok(result);

    }

    @Override
    public Result<List<UserInfoShortVo>> getRecommendedUser() {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.last("limit 12");
        List<User> users = this.baseMapper.selectList(lqw);
        List<UserInfoShortVo> userVos = BeanCopyUtils.copyBeanList(users, UserInfoShortVo.class);
        return Result.ok(userVos);

    }

    @Transactional
    @Override
    public Result<Integer> updateUserInfo(UpdateUser user) {
        String userId = SecurityUtils.getId();
        boolean isUpdateAvatar = false;
        String avatarDir;
        User updateUser = new User();
        BufferedImage image;
        updateUser.setId(userId);
        BeanUtils.copyProperties(user, updateUser);
        if (!"".equals(user.getAvatar()) && user.getAvatar() != null) {
//            需要上传头像
//            读取文件
            String imageUrl = user.getAvatar();
            image = isValidImage(imageUrl);
            avatarDir = "/user/" + userId + "/avatar." + getFileFormat(imageUrl);
            isUpdateAvatar = true;
        }

        int result = 0;
        try {
            result = this.baseMapper.updateById(updateUser);
            if (isUpdateAvatar) {

            }
        } catch (Exception e) {
            throw new SystemException("用户信息更新异常");
        }

        return Result.ok(result);
    }

    private BufferedImage isValidImage(String imageUrl) {
        BufferedImage image;
        URL url = null;
        try {
            url = new URL(imageUrl);
            image = ImageIO.read(url);

        } catch (IOException e) {
            throw new SystemException("url无效");
        }

        // 检查图片是否有效
        if (image == null) {
            throw new SystemException("图片无效");
        }

        // 检查图片格式
        String format = getFileFormat(imageUrl);
        if (format == null || !(format.equals("jpg") || format.equals("png"))) {
            throw new SystemException("格式无效");
        }

        // 检查图片大小
        int width = image.getWidth();
        int height = image.getHeight();
        int maxDimension = 1000; // 设置最大尺寸阈值
        if (width > maxDimension || height > maxDimension) {
            throw new SystemException("图片过大");
        }

        return image;
    }

    private String getFileFormat(String imageUrl) {
        String[] formatNames = ImageIO.getReaderFormatNames();
        for (String format : formatNames) {
            if (imageUrl.toLowerCase().endsWith(format)) {
                return format;
            }
        }
        return null;
    }

    private User getUserById(String userId) {
        User user = this.baseMapper.selectById(userId);
        if (Objects.isNull(user)) {
            throw new SystemException(HttpEnum.ERROR, "用户不存在");
        }
        return user;
    }
}
