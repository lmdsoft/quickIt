package com.lmdsoft.modules.sys.controller;

import com.lmdsoft.modules.common.annotation.SysLog;
import com.lmdsoft.modules.common.controller.BaseController;
import com.lmdsoft.modules.common.utils.PageUtils;
import com.lmdsoft.modules.common.utils.Query;
import com.lmdsoft.modules.common.utils.Result;
import com.lmdsoft.modules.common.validator.ValidatorUtils;
import com.lmdsoft.modules.oss.entity.SysConfigEntity;
import com.lmdsoft.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 类SysConfigController的功能描述:
 * 系统配置信息
 * @Auther lmdsoft
 * @Date 2018-08-25 16:17:32
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	@SysLog("查看配置列表")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysConfigEntity> configList = sysConfigService.queryList(query);
		int total = sysConfigService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	@SysLog("查看系统配置信息")
	public Result info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.queryObject(id);
		return Result.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:update")
	@SysLog("新增配置")
	public Result save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		sysConfigService.save(config);
		return Result.ok();
	}
	
	/**
	 * 修改配置
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	@SysLog("修改配置")
	public Result update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		sysConfigService.update(config);
		return Result.ok();
	}
	
	/**
	 * 删除配置
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	@SysLog("保存配置")
	public Result delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		return Result.ok();
	}

}
