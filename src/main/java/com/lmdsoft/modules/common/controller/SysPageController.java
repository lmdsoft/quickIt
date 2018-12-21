package com.lmdsoft.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * 
 * @author lmdsoft
 * @Date 2018年3月24日 下午11:05:27
 */
@Controller
public class SysPageController {

	@RequestMapping("{module}/{url}.htm")
	public String page(@PathVariable("module") String module, @PathVariable("url") String url){
		return  "modules/"+ module + "/" + url ;
	}

	@GetMapping({ "/", "" })
	String welcome(Model model) {
		return "login";
//		return "redirect:/login";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@GetMapping("/index")
	String index() {
		return "index";
	}
}
