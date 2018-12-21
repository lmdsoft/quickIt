<!DOCTYPE html>
<html>
<head>
<title>代码生成器</title>
<#include "/modules/sys/header.ftl">
</head>
<body>
<div id="rrapp">
	<div class="grid-btn">
		<div class="form-group col-sm-2">
			<input type="text" class="form-control" v-model="q.tableName" @keyup.enter="query" placeholder="表名">
		</div>
		<button class="layui-btn" id="searchSubmit" @click="query"><i class="layui-icon">&#xe615;</i>查 询</button>
		<@shiro.hasPermission name="sys:generator:code">
		<button class="layui-btn" @click="generator" @click="query"><i class="layui-icon">&#xe635;</i>生成代码</button>
		</@shiro.hasPermission>
	</div>
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div>

<script src="${siteurl}/js/gen/generator.js?_${.now?long?c}"></script>
</body>
</html>