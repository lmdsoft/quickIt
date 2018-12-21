package com.lmdsoft.modules.app.controller;

import com.lmdsoft.modules.app.annotation.CurrentUser;
import com.lmdsoft.modules.app.annotation.LoginRequired;
import com.lmdsoft.modules.app.entity.ApiUserEntity;
import com.lmdsoft.modules.app.service.ApiUserService;
import com.lmdsoft.modules.common.utils.Result;
import com.lmdsoft.modules.sys.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类ApiLoginController的功能描述:
 * APP登录授权
 * @Auther lmdsoft
 * @Date 2018-08-16 14:15:39
 */
@Controller
@RequestMapping("/app/user")
public class ApiUserController {
    @Autowired
    private ApiUserService userApiService;

    /**
     * 用户信息
     */
    @LoginRequired
    @ResponseBody
    public Result info(@CurrentUser ApiUserEntity apiUserEntity){
        ApiUserEntity user = userApiService.userInfo(apiUserEntity.getId());
        return Result.ok().put("user", user);
    }

    /**
     * 修改用户信息
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @LoginRequired
    @ResponseBody
    public Result update(UserEntity user){
        userApiService.update(user);
        return Result.ok();
    }

    /**
     * 修改当前用户密码
     */
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    @LoginRequired
    @ResponseBody
    public Result updatePassword(UserEntity newUser,@CurrentUser ApiUserEntity apiUserEntity){
        int i = userApiService.updatePassword(newUser,apiUserEntity);
        if(i<1){
            return Result.error("更改密码失败");
        }
        return Result.ok("更改密码成功");
    }


}
