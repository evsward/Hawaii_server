<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<%
	response.setCharacterEncoding((String)request.getAttribute("charset"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
		<meta http-equiv="refresh" content="5">
		<title>推送服务线程</title>
	</head>
	<body>
		<c:forEach items="${pushMsg2TermServiceMap}" var="map">
			<table style="width: 100%; border: 1px;">
				<thead>
					<tr>
						<td style="width:50%;"><label style="color: blue; font-size: 30px; font-weight: bold;">消息类别：${map.key}</label></td>
						<td style="width:50%;"><label style="color: blue; font-size: 30px; font-weight: bold;">线程描述：${map.value.thrdDesc}</label></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2"><hr/></td>
					</tr>
					<table style="width:100%">
						<thead>
							<tr>
								<td style="width:25%;"><label style="color: black; font-size: 20px; font-weight: bold;">消息号</label></td>
								<td style="width:25%;"><label style="color: black; font-size: 20px; font-weight: bold;">设备号</label></td>
								<td style="width:25%;"><label style="color: black; font-size: 20px; font-weight: bold;">客户端IP</label></td>
								<td style="width:25%;"><label style="color: black; font-size: 20px; font-weight: bold;">channelId</label></td>
							</tr>
						</thead>
						<tbody>
							<c:forEach varStatus="status" items="${map.value.onlineChannelMap}" var="cacheMap">
								<tr>
									<td><label style="color: red; font-size: 20px;">${cacheMap.value.commandId}</label></td>
									<td><label style="color: red; font-size: 20px;">${cacheMap.value.imei}</label></td>
									<td><label style="color: red; font-size: 20px;">${cacheMap.value.clientIp}</label></td>
									<td><label style="color: red; font-size: 20px;">${cacheMap.value.channelId}</label></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="4"><hr/></td>
							</tr>
						</tbody>
					</table>
				</tbody>
			</table>
			<hr/>
			<br/>
			<br/>
		</c:forEach>
	</body>
</html>