package com.lmdsoft.modules.sys.controller;

import com.lmdsoft.modules.common.annotation.SysLog;
import com.lmdsoft.modules.common.controller.BaseController;
import com.lmdsoft.modules.common.page.Page;
import com.lmdsoft.modules.common.utils.DateUtils;
import com.lmdsoft.modules.common.utils.Result;
import com.lmdsoft.modules.common.utils.Utils;
import com.lmdsoft.modules.sys.entity.NoticeEntity;
import com.lmdsoft.modules.sys.service.NoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 通知
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @Date 2018-08-31 15:59:09
 */
@Controller
@RequestMapping("sys/notice")
public class NoticeController extends BaseController {
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 我的通知列表
	 */
	@RequestMapping("/myList")
	@RequiresPermissions("sys:notice:all")
	public String list(Model model, NoticeEntity noticeEntity, HttpServletRequest request){
		int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
		Page<NoticeEntity> page = noticeService.findMyNoticePage(noticeEntity, pageNum);
		model.addAttribute("page",page);
		model.addAttribute("notice",noticeEntity);
		return "notice/noticeList";
	}


	/**
	 * 查阅
	 */
	@RequestMapping("/showNotice")
	@RequiresPermissions("sys:notice:all")
	@ResponseBody
	@SysLog("查阅通知")
	public Result showNotice(String id){
		NoticeEntity notice = noticeService.queryObject(id);
		return Result.ok().put("notice", notice).put("time", DateUtils.format(notice.getReleaseTimee(),"yyyy-MM-dd hh:mm"));
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:notice:all")
	@SysLog("保存通知")
	public Result save(@RequestBody NoticeEntity notice){
		noticeService.save(notice);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:notice:all")
	@SysLog("修改通知")
	public Result update(@RequestBody NoticeEntity notice){
		noticeService.update(notice);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:notice:all")
	@SysLog("删除通知")
	public Result delete(@RequestBody String[] ids){
		noticeService.deleteBatch(ids);
		
		return Result.ok();
	}
	
}
