<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
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
		<li><a href="${ctx}/order/product/">产品列表</a></li>
		<li class="active"><a href="form?id=${product.id}">产品<shiro:hasPermission name="order:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:product:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/order/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="productName">产品名称:</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="productNum">货号:</label>
			<div class="controls">
				<form:input path="productNum" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="brand.id">产品品牌:</label>
			<div class="controls">
				<form:select path="brand.id">
					<form:options items="${productBrandMap}" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="specValue">规格:</label>
			<div class="controls">
				<form:input path="specValue" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="unitValue">单位:</label>
			<div class="controls">
				<form:input path="unitValue" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="catalogFee">目录价:</label>
			<div class="controls">
				<form:input path="catalogFee" htmlEscape="false" maxlength="50" class="required number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="deliveryFee">备货价:</label>
			<div class="controls">
				<form:input path="deliveryFee" htmlEscape="false" maxlength="50" class="required number"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:product:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>