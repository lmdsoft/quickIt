<!DOCTYPE html>
<html>
<head>
<title>定时任务日志</title>
<#include "/modules/sys/header.ftl">
</head>
<body>
<div id="rrapp">
	<div class="grid-btn">
		<div class="form-group col-sm-2">
			<input type="text" class="form-control" v-model="q.jobId" @keyup.enter="query" placeholder="任务ID">
		</div>
		<button class="layui-btn" id="searchSubmit" @click="query"><i class="layui-icon">&#xe615;</i>查 询</button>
		<button class="layui-btn layui-btn-warm" id="back" @click="back">返 回</button>
	</div>
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div>

<script src="${siteurl}/js/sys/schedule_log.js?_${.now?long?c}"></script>
</body>
</html>