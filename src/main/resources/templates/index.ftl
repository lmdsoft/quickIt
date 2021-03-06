<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>QuickIT系统</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" type="text/css" href="plugins/layui-v1.0.7/css/layui.css">
	<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="css/index1.css">
</head>
<style>
	.msg-num{
		background: red;
		width: 18px;
		height: 18px;
		display: inline-block;
		border-radius: 10px;
		line-height: 18px;
		font-size: 12px;
		color: #FFF;
		text-align: center;
		position: absolute;
		top: 14px;
		right: 5px;
	}
</style>
<body>
<div class="layui-layout layui-layout-admin" id="layui_layout">
	<!-- 顶部区域 -->
	<div class="layui-header header header-demo">
		<div class="layui-main">
			<!-- logo区域 -->
			<div class="admin-logo-box">
				<a href="#">
				</a>
				<span class="admin-logo-text" >QuickIT系统</span>
				<div class="larry-side-menu">
					<i class="fa fa-bars" aria-hidden="true"></i>
				</div>

			</div>
			<!-- 顶级菜单区域 -->
			 <div class="layui-larry-menu">
                <!-- <ul class="layui-nav clearfix">
                        <li class="layui-nav-item">
                               <a href="javascirpt:;"><i class="iconfont icon-ht_expand"></i>扩展模块</a>
                        </li>
                 </ul>-->
            </div>
			<div style="position:absolute;top:0;right:330px;width: 166px;padding-top: 23px;font-size: 15px;">欢迎 {{user.userName}}  登陆！</div>
			<!-- 右侧导航 -->
			<ul class="layui-nav larry-header-item" lay-filter="side">
				<li class="layui-nav-item">
					<a  href="javascript:;" @click="myNotice"><i class="fa fa-bullhorn"></i> &nbsp;通知<span class="msg-num">{{myNoticeCount}}</span></a>
				</li>
				<li class="layui-nav-item">
					<a  href="javascript:;" @click="myUpcoming"><i class="fa fa-envelope-o"></i> &nbsp;待办<span class="msg-num">{{myUpcomingCount}}</span></a>
				</li>
				<li class="layui-nav-item">
					<a href="javascript:;" @click="updatePassword"><i class="fa fa-lock"></i> &nbsp;修改密码</a>
				</li>
				<li class="layui-nav-item">
					<a href="logout"><i class="fa fa-sign-out"></i> &nbsp;退出系统</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- 左侧侧边导航开始 -->
	<div class="layui-side layui-side-bg layui-larry-side" id="larry-side">
		<div class="layui-side-scroll" id="larry-nav-side" lay-filter="side">
			<!-- 左侧菜单 -->
			<ul class="layui-nav layui-nav-tree" id="menuTree"></ul>
		</div>
	</div>

	<!-- 左侧侧边导航结束 -->
	<!-- 右侧主体内容 -->

	<div class="layui-body" id="larry-body" style="bottom: 0;border-left: solid 2px #1AA094;">
		<div class="layui-tab layui-tab-card larry-tab-box" id="larry-tab" lay-filter="main-tab" lay-allowClose="true">
			<ul class="layui-tab-title">
				<li class="layui-this" id="admin-home"><i class="fa fa-home"></i><em>控制台</em></li>
			</ul>
			<!--<div class="often key-press" lay-filter="larryOperate" id="buttonRCtrl">
				<ul class="layui-nav posr">
					<li class="layui-nav-item posb">
						<a class="top"><i class="larry-icon larry-caozuo"></i><cite>常用操作</cite><span class="layui-nav-more"></span></a>
						<dl class="layui-nav-child">
							<dd>
								<a data-ename="closeCurrent"><i class="larry-icon larry-guanbidangqianye"></i>关闭当前选项卡</a>
							</dd>
							<dd>
								<a data-ename="closeOther"><i class="larry-icon larry-guanbiqita"></i>关闭其他选项卡</a>
							</dd>
							<dd>
								<a data-ename="closeAll"><i class="larry-icon larry-guanbiquanbufenzu"></i>关闭全部选项卡</a>
							</dd>
							<dd>
								<a data-ename="refreshAdmin"><i class="larry-icon larry-kuangjia_daohang_shuaxin"></i>刷新最外层框架</a>
							</dd>
						</dl>
					</li>
				</ul>
			</div>-->
			<div class="layui-tab-content" style="min-height: 150px; ">
				<div class="layui-tab-item layui-show">
					<iframe id="mainFrame" class="larry-iframe" data-id='0' src="/sys/main.htm"></iframe>
				</div>
			</div>
		</div>


	</div>
	<!-- 底部区域 -->
	<div class="layui-footer layui-larry-foot" id="larry-footer">
		<div class="layui-mian">
			Copyright &copy; 2018 QuickIt All Rights Reserved
		</div>
	</div>

	<!-- 修改密码 -->
	<div id="passwordLayer" style="display: none;">

		<form class="layui-form" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">账号</label>
				<label class="layui-form-label laber-account">{{user.userName}}</label>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">原密码</label>
				<div class="layui-input-inline">
					<input type="password" v-model="passWord" placeholder="原密码" datatype="*"   autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">新密码</label>
				<div class="layui-input-inline">
					<input type="password" v-model="newPassWord" placeholder="新密码" datatype="*" autocomplete="off" class="layui-input">
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="plugins/layui-v1.0.7/layui.js"></script>
<script type="text/javascript" src="js/vue.min.js"></script>
<script type="text/javascript" src="js/index1.js"></script>
<script type="text/javascript" src="plugins/validform/js/Validform_v5.3.2.js"></script>
</body>
</html>