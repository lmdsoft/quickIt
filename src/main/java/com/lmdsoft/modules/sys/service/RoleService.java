package com.lmdsoft.modules.sys.service;


import com.lmdsoft.modules.common.page.Page;
import com.lmdsoft.modules.sys.entity.RoleEntity;
import com.lmdsoft.modules.sys.entity.UserWindowDto;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-05-03 10:07:59
 */
public interface RoleService {
	
	RoleEntity queryObject(String id);
	
	List<RoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RoleEntity role) throws Exception;
	
	void update(RoleEntity role);
	
	void delete(String id);
	
	void deleteBatch(String[] ids) throws Exception;

	/**
	 * 根据用户id查询用户所有的可用角色
	 * @param userId
	 * @param status
	 * @return
	 */
	List<RoleEntity> queryByUserId(String userId, String status);

	/**
	 * 根据角色id查询可用的组织机构
	 * @param roleId 角色id
	 * @return
	 */
	List<String> queryOrganRoleByRoleId(String roleId);

	/**
	 * 分页查询角色审批选择范围
	 * @return
	 */
	Page<UserWindowDto> queryPageByDto(UserWindowDto userWindowDto, int pageNmu);

	/**
	 * 批量更新用户状态
	 * @param status 状态(0正常 -1禁用)
	 * @return
	 */
	int updateBatchStatus(String[] ids, String status);
}
