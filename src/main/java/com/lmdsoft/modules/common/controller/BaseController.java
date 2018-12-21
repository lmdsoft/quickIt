package com.lmdsoft.modules.common.controller;


import com.lmdsoft.modules.common.utils.UserUtils;
import com.lmdsoft.modules.sys.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类的功能描述.
 * 公共的控件器，可在类中实现一些共同的方法和属性 持续更新中
 * @Auther lmdsoft
 * @Date 2018/4/28
 */
public class BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取当前登陆用户
     * @return
     */
    public UserEntity getUser(){
        return UserUtils.getCurrentUser();
    }

    /**
     * 获取当前登陆用户Id
     * @return
     */
    public String getUserId(){
        UserEntity user = getUser();
        if(null != user && null !=user.getId()){
            return user.getId();
        }
        return "";
    }


}
