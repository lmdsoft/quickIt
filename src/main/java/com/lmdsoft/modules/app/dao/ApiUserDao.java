package com.lmdsoft.modules.app.dao;

import com.lmdsoft.modules.app.entity.ApiUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 系统用户表
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-05-03 09:41:38
 */
@Mapper
public interface ApiUserDao{
    /**
     * 根据id获取用户相关信息
     * @param id
     * @return
     */
    ApiUserEntity userInfo(String id);
}
