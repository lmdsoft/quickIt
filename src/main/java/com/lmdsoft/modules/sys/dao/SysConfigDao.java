package com.lmdsoft.modules.sys.dao;

import com.lmdsoft.modules.common.dao.BaseDao;
import com.lmdsoft.modules.oss.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 类SysConfigDao的功能描述:
 * 系统配置信息
 * @Auther lmdsoft
 * @Date 2018-08-25 16:14:20
 */
@Mapper
public interface SysConfigDao extends BaseDao<SysConfigEntity> {
	
	/**
	 * 根据key，查询value
	 */
	String queryByKey(String paramKey);
	
	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);
	
}
