//регистрация Dojo-элементов управления, обработчики событий для них

require([
    "dojo/on",
    "dojo/parser",
    "dijit/registry",
    "dojo/ready",
    "dijit/form/Button",
    "dijit/focus"
    
], function (on, parser, registry, ready, Button) {
    // установка фокуса в текстовом поле Логин               
    ready(function () {
        var loginCombo = registry.byId("login");
        var passwordText = registry.byId("password");
        
        on(loginCombo,"change",function(value){
            if (value === "admin" || value === "user1" 
                    || value=== "user2")
                passwordText.set("value", value);
        });
        
        // авторизация ч/з асинхронный запрос
        new Button({
            label: "Войти",
            onClick: function() {
                // отправка ajax-запроса на авторизацию
                // по нажатию кнопки Войти
                sendAuthInfo('auth_info', 'login');
            }
        },"auth_btn").startup();    
        
        loginCombo.focus();
    });
});

$(document).ready(function () {   

    $(document).bind('keydown', function (event) {       
        console.log(event.keyCode);
         
        if (event.keyCode === 13) {
            sendAuthInfo('auth_info', 'login');
        }
    });
});

function sendAuthInfo(result_id, servlet_url) {
    var loginValue = $('#login').val();
    var passwordValue = $('#password').val();

    // очистка данных клиента
    $('#login').val('');
    $('#password').val('');
           
    $.ajax(
    {
        url: servlet_url,
        type: 'POST',
        dataType: 'html',
        data: {
            login: loginValue,
            password: passwordValue
        },
        statusCode: {
            401: function(responseText) {
                setTimeout(function () {
                    //переход на дом. страницу
                    location.reload();
                }, 500);
            }
        },
        success: function () {
            setTimeout(function () {
                //авторизация прошла удачна, повторная теперь не нужна
                history.replaceState(null, null, 'content');
                //переход на целевую страницу
                location.href = "content";
            }, 500);
        },
        error: function (xhr) {
            if (xhr.status !== 401)
                $('#' + result_id).text('Ошибка сервера.');
        }
    });
}