<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>         
        <title>Welcome</title>
                
        <!--подключение стилей-->
        <%@include file="welcome/header.jspf" %>   
        
    </head>
    
    <body class="claro">   
        <!--форма авторизации-->
        <%@include file="welcome/signin_form.jspf" %>
        
        <!--подключение java-скриптов-->
        <%@include file="welcome/footer.jspf" %>      
        
    </body>
</html>
