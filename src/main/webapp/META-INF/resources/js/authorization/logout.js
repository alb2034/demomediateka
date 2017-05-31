// выход из авторизации и 
// переход на домашнюю страницу по таймеру
// закрытие страницы без предупреждения во время отладки
session_time = 12;
logOuting = false;

// запросить статус авторизации в фоне, если не то выход
timerSeconds = session_time;

function resetTimer() {
    logOuting = false;
    timerSeconds = session_time;
}

// таймер выхода из авторизации
function runTimer() {
    
//    console.log(timerSeconds);
    
    if (timerSeconds === 0) {
        setTimeout(function () {

//            history.replaceState(null, null, contextPath);
            // сессия пользователя прекращена
            // поэтому можно менять адрес страницы
            logOuting = true;
//            location.reload();

        }, 500);
    } 
    if (timerSeconds > 0){
        /*document.getElementById('sec').innerHTML =
                'Переход на домашнюю страницу через ' + timerSeconds + ' сек...';*/
        timerSeconds--; 
    }
    setTimeout('runTimer()', 1000);
    
}

// повторный запрос на закрытие вкладки страницы
function cancelExit(event) {
    confirmMessage = 'Рекомендуется выйти из авторизации' +
            ' перед закрытием вкладки. Закрыть вкладку?';
    
    if (typeof event === 'undefined') {
        var event = window.event;
    }
    
    if (event) {  
        event.returnValue = confirmMessage;
    }
    
    return (confirm(confirmMessage) || confirmMessage);
}

// инициирование отслеживания перехода по ссылке Выйти
function initLogoutEvent() {
    var logOutHref = document.getElementById('logOut');
    logOutHref.addEventListener('click', logOut);
}

// установка флага выхода из авторизации пользователя
// отмена возврата на страницу по кнопке назад
function logOut() {
    history.replaceState(null, null, contextPath);
    logOuting = true;
}

// после загрузки основного интерфейса
// добавляем обработчики событий
document.addEventListener('DOMContentLoaded', initLogoutEvent);

// запуск таймера выхода из авторизации
document.addEventListener('DOMContentLoaded', runTimer);

// обработка закрытия вкладки страницы
window.onbeforeunload = function (e) {
    if (!logOuting) {
        cancelExit(e);
    } 
};