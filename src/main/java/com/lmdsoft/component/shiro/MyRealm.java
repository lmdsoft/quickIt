package com.lmdsoft.component.shiro;

import com.lmdsoft.modules.common.common.Constant;
import com.lmdsoft.modules.common.utils.StringUtils;
import com.lmdsoft.modules.sys.entity.MenuEntity;
import com.lmdsoft.modules.sys.entity.RoleEntity;
import com.lmdsoft.modules.sys.entity.UserEntity;
import com.lmdsoft.modules.sys.service.MenuService;
import com.lmdsoft.modules.sys.service.RoleService;
import com.lmdsoft.modules.sys.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类的功能描述.
 * shiro 认证
 * @Auther lmdsoft
 * @Date 2018/4/27
 */
@Component
public class MyRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        UserEntity user = (UserEntity) principals.getPrimaryPrincipal();
        if(user !=null){
            //根据用户id查询该用户所有的角色,并加入到shiro的SimpleAuthorizationInfo
            List<RoleEntity> roles = roleService.queryByUserId(user.getId(), Constant.YESNO.YES.getValue());
            for (RoleEntity role:roles){
                    info.addRole(role.getId());
            }
            //查询所有该用户授权菜单，并加到shiro的SimpleAuthorizationInfo 做菜单按钮权限控件

            Set<String> permissions = new HashSet<String>();
            List<MenuEntity> menuEntities=null;
            //超级管理员拥有最高权限
            if(Constant.SUPERR_USER.equals(user.getId())){
                menuEntities = menuService.queryList(new HashedMap());
            }else{
                //普通帐户权限查询
               menuEntities = menuService.queryByUserId(user.getId());
            }
            for (MenuEntity menuEntity:menuEntities){
                if(menuEntity != null && !StringUtils.isEmpty(menuEntity.getPermission())){
                    permissions.add(menuEntity.getPermission());
                }
            }
            info.addStringPermissions(permissions);
        }
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userLoginName= (String) token.getPrincipal();
        UserEntity user = userService.queryByLoginName(userLoginName);
        if(user == null){
            throw new AuthenticationException("帐号密码错误");
        }
        if(Constant.ABLE_STATUS.NO.getValue().equals(user.getStatus())){
            throw new AuthenticationException("帐号被禁用,请联系管理员!");
        }
        //用户对应的机构集合
        List<String> baidList = userService.queryBapidByUserIdArray(user.getId());
        //用户对应的部门集合
        List<String> bapidList= userService.queryBaidByUserIdArray(user.getId());
        user.setBaids(StringUtils.toStringBySqlIn(baidList));
        user.setBapids(StringUtils.toStringBySqlIn(bapidList));
        SimpleAuthenticationInfo sainfo=new SimpleAuthenticationInfo(user,user.getPassWord(), ByteSource.Util.bytes(user.getSalt()),getName());
        return sainfo;
    }

//    @Override
//    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
//        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.algorithmName);
//        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
//        super.setCredentialsMatcher(shaCredentialsMatcher);
//    }

}
