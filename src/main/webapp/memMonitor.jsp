<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>虚拟机内存监控</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="refresh" content="5">
    </head>

    <body>
        <%
			Runtime lRuntime = Runtime.getRuntime();
			out.println("*** BEGIN MEMORY STATISTICS ***<br/>");
			out.println("Free  Memory: "+(long)(lRuntime.freeMemory()/(1024*1024))+"M<br/>");
			out.println("Max   Memory: "+(long)(lRuntime.maxMemory()/(1024*1024))+"M<br/>");
			out.println("Total Memory: "+(long)(lRuntime.totalMemory()/(1024*1024))+"M<br/>");
			out.println("Available Processors : "+lRuntime.availableProcessors()+"<br/>");
			out.println("*** END MEMORY STATISTICS ***");
		%>
        <br/>
    </body>
</html>