package com.lmdsoft.modules.activiti.dao;

import com.lmdsoft.modules.activiti.entity.ExtendActNodesetEntity;
import com.lmdsoft.modules.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程节点配置
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-07-24 13:28:51
 */
@Mapper
public interface ExtendActNodesetDao extends BaseDao<ExtendActNodesetEntity> {
    /**
     * 根据nodeId查询节点信息
     * @param nodeId
     * @return
     */
    ExtendActNodesetEntity queryByNodeId(String nodeId);

    /**
     * 根据nodeId和模型id查询节点信息
     * @param nodeId
     * @param modelId
     * @return
     */
    ExtendActNodesetEntity queryByNodeIdModelId(@Param("nodeId") String nodeId,@Param("modelId") String modelId);

}
