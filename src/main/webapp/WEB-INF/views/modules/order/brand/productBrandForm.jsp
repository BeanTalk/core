<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate();
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/brand/">品牌列表</a></li>
		<li class="active"><a href="form?id=${brand.id}">品牌<shiro:hasPermission name="order:brand:edit">${not empty brand.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:brand:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="productBrand" action="${ctx}/order/brand/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="brandName">品牌名称:</label>
			<div class="controls">
				<form:input path="brandName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="buyDiscount">采购折扣:</label>
			<div class="controls">
				<form:input path="buyDiscount" htmlEscape="false" maxlength="50" class="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="weightDiscount">加权折扣:</label>
			<div class="controls">
				<form:input path="weightDiscount" htmlEscape="false" maxlength="50" class="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="limitDiscount">最高限价折扣:</label>
			<div class="controls">
				<form:input path="limitDiscount" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:brand:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>