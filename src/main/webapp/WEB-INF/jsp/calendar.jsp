<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">

<h1><fmt:formatDate value="${event.day}" pattern="dd MMMM YYYY"/> - ${event.name}</h1>

<p>Petit text de presentation expliquant les besoin et les enjeux. Combien il faudrait être. A quel heure jusqu'a quand et les modalité d'inscription. Le nombre idéale de personne par tranche horaire...
Lorem ipsum dolor sit amet consectetur adipiscing elit. Quisque faucibus ex sapien vitae pellentesque sem placerat. In id cursus mi pretium tellus duis convallis. Tempus leo eu aenean sed diam urna tempor. Pulvinar vivamus fringilla lacus nec metus bibendum egestas. Iaculis massa nisl malesuada lacinia integer nunc posuere. Ut hendrerit semper vel class aptent taciti sociosqu. Ad litora torquent per conubia nostra inceptos himenaeos.
</p>


<a href="<spring:url value="/event/${event.id}/register"/>"><b>Je m'inscis</b></a>

<h2>Ils sont déjà inscrits</h2>

<table>
	<thead>
		<tr><th>Quand</th><th colspan="2">Qui</th></tr>
	</thead>
	<tbody>
		<c:forEach items="${event.periods}" var="period">
			<tr>
				<td>${period.label}</td>
				<c:forEach items="${period.registrations}" var="reg">
					<td>${reg.shortLabel}</td>
				</c:forEach>
				<td><spring:url value='/event/${event.id}/register' var='regUrl'/><a href="${regUrl}?arrivalHour=${period.period}"><em>S'inscrire</em></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</jsp:attribute>
</t:template>