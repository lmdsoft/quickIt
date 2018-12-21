package com.lmdsoft.modules.oss.service;


import com.lmdsoft.modules.oss.entity.SysOssEntity;

import java.util.List;
import java.util.Map;

/**
 * 类SysOssService的功能描述:
 * 文件上传
 * @Auther lmdsoft
 * @Date 2018-08-25 16:13:56
 */
public interface SysOssService {
	
	SysOssEntity queryObject(Long id);
	
	List<SysOssEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysOssEntity sysOss);
	
	void update(SysOssEntity sysOss);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
