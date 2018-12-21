<!DOCTYPE html>
<html>
<head>
<title>定时任务</title>
<#include "/modules/sys/header.ftl">
</head>
<body>
<div id="rrapp" v-cloak>
	<div v-show="showList">
		<div class="grid-btn" style="height:34px;">
			<div class="form-group col-sm-2">
				<input  class="form-control" v-model="q.beanName" @keyup.enter="query" placeholder="bean名称">
			</div>
			<button class="layui-btn" id="searchSubmit" @click="query"><i class="layui-icon">&#xe615;</i>查 询</button>
			<@shiro.hasPermission name="sys:schedule:info">
				<button class="layui-btn layui-btn-normal" type="button" @click="update"><i class="layui-icon">&#xe642;</i>编辑</button>
				<button class="layui-btn" @click="add" type="button"><i class="layui-icon">&#xe61f;</i> 新 增</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:schedule:delete">
				<button class="layui-btn layui-btn-danger" type="button" @click="del"><i class="layui-icon">&#xe640;</i>删除</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:schedule:pause">
				<button class="layui-btn layui-btn-warm" type="button" @click="pause"><i class="layui-icon">&#xe652;</i>暂停</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:schedule:resume">
				<button class="layui-btn " type="button" @click="resume"><i class="layui-icon">&#xe651;</i>恢复</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:schedule:run">
				<button class="layui-btn" type="button" @click="runOnce"><i class="layui-icon">&#xe650;</i>立即执行</button>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="sys:schedule:log">
				<a class="btn btn-danger" style="float:right;" href="schedule_log.html">日志列表</a>
			</@shiro.hasPermission>
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
	</div>
	
	<div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">bean名称</div>
			   	<div class="col-sm-10">
			      <input  class="form-control" v-model="schedule.beanName" placeholder="spring bean名称，如：testTask"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">方法名称</div>
			   	<div class="col-sm-10">
			      <input  class="form-control" v-model="schedule.methodName" placeholder="方法名称"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">参数</div>
			   	<div class="col-sm-10">
			      <input  class="form-control" v-model="schedule.params" placeholder="参数"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">cron表达式</div>
			   	<div class="col-sm-10">
			      <input  class="form-control" v-model="schedule.cronExpression" placeholder="如：0 0 12 * * ?"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">备注</div>
			   	<div class="col-sm-10">
			      <input  class="form-control" v-model="schedule.remark" placeholder="备注"/>
			    </div>
			</div>
			<div class="form-group" style="margin-left: 30%;margin-top: 30px;">
				<@shiro.hasPermission name="sys:schedule:update">
					<button class="layui-btn" type="button" @click="saveOrUpdate" >确定</button>
				</@shiro.hasPermission>
				<button class="layui-btn layui-btn-warm" type="button" @click="reload" style="margin-left: 30px">返回</button>
			</div>
		</form>
	</div>
</div>

<script src="${siteurl}/js/quartz/schedule.js?_${.now?long?c}"></script>
</body>
</html>