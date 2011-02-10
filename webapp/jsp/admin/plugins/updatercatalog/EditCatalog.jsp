<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="updatercatalog" scope="session" class="fr.paris.lutece.plugins.updatercatalog.web.CatalogJspBean" />

<% updatercatalog.init( request , updatercatalog.RIGHT_CATALOG_MANAGEMENT ); %>
<%= updatercatalog.getEditCatalog( request )%>

<%@include file="../../AdminFooter.jsp" %>