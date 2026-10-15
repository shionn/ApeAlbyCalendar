<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">

<h1>Insciption <fmt:formatDate value="${event.day}" pattern="dd MMMM YYYY"/> - ${event.name}</h1>

<p>A voir si on met un text ici ?</p>

<spring:url value="/event/${event.id}/register" var="url"/>
<form:form action="${url}" method="POST">
	<label for="firstName">Prénom</label>
	<input id="firstName" name="firstName" type="text" maxlength="100" required="required"/>

	<label for="lastName">Nom</label>
	<input id="lastName" name="lastName" type="text" maxlength="100" required="required"/>

	<label for="email">Email</label>
	<input id="email" name="email" type="email" maxlength="200" required="required"/>

	<label for="arrivalHour">Heure d'arrivée</label>
	<select id="arrivalHour" name="arrivalHour">
		<c:forEach items="${event.periods}" var="p">
			<option value="${p.period}">${p.period}h</option>
		</c:forEach>
	</select>

	<label for="departureHour">Heure de départ</label>
	<select id="departureHour" name="departureHour">
		<c:forEach items="${event.periods}" var="p">
			<option value="${p.period+1}">${p.period+1}h</option>
		</c:forEach>
	</select>

	<label for="comment">Commentaire</label>
	<textarea id="comment" name="comment" rows="5" placeholder="Ajouter toute remarque qui vous semble pertinente." maxlength="2048"></textarea>

	<br/>
	<input type="submit" value="S'inscrire"/>

</form:form>

</jsp:attribute>
</t:template>