<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/SuggestionController/index">JS客户端</a>
	<table style="margin: 0 atuo">
		<tr>
			<td colspan="4">
				<form action="search" method="post">
					<table>
						<tr>
							<td>商品名称</td>
							<td>
								<input type="text" name="name">
							</td>
							<td>
								<input type="submit" value="搜索">
							</td>
						</tr>	
					</table>
				</form>
			</td>
		</tr>
		<tr>
			<th>编号</th>
			<th>商品名称</th>
			<th>商品描述</th>
			<th>商品价格</th>
		</tr>
		<c:forEach varStatus="status" items="${requestScope.pageInfo.content }" var="product">
			<tr>
				<td>${status.count }</td>
				<td>${product.name }</td>
				<td>${product.description }</td>
				<td>${product.price }</td>
			</tr>
		</c:forEach>
		<tr>
			<c:if test="${not requestScope.pageInfo.isFirst }">
				<c:choose>
					<c:when test="${ not empty requestScope.productName }">
						<a href="search?number=${ requestScope.pageInfo.number-1 }&name=${requestScope.productName}">上一页</a>
					</c:when>
					<c:otherwise>
						<a href="?number=${ requestScope.pageInfo.number-1 }">上一页</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${not requestScope.pageInfo.isLast }">
				<c:choose>
					<c:when test="${ not empty requestScope.productName }">
						<a href="search?number=${ requestScope.pageInfo.number+1 }&name=${requestScope.productName}">下一页</a>
					</c:when>
					<c:otherwise>
						<a href="?number=${ requestScope.pageInfo.number+1 }">下一页</a>
					</c:otherwise>
				</c:choose>
			</c:if>
		</tr>
	</table>
</body>
</html>