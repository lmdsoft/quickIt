<!DOCTYPE html>
<html>
<head>
<title>${comments}</title>
${r"<#include '/modules/sys/header.ftl'>"}


</head>
<body>
<div id="rrapp" v-cloak>
	<div v-show="showList">
		<div class="grid-btn">
		${r"<@shiro.hasPermission name = '"}${pathName}${r":save'> "}

            <button class="layui-btn" @click="add" type="button"><i class="layui-icon">&#xe61f;</i> 新 增</button>
		${r"</@shiro.hasPermission>"}
			${r"<@shiro.hasPermission name = '"}${pathName}${r":update'>"}
            <button class="layui-btn layui-btn-normal" type="button" @click="update"><i class="layui-icon">&#xe642;</i>编辑</button>
		${r"</@shiro.hasPermission>"}
		${r"<@shiro.hasPermission name = '"}${pathName}${r":delete'>"}
            <button class="layui-btn layui-btn-danger" type="button" @click="del"><i class="layui-icon">&#xe640;</i>删除</button>
			${r"</@shiro.hasPermission>"}
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
    <div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal">
	<#list  columns as column>
		<#if column.columnName != pk.columnName>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">${column.comments}</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="${classname}.${column.attrname}" placeholder="${column.comments}"/>
			    </div>
			</div>
		</#if>
		</#list>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div>
                <button class="layui-btn" type="button" @click="saveOrUpdate" >保存</button>
                <button class="layui-btn layui-btn-warm" type="button" @click="reload" style="margin-left: 30px">返回</button>
			</div>
		</form>
	</div>
</div>

<script src="${r"${siteurl}"}/js/${module}/${pathName}.js?_${r"${.now?long?c}"}"></script>
</body>
</html>