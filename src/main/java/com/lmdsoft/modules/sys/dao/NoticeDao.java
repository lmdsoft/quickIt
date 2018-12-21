package com.lmdsoft.modules.sys.dao;


import com.lmdsoft.modules.common.dao.BaseDao;
import com.lmdsoft.modules.sys.entity.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通知
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-08-31 15:59:09
 */
@Mapper
public interface NoticeDao extends BaseDao<NoticeEntity> {

    /**
     * 我的通知列表
     * @param noticeEntity
     * @return
     */
    List<NoticeEntity> findMyNotice(NoticeEntity noticeEntity);
}
