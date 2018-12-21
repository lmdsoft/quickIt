package com.lmdsoft.modules.sys.service.impl;

import com.lmdsoft.modules.common.utils.UserUtils;
import com.lmdsoft.modules.common.common.Constant;
import com.lmdsoft.modules.common.exception.MyException;
import com.lmdsoft.modules.common.page.Page;
import com.lmdsoft.modules.common.page.PageHelper;
import com.lmdsoft.modules.common.utils.Utils;
import com.lmdsoft.modules.sys.dao.OrganDao;
import com.lmdsoft.modules.sys.entity.OrganEntity;
import com.lmdsoft.modules.sys.entity.UserEntity;
import com.lmdsoft.modules.sys.entity.UserWindowDto;
import com.lmdsoft.modules.sys.service.OrganService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("organService")
public class OrganServiceImpl implements OrganService {
	@Autowired
	private OrganDao organDao;
	
	@Override
	public OrganEntity queryObject(String id){
		return organDao.queryObject(id);
	}
	
	@Override
	public List<OrganEntity> queryList(Map<String, Object> map){
		return organDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return organDao.queryTotal(map);
	}
	
	@Override
	public String save(OrganEntity organ){
        UserEntity currentUser = UserUtils.getCurrentUser();
        setPublicSaveUpdate(organ);
	    organ.setId(Utils.uuid());
	    organ.setIsDel(Constant.YESNO.NO.getValue());
	    organ.setCreateTime(new Date());
	    organ.setCreateId(currentUser.getId());
	    organ.setBapid(currentUser.getBapid());
	    organ.setBaid(currentUser.getBaid());
        int count = organDao.save(organ);
        if(count>0){
            return organ.getId();
        }
        return "";

	}

    /**
     * 保存和更新,公共操作
     * @param organ
     */
	public void setPublicSaveUpdate(OrganEntity organ){
	    if(StringUtils.isEmpty(organ.getParentId())){
	        throw new MyException("父节点不能为空!");
        }
        OrganEntity organEntity = organDao.queryObject(organ.getParentId());
	    //组织为部门类型
        String depType = Constant.OrganType.DEPART.getValue();
        //如果父节点是部门，那子节点只能是部门类型
	    if( organEntity !=null && depType.equals(organEntity.getType()) && !depType.equals(organ.getType())){
            throw new MyException("父节点是部门,子节点只能是部门类型");
        }
        if(Constant.OrganType.DEPART.getValue().equals(organ.getType())){
            //归属机构
            String bapid = findBapid(organ.getParentId());
            organ.setBapid(bapid);
        }else {
            organ.setBapid("0");
        }
    }

    /**
     * 查询父机构
     * @param organId 组织id
     * @return
     */
    public  String findBapid(String organId){
        OrganEntity organEntity = organDao.queryObject(organId);
        //如果组织为机构或者根节点,停止递归,返回机构
        if(organEntity != null && (Constant.OrganType.ORGAN.getValue().equals(organEntity.getType()) || Constant.OrganType.CATALOG.getValue().equals(organEntity.getType()))){
            return organEntity.getId();
        }else{
            return findBapid(organEntity.getParentId());
        }
    }
	
	@Override
	public int update(OrganEntity organ){
        setPublicSaveUpdate(organ);
	    organ.setUpdateId(UserUtils.getCurrentUserId());
	    organ.setUpdateTime(new Date());
        return organDao.update(organ);
	}
	
	@Override
	public int delete(String id){
        return organDao.delete(id);
	}
	
	@Override
	public int deleteBatch(String[] ids){
        return organDao.deleteBatch(ids);
	}

	@Override
	public List<OrganEntity> queryListByBean(OrganEntity organEntity) {
	    organEntity.setIsDel(Constant.YESNO.NO.getValue());
        List<OrganEntity> organEntities = organDao.queryListByBean(organEntity);
        if(organEntities == null || organEntities.size()<1){
            OrganEntity organ = new OrganEntity();
            organ.setId(Utils.uuid());
            organ.setCreateId(UserUtils.getCurrentUserId());
            organ.setCreateTime(new Date());
            organ.setRemark("根节点");
            organ.setOpen("true");
            organ.setParentId("0");
            organ.setType(Constant.OrganType.CATALOG.getValue());
            organ.setIsDel(Constant.YESNO.NO.getValue());
            organ.setName("组织机构树");
            organ.setSysmark(organ.getId());
            organ.setSort("1");
            organ.setCode("0");
            int count = organDao.save(organ);
            if(count>0){
                organEntities.add(organ);
            }
        }
        return organEntities;
	}

    @Override
    public List<OrganEntity> queryListByCode(String code) {
        OrganEntity organEntity = new OrganEntity();
        organEntity.setIsDel(Constant.YESNO.NO.getValue());
        organEntity.setCode(code);
        return organDao.queryListByBean(organEntity);
    }

    @Transactional
    @Override
    public void updateIsdel(String ids, String type) {
	    for (String id:ids.split(",")){
            OrganEntity organEntity = new OrganEntity();
            organEntity.setId(id);
            organEntity.setIsDel(type);
            organDao.update(organEntity);
        }
    }

    @Override
    public Page<UserWindowDto> queryPageByDto(UserWindowDto userWindowDto, int pageNum) {
        PageHelper.startPage(pageNum,Constant.pageSize);
        organDao.queryPageByDto(userWindowDto);
        return PageHelper.endPage();
    }
}
