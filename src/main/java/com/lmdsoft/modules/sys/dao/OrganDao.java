package com.lmdsoft.modules.sys.dao;

import com.lmdsoft.modules.common.dao.BaseDao;
import com.lmdsoft.modules.sys.entity.OrganEntity;
import com.lmdsoft.modules.sys.entity.UserWindowDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织表
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-07-14 13:42:42
 */
@Mapper
public interface OrganDao extends BaseDao<OrganEntity> {
    /**
     * 根据实体条件查询
     * @return
     */
    List<UserWindowDto> queryPageByDto(UserWindowDto userWindowDto);
}
