<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isErrorPage="true" import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>▒▒▒  eGovFrame Potal 온라인 지원 포탈  ▒▒▒</title>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css" />

<script language="javascript">
function fncGoAfterErrorPage(){
    history.back(-2);
}
</script>
</head>
<%
if ( "egovframework.com.cmm.exception.EgovFileExtensionException".equals(exception.getClass().getName()) ) {
	pageContext.forward("/errorFileExtension.jsp");
}
%>
<body>
<div style="width: 1000px; margin: 50px auto 50px;">
	<p style="font-size: 18px; color: #000; margin-bottom: 10px; "><img src="<c:url value='/images/egovframework/com/cmm/er_logo.jpg' />" width="379" height="57" alt="" /></p>
	<div style="border: 0 solid #666; padding: 20px;">
		<!-- 404 -->
		<p style="color:red; margin-bottom: 8px; ">500 Error</p>

		<div class="boxType1" style="width: 500px;">
			<div class="box">
				<div class="error">
					<p class="title">HTTP 500 Error</p>
					<p class="cont mb20">HTTP 500 Internal Server error.<br /></p>
					<br/>
					<p>
						<%
							out.println("<pre>");
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							exception.printStackTrace(pw);
							out.print(sw.toString());
							sw.close();
							pw.close();
							out.println("</pre>");
						%>
					</p>
					<br/>
					<span class="btn_style1 blue"><a href="javascript:fncGoAfterErrorPage();">이전 페이지</a></span>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>