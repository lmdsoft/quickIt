package com.lmdsoft.modules.gen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lmdsoft.modules.common.utils.Utils;
import com.lmdsoft.modules.gen.dao.CeshiGenDao;
import com.lmdsoft.modules.gen.entity.CeshiGenEntity;
import com.lmdsoft.modules.gen.service.CeshiGenService;



@Service("ceshiGenService")
public class CeshiGenServiceImpl implements CeshiGenService {
	@Autowired
	private CeshiGenDao ceshiGenDao;
	
	@Override
	public CeshiGenEntity queryObject(Long id){
		return ceshiGenDao.queryObject(id);
	}
	
	@Override
	public List<CeshiGenEntity> queryList(Map<String, Object> map){
		return ceshiGenDao.queryList(map);
	}

    @Override
    public List<CeshiGenEntity> queryListByBean(CeshiGenEntity entity) {
        return ceshiGenDao.queryListByBean(entity);
    }
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ceshiGenDao.queryTotal(map);
	}
	
	@Override
	public int save(CeshiGenEntity ceshiGen){
//        ceshiGen.setId(Utils.uuid());
		return ceshiGenDao.save(ceshiGen);
	}
	
	@Override
	public int update(CeshiGenEntity ceshiGen){
        return ceshiGenDao.update(ceshiGen);
	}
	
	@Override
	public int delete(Long id){
        return ceshiGenDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Long[] ids){
        return ceshiGenDao.deleteBatch(ids);
	}
	
}
