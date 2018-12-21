package ${package}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lmdsoft.modules.common.utils.Utils;
import ${package}.dao.${className}Dao;
import ${package}.entity.${className}Entity;
import ${package}.service.${className}Service;



@Service("${classname}Service")
public class ${className}ServiceImpl implements ${className}Service {
	@Autowired
	private ${className}Dao ${classname}Dao;
	
	@Override
	public ${className}Entity queryObject(${pk.attrType} ${pk.attrname}){
		return ${classname}Dao.queryObject(${pk.attrname});
	}
	
	@Override
	public List<${className}Entity> queryList(Map<String, Object> map){
		return ${classname}Dao.queryList(map);
	}

    @Override
    public List<${className}Entity> queryListByBean(${className}Entity entity) {
        return ${classname}Dao.queryListByBean(entity);
    }
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ${classname}Dao.queryTotal(map);
	}
	
	@Override
	public int save(${className}Entity ${classname}){
        ${classname}.set${pk.attrName}(Utils.uuid());
		return ${classname}Dao.save(${classname});
	}
	
	@Override
	public int update(${className}Entity ${classname}){
        return ${classname}Dao.update(${classname});
	}
	
	@Override
	public int delete(${pk.attrType} ${pk.attrname}){
        return ${classname}Dao.delete(${pk.attrname});
	}
	
	@Override
	public int deleteBatch(${pk.attrType}[] ${pk.attrname}s){
        return ${classname}Dao.deleteBatch(${pk.attrname}s);
	}
	
}
