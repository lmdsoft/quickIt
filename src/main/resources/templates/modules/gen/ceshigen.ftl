<!DOCTYPE html>
<html>
<head>
<title></title>
<#include '/modules/sys/header.ftl'>


</head>
<body>
<div id="rrapp" v-cloak>
	<div v-show="showList">
		<div class="grid-btn">
		<@shiro.hasPermission name = 'ceshigen:save'> 

            <button class="layui-btn" @click="add" type="button"><i class="layui-icon">&#xe61f;</i> 新 增</button>
		</@shiro.hasPermission>
			<@shiro.hasPermission name = 'ceshigen:update'>
            <button class="layui-btn layui-btn-normal" type="button" @click="update"><i class="layui-icon">&#xe642;</i>编辑</button>
		</@shiro.hasPermission>
		<@shiro.hasPermission name = 'ceshigen:delete'>
            <button class="layui-btn layui-btn-danger" type="button" @click="del"><i class="layui-icon">&#xe640;</i>删除</button>
			</@shiro.hasPermission>
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
    <div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">值</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="ceshiGen.value" placeholder="值"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">名称</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="ceshiGen.name" placeholder="名称"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">类型</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="ceshiGen.type" placeholder="类型"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">标题</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="ceshiGen.title" placeholder="标题"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">创建时间</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="ceshiGen.createtime" placeholder="创建时间"/>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div>
                <button class="layui-btn" type="button" @click="saveOrUpdate" >保存</button>
                <button class="layui-btn layui-btn-warm" type="button" @click="reload" style="margin-left: 30px">返回</button>
			</div>
		</form>
	</div>
</div>

<script src="${siteurl}/js/gen/ceshigen.js?_${.now?long?c}"></script>
</body>
</html>