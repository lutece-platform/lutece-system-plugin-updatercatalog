<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="updatercatalog" scope="session" class="fr.paris.lutece.plugins.updatercatalog.web.CatalogJspBean" />

<% 
    updatercatalog.init( request , updatercatalog.RIGHT_CATALOG_MANAGEMENT ); 
    response.sendRedirect( updatercatalog.doGenerateCatalog( request ) );
%>

