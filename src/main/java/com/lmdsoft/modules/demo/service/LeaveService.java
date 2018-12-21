package com.lmdsoft.modules.demo.service;


import com.lmdsoft.modules.common.page.Page;
import com.lmdsoft.modules.demo.entity.LeaveEntity;

import java.util.List;
import java.util.Map;

/**
 * 请假流程测试
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-07-31 13:33:23
 */
public interface LeaveService {
	
	LeaveEntity queryObject(String id);
	
	List<LeaveEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(LeaveEntity leave);
	
	void update(LeaveEntity leave);
	
	int delete(String id);
	
	void deleteBatch(String[] ids);

	Page<LeaveEntity> findPage(LeaveEntity leaveEntity, int pageNum);
}
