<!DOCTYPE html>
<html>
<head>
<title>机构管理</title>
<#include "/modules/sys/header.ftl">
</head>
<body>
<div id="rrapp" v-cloak>
	<!--.container （固定宽度）或 .container-fluid （100% 宽度）-->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4">
				<@shiro.hasPermission name="sys:organ:all">
					<button class="layui-btn layui-btn-small" type="button" @click="add"><i class="layui-icon">&#xe61f;</i> 新 增</button>
					<button class="layui-btn layui-btn-danger layui-btn-small" type="button" @click="del"><i class="layui-icon">&#xe640;</i>删 除</button>
			</@shiro.hasPermission>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3">
				<div style="font-size: 20px;margin-bottom: 10px">
					<i class="layui-icon" style="font-size: 20px; color: #009688">&#xe62e;</i> 组织机构树
				</div>
				<div class="col-sm-10">
					<ul id="allorganTree" class="ztree"></ul>
				</div>
			</div>
			<div v-show="showInfo" class="col-md-5 col-sm-10">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe63c;</i> {{title}}</div>
					<form class="form-horizontal" id="organFrom">
						<div class="form-group">
							<div class="col-sm-2 control-label">父节点名</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="organ.parentName" readonly="readonly" placeholder="自动获取"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">是否展开</div>
							<label class="radio-inline">
								<input type="radio" name="open" value="true" v-model="organ.open"/> 是
							</label>
							<label class="radio-inline">
								<input type="radio" name="open" value="false" v-model="organ.open"/> 否
							</label>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">编号</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="organ.code" />
							</div>
						</div>
						<div  class="form-group">
							<div class="col-sm-2 control-label">组织名称</div>
							<div class="col-sm-10">
								<input type="tex" class="form-control" v-model="organ.name" />
							</div>
						</div>
						<div v-if="organ.type > 0" class="form-group">
							<div class="col-sm-2 control-label">组织类型</div>
							<div class="col-sm-10">
								<label class="radio-inline">
									<input type="radio" name="type" value="1" v-model="organ.type"/> 机构
								</label>
								<label class="radio-inline">
									<input type="radio" name="type" value="2" v-model="organ.type"/> 部门
								</label>
							</div>
						</div>
						<div  class="form-group">
							<div class="col-sm-2 control-label">显示顺序</div>
							<div class="col-sm-10">
								<input type="number" class="form-control" v-model="organ.sort" />
							</div>
						</div>
						<div  class="form-group">
							<div class="col-sm-2 control-label">字典描述</div>
							<div class="col-sm-10">
								<textarea v-model="organ.remark" class="form-control" rows="3"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label"></div>
							<@shiro.hasPermission name="sys:organ:all">
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
<div id="organLayer" style="display: none;padding:10px;">
	<ul id="organTree" class="ztree"></ul>
</div>

<script src="${siteurl}/js/sys/organ.js?_${.now?long?c}"></script>
</body>
</html>