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

        // авторизация ч/з асинхронный запрос
        new Button({
            label: "Отправить",
            onClick: function() {
                // отправка ajax-запроса на авторизацию
                // по нажатию кнопки Отправить
                sendAuthInfo('registrate_info', 'signup.do');
            }
        },"registrate_btn").startup();
        
        var loginTextBox = registry.byId("login");
        loginTextBox.focus();
    });
});

$(document).ready(function () {

    $(document).bind('keydown', function (event) {
        console.log(event.keyCode);

        if (event.keyCode === 13) {
            sendAuthInfo('registrate_info', 'signup.do');
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
            409: function(responseText) {
                setTimeout(function () {
                    $('#' + result_id).text('Вы уже зарегистрированы!');
                }, 500);
            }
        },
        success: function () {
            setTimeout(function () {
                $('#' + result_id).text('Вы успешно зарегистрированы.');
            }, 500);
        },
        error: function (xhr) {
            if (xhr.status !== 409)
                $('#' + result_id).text('Ошибка сервера.');
        }
    });
}