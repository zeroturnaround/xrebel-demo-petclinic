<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    <h2>Supplements</h2>

    <datatables:table id="supplements" data="${supplements}" cdn="true" row="supplement" theme="bootstrap2"
                      cssClass="table table-striped" paginate="false" info="false" export="pdf">
        <datatables:column title="Name" property="name"/>
        <datatables:column title="Price" property="price"/>
    </datatables:table>

    <jsp:include page="../fragments/footer.jsp"/>

</div>
</body>

</html>
