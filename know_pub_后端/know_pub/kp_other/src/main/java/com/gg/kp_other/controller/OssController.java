package com.gg.kp_other.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.gg.kp_common.entity.vo.OssPolicy;
import com.gg.kp_common.utils.HttpEnum;
import com.gg.kp_common.utils.Result;
import com.gg.kp_common.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RequestMapping("/oss")
@RestController
public class OssController {
    @Resource
    OSSClient ossClient;

    @Value("${spring.cloud.alicloud.bucket}")
    public String bucket;
    @Value("${spring.cloud.alicloud.access-key}")
    public String accessId;
    @Value("${spring.cloud.alicloud.oss.endpoint}")
    public String endpoint;

    @RequestMapping("/policy")
    public Result<OssPolicy> policy() {

        // 填写Bucket名称，例如examplebucket。
        // 填写Host地址，格式为https://bucketname.endpoint。
        String host = "https://" + bucket + "." + endpoint;
        // 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。
        // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = format + "/";

        // 创建ossClient实例。
        try {
            long expireTime = 60*60;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);


            OssPolicy ossPolicy = new OssPolicy();
            ossPolicy.setOssaccessKeyId(accessId);
            ossPolicy.setPolicy(encodedPolicy);
            ossPolicy.setSignature(postSignature);
            ossPolicy.setDir(dir);
            ossPolicy.setHost(host);
            ossPolicy.setExpire(String.valueOf(expireEndTime / 1000));
            return Result.ok(ossPolicy);

        } catch (Exception e) {
            Result<OssPolicy> error = new Result<>();
            error.setStatus(HttpEnum.ERROR);
            error.setData(null);
            return error;
        }
    }

    @PostMapping("/upload-avatar")
    public Result<Integer> uploadAvatar(@RequestBody Map<String, Object> param) {
        String dir = (String) param.get("dir");
        String content = (String) param.get("content");
        ValidationUtils.validate().validateEmpty(dir, content);
        try {
            ossClient.putObject(bucket, dir, new ByteArrayInputStream(content.getBytes()));
        } catch (OSSException | ClientException e) {
            Result<Integer> result = new Result<>();
            result.setStatus(HttpEnum.ERROR);
            result.setData(0);
            return result;
        }
        return Result.ok(1);
    }


}
