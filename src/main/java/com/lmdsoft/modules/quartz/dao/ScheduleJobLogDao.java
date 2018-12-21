package com.lmdsoft.modules.quartz.dao;


import com.lmdsoft.modules.common.dao.BaseDao;
import com.lmdsoft.modules.quartz.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类ScheduleJobLogDao的功能描述:
 * 定时任务日志
 * @Auther lmdsoft
 * @Date 2018-08-25 16:13:41
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}
