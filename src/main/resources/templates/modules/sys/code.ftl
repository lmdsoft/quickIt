<!DOCTYPE html>
<html>
<head>
<title>码值管理</title>



<#include "/modules/sys/header.ftl">
<#assign shiro = JspTaglibs["/WEB-INF/tlds/shiro.tld"]>
</head>
<body>
<div id="rrapp" v-cloak>
	<!--.container （固定宽度）或 .container-fluid （100% 宽度）-->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4">
                <@shiro.hasPermission name = "sys:code:update">
					<button class="layui-btn layui-btn-small" type="button" @click="add"><i class="layui-icon">&#xe61f;</i> 新 增</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name = "sys:code:delete">
					<button class="layui-btn layui-btn-danger layui-btn-small" type="button" @click="del"><i class="layui-icon">&#xe640;</i>删 除</button>
				</@shiro.hasPermission>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3">
				<div style="font-size: 20px;margin-bottom: 10px">
					<i class="layui-icon" style="font-size: 20px; color: #009688">&#xe62e;</i> 字典树
				</div>
				<div class="col-sm-10">
					<ul id="allCodeTree" class="ztree"></ul>
				</div>
			</div>
			<div v-show="showInfo" class="col-md-5 col-sm-10">
				<div class="panel panel-default">
					<div class="panel-heading"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe63c;</i> {{title}}</div>
					<form class="form-horizontal" id="codeFrom">
						<div class="form-group">
							<div class="col-sm-2 control-label">类型</div>
							<label class="radio-inline">
								<input type="radio" name="type" value="1" v-model="code.type"/> 目录
							</label>
							<label class="radio-inline">
								<input type="radio" name="type" value="2" v-model="code.type"/> 字典码
							</label>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">父节点名</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="code.parentName" readonly="readonly" placeholder="自动获取"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">字典标识</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="code.mark" placeholder="码值唯一标识, 例如，SEX、SEX_1、SEX_2"/>
							</div>
						</div>
						<div v-if="code.type == 2" class="form-group">
							<div class="col-sm-2 control-label">码值</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="code.value" placeholder="码值 的数字表示，例如：1，2，3、sex"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">码值名</div>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="code.name" placeholder="码值的中文表示,例如：是、否 "/>
							</div>
						</div>
						<div  class="form-group">
							<div class="col-sm-2 control-label">排序号</div>
							<div class="col-sm-10">
								<input type="number" class="form-control" v-model="code.sort" placeholder="排序号"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label">是否展开</div>
							<label class="radio-inline">
								<input type="radio" name="open" value="true" v-model="code.open"/> 是
							</label>
							<label class="radio-inline">
								<input type="radio" name="open" value="false" v-model="code.open"/> 否
							</label>
						</div>
						<div  class="form-group">
							<div class="col-sm-2 control-label">描述</div>
							<div class="col-sm-10">
								<textarea v-model="code.remark" class="form-control" rows="3"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 control-label"></div>
						<@shiro.hasPermission name="sys:code:update">
								<button class="layui-btn" @click="saveOrUpdate" type="button">保 存</button>
						</@shiro.hasPermission>
							<button class="layui-btn layui-btn-warm" @click="reload" type="button">重 置</button>
						</div>
					</form>
				</div>
			</div>
		</div>site
	</div>
</div>

<!-- 选择菜单 -->
<div id="codeLayer" style="display: none;padding:10px;">
	<ul id="codeTree" class="ztree"></ul>
</div>
<script src="${siteurl}/js/sys/code.js?_${.now?long?c}"></script>
</body>
</html>