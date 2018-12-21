package com.lmdsoft.modules.activiti.service;

import com.lmdsoft.modules.activiti.entity.ExtendActTasklogEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-08-04 11:46:48
 */
public interface ExtendActTasklogService {
	
	ExtendActTasklogEntity queryObject(String id);
	
	List<ExtendActTasklogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ExtendActTasklogEntity extendActTasklog);
	
	void update(ExtendActTasklogEntity extendActTasklog);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 * 根据任务id 更改日志
	 * @param extendActTasklogEntity
	 * @return
	 */
	int updateByTaskId(ExtendActTasklogEntity extendActTasklogEntity);

	/**
	 * 转办任务时更新任务日志，有可能会存在多次转办，就会产生多条任务日志，所有这里用 taskId+appAction为空 作唯一
	 * @param extendActTasklogEntity
	 * @return
	 */
	int updateByTaskIdOpinion(ExtendActTasklogEntity extendActTasklogEntity);
}
