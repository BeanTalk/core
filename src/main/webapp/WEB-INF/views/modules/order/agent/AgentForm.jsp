<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>经销商管理</title>
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
		<li><a href="${ctx}/order/agent/">经销商列表</a></li>
		<li class="active"><a href="form?id=${agency.id}">经销商<shiro:hasPermission name="order:agent:edit">${not empty brand.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:agency:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="agency" action="${ctx}/order/agent/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="agentName">经销商名称:</label>
			<div class="controls">
				<form:input path="agentName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:agent:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>