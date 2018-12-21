package com.lmdsoft.modules.demo.controller;
import com.lmdsoft.modules.activiti.dto.ProcessTaskDto;
import com.lmdsoft.modules.demo.entity.LeaveEntity;
import com.lmdsoft.modules.demo.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类的功能描述.
 * 流程相关的业务根据业务id查询公共类，路径为actKey，也就是业务key
 * @Auther lmdsoft
 * @Date 2018/8/7
 */
@RequestMapping("act/busInfo")
@Controller
public class ActBusInfoController {

    @Autowired
    LeaveService leaveService;

    @RequestMapping(value ="leave",method = RequestMethod.POST)
    public String leave(ProcessTaskDto processTaskDto, Model model, String flag){
        LeaveEntity leaveEntity = leaveService.queryObject(processTaskDto.getBusId());
        model.addAttribute("leave",leaveEntity);
        model.addAttribute("taskDto",processTaskDto);
        model.addAttribute("flag",flag);
        return "/demo/leaveActInfo";
    }
}
