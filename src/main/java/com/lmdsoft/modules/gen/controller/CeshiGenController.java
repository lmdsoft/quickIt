package com.lmdsoft.modules.gen.controller;

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

import com.lmdsoft.modules.gen.entity.CeshiGenEntity;
import com.lmdsoft.modules.gen.service.CeshiGenService;
import com.lmdsoft.modules.common.utils.PageUtils;
import com.lmdsoft.modules.common.utils.Query;
import com.lmdsoft.modules.common.utils.Result;


/**
 * 
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @date 2018-12-04 10:47:45
 */
@RestController
@RequestMapping("/gen/ceshigen")
public class CeshiGenController extends BaseController{
	@Autowired
	private CeshiGenService ceshiGenService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ceshigen:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CeshiGenEntity> ceshiGenList = ceshiGenService.queryList(query);
		int total = ceshiGenService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ceshiGenList, total, query.getLimit(), query.getPage());
		
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ceshigen:info")
	public Result info(@PathVariable("id") Long id){
		CeshiGenEntity ceshiGen = ceshiGenService.queryObject(id);
		
		return Result.ok().put("ceshiGen", ceshiGen);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ceshigen:save")
	public Result save(@RequestBody CeshiGenEntity ceshiGen){
		ceshiGenService.save(ceshiGen);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ceshigen:update")
	public Result update(@RequestBody CeshiGenEntity ceshiGen){
		ceshiGenService.update(ceshiGen);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ceshigen:delete")
	public Result delete(@RequestBody Long[] ids){
		ceshiGenService.deleteBatch(ids);
		
		return Result.ok();
	}
	
}
