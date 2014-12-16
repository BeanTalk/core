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
				top.$.jBox.confirm("确认要导出礼品数据吗？","系统提示",function(v,h,f){
					if(v == "ok"){
						$("#searchForm").attr("action","${ctx}/order/gift/export").submit();
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
			<a href="${ctx}/order/gift/import/template">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/gift/">品牌列表</a></li>
		<shiro:hasPermission name="order:gift:edit"><li><a href="${ctx}/order/gift/form">品牌添加</a></li></shiro:hasPermission>
	</ul>
	
	<form:form id="searchForm" modelAttribute="gift" action="${ctx}/order/gift/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>礼品名称：</label><form:input path="giftName" htmlEscape="false" maxlength="50" class="input-large"/>
			<label>归属区域：</label>
				<tags:treeselect id="area" name="area.id" value="${gift.area.id}" labelName="area.name" labelValue="${gift.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required"/>
			<label>礼品状态:</label>
				<form:select path="giftStatus">
					<form:options items="${fns:getDictList('sys_gift_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>礼品名称</th>
				<th>礼品状态</th>
				<th>所属区域</th>
				<th>所需豆豆</th>
				<th>所需积分</th>
				<th>备注信息</th>
				<th>可用数量</th>
				<shiro:hasPermission name="order:gift:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="gift">
				<tr>
					<td><a href="${ctx}/order/gift/form?id=${gift.id}">${gift.giftName}</a></td>
					<td>${fns:getDictLabel(gift.giftStatus, 'sys_gift_status', '无')}</td>
					<td>${gift.area.name}</td>
					<td>${gift.needPea}</td>
					<td>${gift.needScore}</td>
					<td>${gift.remarks}</td>
					<td>${gift.giftNum}</td>
					<shiro:hasPermission name="order:gift:edit"><td>
	    				<a href="${ctx}/order/gift/form?id=${gift.id}">修改</a>
						<a href="${ctx}/order/gift/delete?id=${gift.id}" onclick="return confirmx('确认要删除礼品吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>