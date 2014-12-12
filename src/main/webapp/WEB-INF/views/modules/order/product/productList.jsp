<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出产品数据吗？","系统提示",function(v,h,f){
					if(v == "ok"){
						$("#searchForm").attr("action","${ctx}/order/product/export").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			
			$("#btnIndex").click(function(){
				top.$.jBox.confirm("确认要重建索引吗？","系统提示",function(v,h,f){
					if(v == "ok"){
						$("#searchForm").attr("action","${ctx}/order/product/create/index").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
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
		<form id="importForm" action="${ctx}/order/product/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/order/product/import/template">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/product/">产品列表</a></li>
		<shiro:hasPermission name="order:product:edit"><li><a href="${ctx}/order/product/form">产品添加</a></li></shiro:hasPermission>
	</ul>
	
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/order/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>产品名称：</label><form:input path="productName" htmlEscape="false" maxlength="50" class="input-large"/>
			<label>货&nbsp;&nbsp;&nbsp;&nbsp;号：</label><form:input path="productNum" htmlEscape="false" maxlength="50" class="input-small"/>
			<label>品牌名称：</label>
			<form:select id="brand.id" path="brand.id" class="input-middle">
				<form:option value="" label=""/>
				<form:options items="${productBrandMap}" htmlEscape="false"/>
			</form:select>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			&nbsp;<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			&nbsp;<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
			&nbsp;<input id="btnIndex" class="btn btn-primary" type="button" value="重建索引"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>货号</th>
				<th>产品品牌</th>
				<th>规格</th>
				<th>单位</th>
				<th>目录价</th>
				<th>备货价</th>
				<shiro:hasPermission name="order:product:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="product">
				<tr>
					<td><a href="${ctx}/order/product/form?id=${product.id}">${product.productName}</a></td>
					<td>${product.productNum}</td>
					<td>${product.brand.brandName}</td>
					<td>${product.specValue}</td>
					<td>${product.unitValue}</td>
					<td>${product.catalogFee}</td>
					<td>${product.deliveryFee}</td>
					<shiro:hasPermission name="order:product:edit"><td>
	    				<a href="${ctx}/order/product/form?id=${product.id}">修改</a>
						<a href="${ctx}/order/product/delete?id=${product.id}" onclick="return confirmx('确认要删除产品吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>