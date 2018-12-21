package com.lmdsoft.modules.sys.dao;


import com.lmdsoft.modules.common.dao.BaseDao;
import com.lmdsoft.modules.sys.entity.CodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统数据字典
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-07-14 13:42:42
 */
@Mapper
public interface CodeDao extends BaseDao<CodeEntity> {
    /**
     * 查询所有的字典及子字典(用做字典缓存)
     * @return
     */
    List<CodeEntity> queryAllCode();

    /**
     * 查询所有的字典及子字典(用做字典缓存)
     * @return
     */
    List<CodeEntity> queryChildsByMark(String mark);

    /**
     * 根据字典标识查询
     * @param mark
     * @return
     */
    CodeEntity queryByMark(String mark);


}
