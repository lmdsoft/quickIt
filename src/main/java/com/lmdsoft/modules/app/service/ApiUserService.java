package com.lmdsoft.modules.app.service;


import com.lmdsoft.modules.app.entity.ApiUserEntity;
import com.lmdsoft.modules.sys.entity.UserEntity;
import com.lmdsoft.modules.sys.service.UserService;

/**
 * 类UserService的功能描述:
 * 用户
 * @Auther lmdsoft
 * @Date 2018-08-16 14:17:17
 */
public interface ApiUserService extends UserService {

	/**
	 * 用户登录
	 * @return          返回用户ID
	 */
	String login(UserEntity userEntity);

	/**
	 * 根据id获取用户相关信息
	 * @param id
	 * @return
	 */
	ApiUserEntity userInfo(String id);

	/**
	 * 修改密码
	 * @param oldUser
	 * @param newUser
	 * @return
	 */
	int updatePassword(UserEntity newUser, UserEntity oldUser);

}
