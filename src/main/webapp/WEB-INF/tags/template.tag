<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="content" fragment="true"%>
<%@ attribute name="scripts" fragment="true"%>
<!DOCTYPE html>
<html color-mode="user">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta name="mobile-web-app-capable" content="yes" />
<title>Ape Alby s/ Chéran</title>
<link rel="stylesheet" href="<spring:url value="/css/mvp.css"/>" />
<link rel="stylesheet" href="<spring:url value="/css/style.css"/>" />
<link rel="icon" href="<spring:url value="/img/favicon-64.ico"/>" type="image/x-icon">
<link rel="shortcut icon" href="<spring:url value="/img/favicon-64.ico"/>" type="image/x-icon"> </head>
</head>
<body>
	<header>
		<nav>
			<a href="<spring:url value="/"/>"><img src="<spring:url value="/img/favicon-64.ico"/>"/></a>
			<ul>
				<li><a href="<spring:url value="/todo"/>">Historique</a></li>
<%-- 				<li><a href="<spring:url value="/admin"/>">Admin</a></li> --%>
			</ul>
		</nav>
	</header>
	<main>
		<jsp:invoke fragment="content" />
	</main>
	<footer>
		<hr>
		<p>Calendrier pour l'APE d'alby sur Chéran<br/> 
		Par <a href="mailto:shionn@gmail.com">shionn</a> avec du Spring, mybatis, mvp et tomcat</p>
	</footer>
</body>
</html>
