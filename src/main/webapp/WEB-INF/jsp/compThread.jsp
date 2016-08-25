<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
		<meta http-equiv="refresh" content="5">
		<title>后台比赛维护线程</title>
	</head>
	<body>
		<table style="font-size: 20px;">
			<thead>
				<tr>
					<td style="width:3%;"><label style="color: blue; ">ID</label></td>
					<td style="width:7%;"><label style="color: blue; ">名称</label></td>
					<td style="width:8%;"><label style="color: blue; ">状态</label></td>
					<td style="width:5%;"><label style="color: blue; ">暂停</label></td>
					<td style="width:5%;"><label style="color: blue; ">类型</label></td>
					
					<td style="width:4%;"><label style="color: blue; ">参赛费</label></td>
					<td style="width:4%;"><label style="color: blue; ">服务费</label></td>
					<td style="width:5%;"><label style="color: blue; ">初始筹码</label></td>
					<td style="width:5%;"><label style="color: blue; ">货币</label></td>
					<td style="width:4%;"><label style="color: blue; ">奖励表</label></td>
					
					<td style="width:5%;"><label style="color: blue; ">停报盲级</label></td>
					<td style="width:5%;"><label style="color: blue; ">保底奖金</label></td>
					<td style="width:5%;"><label style="color: blue; ">扣减人数</label></td>
					<td style="width:5%;"><label style="color: blue; ">总人数</label></td>
					<td style="width:5%;"><label style="color: blue; ">牌桌类型</label></td>
					
					<td style="width:5%;"><label style="color: blue; ">分座系统</label></td>
					<td style="width:5%;"><label style="color: blue; ">可重进</label></td>
					<td style="width:10%;"><label style="color: blue; ">开赛时间</label></td>
				</tr>
			</thead>
			<tbody>
				<tr><td colspan="18"></td></tr>
				<c:forEach varStatus="status" items="${compServManageMap}" var="map">
					<tr>
						<td><label style="">${map.value.compInfo.compID}</label></td>
						<td><label style="">${map.value.compInfo.compName}</label></td>
						<td>
							<label style="">
								<c:if test="${map.value.compInfo.compState == -1}">
									已删除
								</c:if>
								<c:if test="${map.value.compInfo.compState == 0}">
									未开放
								</c:if>
								<c:if test="${map.value.compInfo.compState == 1}">
									正在报名--比赛未开始
								</c:if>
								<c:if test="${map.value.compInfo.compState == 2}">
									正在报名--比赛已开始
								</c:if>
								<c:if test="${map.value.compInfo.compState == 3}">
									停止报名--比赛未开始
								</c:if>
								<c:if test="${map.value.compInfo.compState == 4}">
									停止报名--比赛进行中
								</c:if>
								<c:if test="${map.value.compInfo.compState == 5}">
									已结束
								</c:if>
							</label>
						</td>
						<td>
							<label style="">
								<c:if test="${map.value.compInfo.compPause == 0}">
									未暂停
								</c:if>
								<c:if test="${map.value.compInfo.compPause == 1}">
									系统自动暂停
								</c:if>
								<c:if test="${map.value.compInfo.compPause == 2}">
									暂停中
								</c:if>
							</label>
						</td>
						<td>
							<label style="">
								<c:if test="${map.value.compInfo.compType == 1}">
									晋级赛
								</c:if>
								<c:if test="${map.value.compInfo.compType == 0}">
									非晋级赛
								</c:if>
							</label>
						</td>
						<td><label style="">${map.value.compInfo.regFee}</label></td>
						<td><label style="">${map.value.compInfo.serviceFee}</label></td>
						<td><label style="">${map.value.compInfo.beginChip}</label></td>
						<td>
							<label style="">
								<c:if test="${map.value.compInfo.amountUnit == 1}">
									CNY
								</c:if>
								<c:if test="${map.value.compInfo.amountUnit == 0}">
									USD
								</c:if>
							</label>
						</td>
						<td>
							<label style="">
								<c:if test="${map.value.compInfo.aword == 1}">
									带
								</c:if>
								<c:if test="${map.value.compInfo.aword == 0}">
									不带
								</c:if>
							</label>
						</td>
						<td><label style="">${map.value.compInfo.stopRegRank}</label></td>
						<td><label style="">${map.value.compInfo.leastPrize}</label></td>
						<td><label style="">${map.value.compInfo.subPlayerCount}</label></td>
						<td><label style="">${map.value.compInfo.totalPlayer}</label></td>
						<td><label style="">${map.value.compInfo.tableType}</label></td>
						<td>
							<label style="">
								<c:if test="${map.value.compInfo.assignSeat == 1}">
									使用
								</c:if>
								<c:if test="${map.value.compInfo.assignSeat == 0}">
									未使用
								</c:if>
							</label>
						</td>
						<td>
							<label style="">
								<c:if test="${map.value.compInfo.reEntry == 1}">
									是
								</c:if>
								<c:if test="${map.value.compInfo.reEntry == 0}">
									否
								</c:if>
							</label>
						</td>
						<td><label style=""><fmt:formatDate value="${map.value.compInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label></td>
					</tr>
					<tr><td colspan="18"></td></tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>