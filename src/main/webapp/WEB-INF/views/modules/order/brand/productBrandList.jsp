<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出产品数据吗？","系统提示",function(v,h,f){
					if(v == "ok"){
						$("#searchForm").attr("action","${ctx}/order/brand/export").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
	
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
	    }
	</script>
</head>
<body>

	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/order/brand/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/order/brand/import/template">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/brand/">品牌列表</a></li>
		<shiro:hasPermission name="order:brand:edit"><li><a href="${ctx}/order/brand/form">品牌添加</a></li></shiro:hasPermission>
	</ul>
	
	<form:form id="searchForm" modelAttribute="productBrand" action="${ctx}/order/brand/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>品牌名称：</label><form:input path="brandName" htmlEscape="false" maxlength="50" class="input-large"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>品牌编码名称</th>
				<th>品牌名称</th>
				<th>采购折扣</th>
				<th>加权折扣</th>
				<th>最高限价折扣</th>
				<shiro:hasPermission name="order:brand:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="productBrand">
				<tr>
					<td><a href="${ctx}/order/brand/form?id=${productBrand.id}">${productBrand.uniqueBrandName}</a></td>
					<td>${productBrand.brandName}</td>
					<td>${productBrand.buyDiscount}</td>
					<td>${productBrand.weightDiscount}</td>
					<td>${productBrand.limitDiscount}</td>
					<shiro:hasPermission name="order:brand:edit"><td>
	    				<a href="${ctx}/order/brand/form?id=${productBrand.id}">修改</a>
						<a href="${ctx}/order/brand/delete?id=${productBrand.id}" onclick="return confirmx('确认要删除产品吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>