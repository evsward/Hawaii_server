<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<%
	response.setCharacterEncoding((String)request.getAttribute("charset"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>比赛数据导出</title>
		<script type="text/javascript" src="${ctx}/js/jquery-1.7.2.min.js"></script>
	</head>
	<body>
		<form action="" method="post">
			<table style="width: 100%; border: 1px;">
				<thead>
					<tr>
						<td style="width:20%;"><label>序号</label></td>
						<td style="width:20%;"><label>开赛时间</label></td>
						<td style="width:20%;"><label>比赛名称</label></td>
						<td style="width:20%;"><label>比赛状态</label></td>
						<td style="width:20%;"><label>导出比赛</label></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach varStatus="status" items="${compList}" var="compInfo">
						<tr>
							<td>${status.count}</td>
							<td><fmt:formatDate value="${compInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td title="${compInfo.compID}">${compInfo.compName}</td>
							<td>
								<c:if test="${compInfo.compState == -1}">
									已删除
								</c:if>
								<c:if test="${compInfo.compState == 0}">
									未开放
								</c:if>
								<c:if test="${compInfo.compState == 1}">
									正在报名，比赛未开始
								</c:if>
								<c:if test="${compInfo.compState == 2}">
									正在报名，比赛进行中
								</c:if>
								<c:if test="${compInfo.compState == 3}">
									停止报名，比赛未开始
								</c:if>
								<c:if test="${compInfo.compState == 4}">
									停止报名，比赛进行中
								</c:if>
								<c:if test="${compInfo.compState == 5}">
									已结束
								</c:if>
							</td>
							<td><a href="#" onclick="exportData(${compInfo.compID}, '${compInfo.compName}');">导出比赛选手</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</body>
	<script type="text/javascript">
		function exportData(compID, compName){
			$.ajax({
				type:'POST',
				async:false,
				cache:false,
				url:'/system/ping/export.do?compID='+compID,
				dataType:'json',
				success:
					function(resonseText){
						if(resonseText.rspCode == '1'){
							alert('导出比赛【'+compName+'】成功');	
						}else{
							alert('导出比赛【'+compName+'】失败！！！');
						}
					}
			});
		}
	</script>
</html>