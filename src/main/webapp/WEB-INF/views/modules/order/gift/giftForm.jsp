<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼品管理</title>
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
		<li><a href="${ctx}/order/gift/">礼品列表</a></li>
		<li class="active"><a href="form?id=${brand.id}">礼品<shiro:hasPermission name="order:gift:edit">${not empty brand.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:brand:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="gift" action="${ctx}/order/gift/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">归属区域:</label>
			<div class="controls">
                <tags:treeselect id="area" name="area.id" value="${gift.area.id}" labelName="area.name" labelValue="${gift.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="giftName">礼品名称:</label>
			<div class="controls">
				<form:input path="giftName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label" for="giftStatus">礼品状态:</label>
			<div class="controls">
				<form:select path="giftStatus">
					<form:options items="${fns:getDictList('sys_gift_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="giftNum">可使用数量:</label>
			<div class="controls">
				<form:input path="giftNum" htmlEscape="false" maxlength="50" class="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="needPea">所需豆豆:</label>
			<div class="controls">
				<form:input path="needPea" htmlEscape="false" maxlength="50" class="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="needScore">所需积分:</label>
			<div class="controls">
				<form:input path="needScore" htmlEscape="false" maxlength="50" class="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="remarks">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
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