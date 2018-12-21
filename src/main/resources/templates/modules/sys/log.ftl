<!DOCTYPE html>
<html>
<head>
<title>系统日志</title>
<#include "/modules/sys/header.ftl">
</head>
<body>
<div id="rrapp" v-cloak>
	<div>
		<div class="grid-btn">
			<div class="form-group col-sm-2">
				<input type="text" class="form-control" v-model="q.key" @keyup.enter="query" placeholder="用户名、用户操作">
			</div>
			<button class="layui-btn" id="searchSubmit" @click="query"><i class="layui-icon">&#xe615;</i>查 询</button>
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
</div>

<script src="${siteurl}/js/sys/log.js?_${.now?long?c}"></script>
</body>
</html>