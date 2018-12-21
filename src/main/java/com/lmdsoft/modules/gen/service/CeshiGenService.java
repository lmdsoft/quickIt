package com.lmdsoft.modules.gen.service;

import com.lmdsoft.modules.gen.entity.CeshiGenEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @date 2018-12-04 10:47:45
 */
public interface CeshiGenService {
	
	CeshiGenEntity queryObject(Long id);
	
	List<CeshiGenEntity> queryList(Map<String, Object> map);

    List<CeshiGenEntity> queryListByBean(CeshiGenEntity entity);
	
	int queryTotal(Map<String, Object> map);
	
	int save(CeshiGenEntity ceshiGen);
	
	int update(CeshiGenEntity ceshiGen);
	
	int delete(Long id);
	
	int deleteBatch(Long[] ids);
}
