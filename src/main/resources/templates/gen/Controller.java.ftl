package ${package}.controller;

import java.util.List;
import java.util.Map;

import com.lmdsoft.modules.common.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package}.entity.${className}Entity;
import ${package}.service.${className}Service;
import ${utils}.PageUtils;
import ${utils}.Query;
import ${utils}.Result;


/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
@RestController
@RequestMapping("/${module}/${pathName}")
public class ${className}Controller extends BaseController{
	@Autowired
	private ${className}Service ${classname}Service;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("${pathName}:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<${className}Entity> ${classname}List = ${classname}Service.queryList(query);
		int total = ${classname}Service.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(${classname}List, total, query.getLimit(), query.getPage());
		
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{${pk.attrname}}")
	@RequiresPermissions("${pathName}:info")
	public Result info(@PathVariable("${pk.attrname}") ${pk.attrType} ${pk.attrname}){
		${className}Entity ${classname} = ${classname}Service.queryObject(${pk.attrname});
		
		return Result.ok().put("${classname}", ${classname});
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("${pathName}:save")
	public Result save(@RequestBody ${className}Entity ${classname}){
		${classname}Service.save(${classname});
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("${pathName}:update")
	public Result update(@RequestBody ${className}Entity ${classname}){
		${classname}Service.update(${classname});
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("${pathName}:delete")
	public Result delete(@RequestBody ${pk.attrType}[] ${pk.attrname}s){
		${classname}Service.deleteBatch(${pk.attrname}s);
		
		return Result.ok();
	}
	
}
