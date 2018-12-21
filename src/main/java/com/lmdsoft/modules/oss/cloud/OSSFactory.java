package com.lmdsoft.modules.oss.cloud;

import com.lmdsoft.modules.common.common.Constant;
import com.lmdsoft.modules.common.utils.SpringContextUtils;
import com.lmdsoft.modules.sys.service.SysConfigService;

/**
 * 类OSSFactory的功能描述:
 * 文件上传Factory
 * @Auther lmdsoft
 * @Date 2018-08-25 16:18:59
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(Constant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == Constant.CloudService.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

}
