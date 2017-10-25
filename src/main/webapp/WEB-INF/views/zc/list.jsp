<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产列表</title>
<link rel="stylesheet" href="/resources/lib/amazeui/css/amazeui.css" />
<link rel="stylesheet" href="/resources/css/admin.css">
<script src="/resources/lib/jquery/jquery.min.js"></script>
</head>
<body>
<div class="admin-biaogelist">
<div class="listbiaoti am-cf">
	<div class="am-icon-flag title">资产列表</div>
</div>

<form class="am-form am-g">
  <table width="100%" class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover">
    <thead>
      <tr class="am-success">
        <th class="table-check"><input type="checkbox"></th>
        <th >名称</th>
        <th >数量</th>
        <th >单位</th>
        <th >资产来源</th>
        <th >供应商名称</th>
        <th >规格型号</th>
        <th >保管人</th>
        <th width="130px">操作</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${zcList}" var="item" >
      <tr>
        <td><input type="checkbox"></td>
        <td>${item.mingch}</td>
        <td>${item.shul}</td>
        <td>${item.danwei}</td>
        <td>${item.zcly}</td>
        <td>${item.gysDcxm}</td>
        <td>${item.ggxh}</td>
        <td>${item.bgr.realname}</td>
        <td>
        	<div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              <button class="am-btn am-btn-default am-btn-xs am-text-success am-round" title="查看">
              	<span class="am-icon-search" ></span>
              </button>
              <button class="am-btn am-btn-default am-btn-xs am-text-secondary am-round" title="修改">
              	<span class="am-icon-pencil-square-o"></span>
              </button>
              <button class="am-btn am-btn-default am-btn-xs am-text-danger am-round" title="删除">
              	<span class="am-icon-trash-o"></span>
              </button>
            </div>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
	<div class="am-btn-group am-btn-group-xs">
		<button type="button" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
		<button type="button" class="am-btn am-btn-default"><span class="am-icon-trash-o"></span>批量删除</button>
	</div>
  <ul class="am-pagination am-fr">
     <li class="am-disabled"><a href="#">«</a></li>
     <li class="am-active"><a href="#">1</a></li>
     <li><a href="#">2</a></li>
     <li><a href="#">3</a></li>
     <li><a href="#">4</a></li>
     <li><a href="#">5</a></li>
     <li><a href="#">»</a></li>
   </ul>
</form>
</div>
<script src="/resources/lib/amazeui/js/amazeui.min.js"></script>
</body>
</html>