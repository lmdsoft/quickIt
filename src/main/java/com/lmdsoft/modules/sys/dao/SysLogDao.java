package com.lmdsoft.modules.sys.dao;


import com.lmdsoft.modules.common.dao.BaseDao;
import com.lmdsoft.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类SysLogDao的功能描述:
 * 系统日志
 * @Auther lmdsoft
 * @Date 2018-08-25 16:14:57
 */
@Mapper
public interface SysLogDao extends BaseDao<SysLogEntity> {
	
}
