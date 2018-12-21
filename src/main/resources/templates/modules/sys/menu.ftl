<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
<#include "/modules/sys/header.ftl">
</head>
<body>
<div id="rrapp" v-cloak>
	<!--.container （固定宽度）或 .container-fluid （100% 宽度）-->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4">
				<@shiro.hasPermission name="sys:menu:update">
					<button class="layui-btn layui-btn-small" @click="add" type="button" @click="add"><i class="layui-icon">&#xe61f;</i> 新 增</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="sys:menu:delete">
					<button class="layui-btn layui-btn-danger layui-btn-small" type="button" @click="del"><i class="layui-icon">&#xe640;</i>删 除</button>
				</@shiro.hasPermission>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3">
				<div style="font-size: 20px;margin-bottom: 10px">
					<i class="layui-icon" style="font-size: 20px; color: #009688">&#xe62e;</i> 菜单树
				</div>
				<div class="col-sm-10">
					<ul id="allMenuTree" class="ztree"></ul>
				</div>
			</div>
			<div v-show="showInfo" class="col-md-5 col-sm-10">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe63c;</i> {{title}}</div>
					<form class="form-horizontal" id="menuFrom">
						<div class="form-group">
							<div class="col-sm-2 control-label">类型</div>
							<label class="radio-inline">
								<input type="radio" name="type" value="0" v-model="menu.type"/> 目录
							</label>
							<label class="radio-inline">
								<input type="radio" name="type" value="1" v-model="menu.type"/> 菜单
							</label>
							<label class="radio-inline">
								<input type="radio" name="type" value="2" v-model="menu.type"/> 按钮
							</label>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">名称</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="menu.name" placeholder="菜单名称或按钮名称"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">上级菜单</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" style="cursor:pointer;" v-model="menu.parentName" @click="menuTree" readonly="readonly" placeholder="一级菜单"/>
							</div>
						</div>
						<div v-if="menu.type == 1" class="form-group">
							<div class="col-sm-2 control-label">菜单URL</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="menu.url" placeholder="菜单URL"/>
							</div>
						</div>
						<div v-if="menu.type == 1 || menu.type == 2" class="form-group">
							<div class="col-sm-2 control-label">授权标识</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="menu.permission" placeholder="多个用逗号分隔，如：user:list,user:create"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">是否展开</div>
							<label class="radio-inline">
								<input type="radio" name="open" value="true" v-model="menu.open"/> 是
							</label>
							<label class="radio-inline">
								<input type="radio" name="open" value="false" v-model="menu.open"/> 否
							</label>
						</div>
						<div v-if="menu.type != 2" class="form-group">
							<div class="col-sm-2 control-label">排序号</div>
							<div class="col-sm-10">
								<input type="number" class="form-control" v-model="menu.sort" placeholder="排序号"/>
							</div>
						</div>
						<div v-if="menu.type != 2" class="form-group">
							<div class="col-sm-2 control-label">图标</div>
							<div class="col-sm-10">
								<input type="text" id="icon" class="form-control"  v-model="menu.icon" @click="menuIcons" placeholder="菜单图标"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label"></div>
							<@shiro.hasPermission name="sys:menu:update">
							<button class="layui-btn" @click="saveOrUpdate" type="button">保 存</button>
							</@shiro.hasPermission>
							<button class="layui-btn layui-btn-warm" @click="reload" type="button">重 置</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 选择菜单 -->
<div id="menuLayer" style="display: none;padding:10px;">
	<ul id="menuTree" class="ztree"></ul>
</div>

<script src="${siteurl}/js/sys/menu.js?_${.now?long?c}"></script>
</body>
</html>