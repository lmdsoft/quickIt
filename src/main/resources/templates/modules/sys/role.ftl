<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
<#include "header.ftl">
</head>
<body>
<div id="rrapp" v-cloak>
	<div v-show="showList">
		<div class="grid-btn">
			<div class="form-group col-sm-2">
				<input class="form-control" v-model="q.roleName" @keyup.enter="query" placeholder="角色名称">
			</div>
			<button class="layui-btn" id="searchSubmit" @click="query"><i class="layui-icon">&#xe615;</i>查 询</button>
			<@shiro.hasPermission name = "sys:role:update">
				<button class="layui-btn" @click="add" type="button"><i class="layui-icon">&#xe61f;</i> 新 增</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name = "sys:role:update">
				<button class="layui-btn layui-btn-normal" type="button" @click="update"><i class="layui-icon">&#xe642;</i>编辑</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name = "sys:role:delete">
				<button class="layui-btn layui-btn-danger" type="button" @click="del"><i class="layui-icon">&#xe640;</i>禁用</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name = "sys:role:enabled">
			<button class="layui-btn" type="button" @click="enabled"><i class="layui-icon">&#xe640;</i>启用</button>
			</@shiro.hasPermission>
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
    <div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">角色名称</div>
			   	<div class="col-sm-10">
			      <input  class="form-control" v-model="role.name" placeholder="角色名称"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">备注</div>
			   	<div class="col-sm-10">
			      <input  class="form-control" v-model="role.remark" placeholder="备注"/>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">状态</div>
				<label class="radio-inline">
					<input type="radio" name="status" value="-1" v-model="role.status"/> 禁用
				</label>
				<label class="radio-inline">
					<input type="radio" name="status" value="0" v-model="role.status"/> 正常
				</label>
			</div>
			<div class="row">
				<div class="form-group col-md-6 ">
					<div class="col-sm-6 control-label">菜单授权:</div>
					<div class="col-sm-6">
						<ul id="menuTree" class="ztree"></ul>
					</div>
				</div>
				<div class="form-group col-md-6 ">
					<div class="col-sm-6 control-label">数据授权:</div>
					<div class="col-sm-6">
						<ul id="organTree" class="ztree"></ul>
					</div>
				</div>

			</div>
			<div class="form-group" style="margin-left: 30%;margin-top: 30px;">
				<@shiro.hasPermission name="sys:role:update">
				<button class="layui-btn" type="button" @click="saveOrUpdate" >确定</button>
				</@shiro.hasPermission>
				<button class="layui-btn layui-btn-warm" type="button" @click="reload" style="margin-left: 30px">返回</button>
			</div>
		</form>
	</div>
</div>
   
<script src="${siteurl}/js/sys/role.js?_${.now?long?c}"></script>
</body>
</html>