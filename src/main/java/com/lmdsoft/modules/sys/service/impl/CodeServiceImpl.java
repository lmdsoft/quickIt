package com.lmdsoft.modules.sys.service.impl;


import com.lmdsoft.modules.common.utils.UserUtils;
import com.lmdsoft.modules.common.common.Constant;
import com.lmdsoft.modules.common.exception.MyException;
import com.lmdsoft.modules.common.utils.RedisUtil;
import com.lmdsoft.modules.common.utils.Utils;
import com.lmdsoft.modules.sys.dao.CodeDao;
import com.lmdsoft.modules.sys.entity.CodeEntity;
import com.lmdsoft.modules.sys.entity.UserEntity;
import com.lmdsoft.modules.sys.service.CodeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.lmdsoft.modules.common.utils.UserUtils.getCurrentUser;


@Service("codeService")
public class CodeServiceImpl implements CodeService {

    private static final Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

	@Autowired
	private CodeDao codeDao;
	
	public CodeEntity queryObject(String id){
		return codeDao.queryObject(id);
	}
	
	public List<CodeEntity> queryList(Map<String, Object> map){
		return codeDao.queryList(map);
	}
	
	public int queryTotal(Map<String, Object> map){
		return codeDao.queryTotal(map);
	}
	
	public String save(CodeEntity code){
        UserEntity currentUser = getCurrentUser();
        code.setBapid(currentUser.getBapid());
        code.setBaid(currentUser.getBaid());
	    code.setCreateTime(new Date());
	    code.setCreateId(currentUser.getId());
	    code.setId(Utils.uuid());
	    codeDao.save(code);
        try {
            updateCodeCahce(code,true);
            logger.info("新增数据字典:{} 缓存成功!",code.getName());
        } catch (Exception e) {
            logger.info("新增数据字典:{} 缓存失败!",code.getName());
            e.printStackTrace();
        }
        return code.getId();
	}
	
	public void update(CodeEntity code){
	    code.setUpdateTime(new Date());
	    code.setUpdateId(UserUtils.getCurrentUserId());
		codeDao.update(code);
        try {
            updateCodeCahce(code,false);
            logger.info("数据字典:{} 缓存成功!",code.getName());
        } catch (Exception e) {
            logger.warn("数据字典:{} 缓存失败!",code.getName());
            e.printStackTrace();
        }
	}

    /**
     * 更新/新增字典
     * @param code 字典实体
     * @param isSave 是否是保存
     */
	public  void updateCodeCahce(CodeEntity code,Boolean isSave) throws Exception {
        //更新缓存
        CodeEntity parentCode = codeDao.queryObject(code.getParentId());
//        Map<String,Map<String,Object>> allMap = CodeCache.get(Constant.CODE_CACHE);
        Map<String,Map<String,Object>> allMap = (Map<String, Map<String, Object>>) redisUtil.get(Constant.CODE_CACHE);
        Map<String,Object> parentMap = allMap.get(parentCode.getMark());
        //第一步取出父字典，添加新子字典
        List<Map<String, Object>> childList=(List<Map<String, Object>>) parentMap.get("childList");
        if(childList == null){
            childList = new ArrayList<Map<String, Object>>();
        }
        List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
        Map<String,Object> childMap = new HashMap<String,Object>();
        childMap.put("id",code.getId());
        childMap.put("value",code.getValue());
        childMap.put("mark",code.getMark());
        childMap.put("name",code.getName());
        if(isSave){
            childMap.put("childList",tempList);
            childList.add(childMap);
        }else {
            //父节点所有子字典中，找出当前修改的子节点
            for (int i=0;i<childList.size();i++){
                if(childList.get(i).get("id").equals(code.getId())){
                    childList.remove(i);
                    childList.add(childMap);
                }
            }
        }
        //更新当前字典
        allMap.put(code.getMark(),childMap);
        //更新父类字典
        parentMap.put("childList",childList);
        allMap.put(parentCode.getMark(),parentMap);
//        CodeCache.put(Constant.CODE_CACHE,allMap);
        redisUtil.set(Constant.CODE_CACHE,allMap);
    }


	public void delete(String id){
		codeDao.delete(id);
	}
	
	public void deleteBatch(String[] ids){
//        Map<String,Map<String,Object>> allMap = CodeCache.get(Constant.CODE_CACHE);
        Map<String,Map<String,Object>> allMap = null;
        try {
            allMap = (Map<String, Map<String, Object>>) redisUtil.get(Constant.CODE_CACHE);
            logger.info("读取数据字典缓存成功!");
        } catch (Exception e) {
            logger.warn("读取数据字典缓存失败!");
            e.printStackTrace();
        }
        for (String id:ids){
            //清除该字典的缓存
            CodeEntity code = codeDao.queryObject(id);
            CodeEntity parentCode = codeDao.queryObject(code.getParentId());
            //移除字典
            allMap.remove(code.getMark());
            Map<String,Object> parentMap = allMap.get(parentCode.getMark());
            //如果父级字典不为空，则移出子字典list
            if(parentMap != null){
                List<Map<String,Object>> childList = (List<Map<String, Object>>) parentMap.get("childList");
                //移除父字典中的子字典
                for (int i=0;i<childList.size();i++){
                    if(childList.get(i).get("id").equals(code.getId())){
                        childList.remove(i);
                    }
                }
                allMap.put(parentCode.getMark(),parentMap);
            }
        }
//        CodeCache.put(Constant.CODE_CACHE,allMap);
        try {
            redisUtil.set(Constant.CODE_CACHE,allMap);
            logger.info("更新数据字典缓存成功!");
        } catch (Exception e) {
            logger.warn("更新数据字典缓存失败!");
            e.printStackTrace();
        }
        codeDao.deleteBatch(ids);
	}

	public List<CodeEntity> queryListByBean(CodeEntity codeEntity) {
        List<CodeEntity> codeList = codeDao.queryListByBean(codeEntity);
        UserEntity currentUser = getCurrentUser();
        if(codeList == null || codeList.size()<1){
            CodeEntity code = new CodeEntity();
            code.setId(Utils.uuid());
            code.setMark("初始化");
            code.setRemark("字典根据结点");
            code.setType("1");
            code.setName("字典管理树");
            code.setParentId("0");
            code.setSort("1");
            code.setOpen("true");
            code.setCreateId(currentUser.getId());
            code.setCreateTime(new Date());
            int count = codeDao.save(code);
            if(count>0){
                codeList.add(code);
            }
        }
        return codeList;
	}

    public List<CodeEntity> queryListByCode(CodeEntity codeEntity) {
        return codeDao.queryListByBean(codeEntity);
    }

	public List<CodeEntity> queryAllCode() {
		return codeDao.queryAllCode();
	}

    public CodeEntity queryByMark(String mark) {
	    if(StringUtils.isEmpty(mark)){
	        throw new MyException("字典标识不能为空!");
        }
        return codeDao.queryByMark(mark);
    }

    public List<CodeEntity> queryChildsByMark(String mark) {
	    if(StringUtils.isEmpty(mark)){
	        throw new MyException("字典标识不能为空");
        }
        return codeDao.queryChildsByMark(mark);
    }
}
