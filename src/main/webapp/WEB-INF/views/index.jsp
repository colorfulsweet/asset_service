<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产管理系统</title>
<link rel="stylesheet" href="/resources/lib/amazeui/css/amazeui.css" />
<link rel="stylesheet" href="/resources/css/admin.css">
<script src="/resources/lib/jquery/jquery.min.js"></script>
<script src="/resources/js/app.js"></script>
<style>
#main-view {width:100%; height:100%;}
</style>
</head>
<body>
<header class="am-topbar admin-header">
	<div class="am-topbar-brand">资产管理系统</div>
	<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
		<ul class="am-nav am-nav-pills am-topbar-nav admin-header-list">
			<li class="kuanjie"><a href="/views/logout">注销</a></li>
			<li class="am-hide-sm-only" style="float: right;">
				<a href="javascript:;" id="admin-fullscreen">
					<span class="am-icon-arrows-alt"></span>
					<span class="admin-fullText">开启全屏</span>
				</a>
			</li>
		</ul>
	</div>
</header>
<div class="am-cf admin-main">
	<div class="nav-navicon admin-main admin-sidebar">
		<div class="sideMenu">
			<h3 class="am-icon-flag">
				<em></em> <a href="javascript:void(0);" >资产管理</a>
			</h3>
			<ul>
				<li><a href="/views/zc/list" target="MainView">资产列表</a></li>
				<li>添加新资产</li>
				<li>资产分类</li>
				<li>资产回收</li>
				<li>库存管理</li>
			</ul>
			<h3 class="am-icon-users">
				<em></em> <a href="javascript:void(0);">用户管理</a>
			</h3>
			<ul>
				<li>用户列表</li>
				<li>添加新用户</li>
			</ul>
		</div>
		<!-- sideMenu End -->
	</div>
	<div class=" admin-content">
		<div class="daohang">
			<ul>
				<li><button type="button" class="am-btn am-btn-default am-radius am-btn-xs">首页</button></li>
				<li>
					<button type="button" class="am-btn am-btn-default am-radius am-btn-xs">
						<span>页签1</span>
						<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close="">×</a>
					</button>
				</li>
				<li>
					<button type="button" class="am-btn am-btn-default am-radius am-btn-xs">
						<span>页签2</span>
						<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close="">×</a>
					</button>
				</li>
				<li>
					<button type="button" class="am-btn am-btn-default am-radius am-btn-xs">
						<span>页签3</span>
						<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close="">×</a>
					</button>
				</li>
			</ul>
		</div>
		<div class="admin">
		<iframe src="/views/welcome" name="MainView" id="main-view"></iframe>
			<!-- 页面显示区域 -->

		</div>
	</div>
</div>
<script type="text/javascript">
jQuery(".sideMenu").slide({
	titCell : "h3", //鼠标触发对象
	targetCell : "ul", //与titCell一一对应，第n个titCell控制第n个targetCell的显示隐藏
	effect : "slideDown", //targetCell下拉效果
	delayTime : 300, //效果时间
	triggerTime : 150, //鼠标延迟触发时间（默认150）
	defaultPlay : true,//默认是否执行效果（默认true）
	returnDefault : true
//鼠标从.sideMen移走后返回默认状态（默认false）
});
</script>
<!--[if lt IE 9]>
<script src="/resources/js/modernizr.js"></script>
<script src="/resources/lib/polyfill/rem.min.js"></script>
<script src="/resources/lib/polyfill/respond.min.js"></script>
<script src="/resources/lib/amazeui/js/amazeui.legacy.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="/resources/lib/amazeui/js/amazeui.min.js"></script>
<!--<![endif]-->
</body>
</html>