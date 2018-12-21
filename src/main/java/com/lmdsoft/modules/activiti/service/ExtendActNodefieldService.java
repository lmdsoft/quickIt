package com.lmdsoft.modules.activiti.service;

import com.lmdsoft.modules.activiti.entity.ExtendActNodefieldEntity;

import java.util.List;
import java.util.Map;

/**
 * 流程节点对应的字段权限表
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-07-24 13:28:51
 */
public interface ExtendActNodefieldService {
	
	ExtendActNodefieldEntity queryObject(String id);
	
	List<ExtendActNodefieldEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(ExtendActNodefieldEntity extendActNodefield);
	
	void update(ExtendActNodefieldEntity extendActNodefield);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 * 根据节点集合查询
	 * @param nodes
	 * @return
	 */
	List<ExtendActNodefieldEntity> queryByNodes(List<String> nodes);

}
