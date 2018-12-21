package com.lmdsoft.modules.demo.dao;

import com.lmdsoft.modules.common.dao.BaseDao;
import com.lmdsoft.modules.demo.entity.LeaveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 请假流程测试
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-07-31 13:33:23
 */
@Mapper
public interface LeaveDao extends BaseDao<LeaveEntity> {
	
}
