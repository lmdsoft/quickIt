<!DOCTYPE html>
<html>
<head>
	<title>管理员列表</title>
	<#include "/modules/sys/header.ftl">
</head>
<body>
<div id="rrapp" v-cloak>
	<div v-show="showList">
		<div class="grid-btn">
			<div class="form-group col-sm-2">
				<input class="form-control" v-model="q.userName" @keyup.enter="query" placeholder="用户名">
			</div>
			<button class="layui-btn" id="searchSubmit" @click="query"><i class="layui-icon">&#xe615;</i>查 询</button>
			<@shiro.hasPermission name="sys:user:update">
			<button class="layui-btn" @click="add" type="button"><i class="layui-icon">&#xe61f;</i> 新 增</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:user:info">
			<button class="layui-btn layui-btn-normal" type="button" @click="update"><i class="layui-icon">&#xe642;</i>编辑</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:user:reset">
			<button class="layui-btn layui-btn-danger" type="button" @click="reset"><i class="layui-icon">&#xe642;</i>重置密码</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:user:delete">
			<button class="layui-btn layui-btn-danger" type="button" @click="del"><i class="layui-icon">&#xe640;</i>禁用</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:user:enabled">
			<button class="layui-btn" type="button" @click="enabled"><i class="layui-icon">&#xe640;</i>启用</button>
			</@shiro.hasPermission>
		</div>
		<table id="jqGrid"></table>
		<div id="jqGridPager"></div>
		<div id="page"></div>
	</div>

	<div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal">
			<div class="form-group">
				<div class="col-sm-2 control-label">用户名</div>
				<div class="col-sm-10">
					<input class="form-control" v-model="user.userName" placeholder="用户名"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">登陆名</div>
				<div class="col-sm-10">
					<input class="form-control" v-model="user.loginName" placeholder="登录账号"/>
				</div>
			</div>
			<div class="form-group" v-show="user.id==null">
				<div class="col-sm-2 control-label">密码</div>
				<div class="col-sm-10">
					<input type="password" class="form-control" v-model="user.passWord" placeholder="密码"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">状态</div>
				<label class="radio-inline">
					<input type="radio" name="status" value="-1" v-model="user.status"/> 禁用
				</label>
				<label class="radio-inline">
					<input type="radio" name="status" value="" v-model="user.status"/> 正常
				</label>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">手机号</div>
				<div class="col-sm-10">
					<input class="form-control" v-model="user.phone" placeholder="手机号"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">所属部门</div>
				<div class="col-sm-10">
					<input class="form-control" readonly @click="organTree" v-model="user.baName" placeholder="选择所属部门"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">角色</div>
				<div class="col-sm-10">
					<label v-for="role in roleList" class="checkbox-inline">
						<input type="checkbox" :value="role.id" v-model="user.roleIdList">{{role.name}}
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">邮箱</div>
				<div class="col-sm-10">
					<input class="form-control" v-model="user.email" placeholder="邮箱"/>
				</div>
			</div>
			<div class="form-group" style="margin-left: 30%;margin-top: 30px;">
				<@shiro.hasPermission name="sys:user:update">
				<button class="layui-btn" type="button" @click="saveOrUpdate" >保存</button>
				</@shiro.hasPermission>
				<button class="layui-btn layui-btn-warm" type="button" @click="reload" style="margin-left: 30px">返回</button>
			</div>
		</form>
	</div>
</div>
<!-- 选择机构 -->
<div id="menuLayer" style="display: none;padding:10px;">
	<ul id="organTree" class="ztree"></ul>
</div>
<script src="${siteurl}/js/sys/user.js?_${.now?long?c}"></script>
</body>
</html>