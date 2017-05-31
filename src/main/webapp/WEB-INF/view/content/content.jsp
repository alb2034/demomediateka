<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!--подключение библиотеки для работы java тегов-->
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html>
<html>   
    <head>
        <meta charset="utf-8">
        <title>Список видео</title> 
        <!--подключение стилей-->
        <%@include file="header.jspf" %>
    </head>

    <body class="claro">
         
        <div
            id="contentLayout"
            data-dojo-type="dijit/layout/BorderContainer"
            >
            
            <!--шапка страницы-->
            <div
                id="topPanel"
                data-dojo-type="dijit/layout/ContentPane"
                data-dojo-props="region: 'top'">
                
                <%@include file="top.jspf" %>
            </div>
            
            <!--центральная область сраницы-->
            <div
                id="centerPanel"
                data-dojo-type="dijit/layout/ContentPane"
                data-dojo-props="region: 'center'">
                
                <%@include file="center.jspf" %>
            </div>
            
            <!--подвал страницы-->
            <div
                id="bottomPanel"
                data-dojo-type="dijit/layout/ContentPane"
                data-dojo-props="region: 'bottom'">
                
                <%@include file="bottom.jspf" %>
            </div> 
        </div>
        <!--подключение скриптов-->
        <%@include file="footer.jspf" %>
    </body>
</html>