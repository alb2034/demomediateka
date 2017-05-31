//константы режимов карточки записи
CM_VIEW =   0;  //просмотр
CM_EDIT =   1;  //редактирование
CM_ADD =    2;  //добавление
CM_FIND =   3;  //поиск

card_mode = CM_VIEW;

contextPath = getContextPath();

listServicePath =  contextPath +"/manyContent";
groupsServicePath =  contextPath + "/groups";
descriptionServicePath =  contextPath + "/description";

require(["dijit/Dialog"], function(Dialog) {
    cardDialog = new Dialog({
        href: "card",
        style: "height: 100%"
    });
});

function getContextPath() {
    var pathName = window.location.pathname;
    var lastIndex = pathName.lastIndexOf("/");

    if (lastIndex == 0)
        return "";
    else
        return pathName.substr(0, pathName.indexOf("/", 1));
}

//создать если не существует и отобразтиь дилоговое окно для карточки записи
function showRecordCardDialog(dialogTitle) {
    require(["dijit/Dialog"], function(Dialog) {
                      
        //установить заголовок
        cardDialog.set("title",dialogTitle); 
        cardDialog.show(); 
    });
}

//дилоговое окно в режиме просмотра записи
viewRecordButtonClick = function () {
    card_mode = CM_VIEW;
    showRecordCardDialog("информация о записи");
};

//дилоговое окно в режиме редактирования записи
editRecordButtonClick = function () {
    card_mode = CM_EDIT;
    showRecordCardDialog("редактировать запись");
};

//дилоговое окно в режиме добавления записи
addRecordButtonClick = function () {
    card_mode = CM_ADD;
    showRecordCardDialog("новая запись");
};

//дилоговое окно в режиме поиска записи
findRecordButtonClick = function () {
    card_mode = CM_FIND;
    showRecordCardDialog("поиск записи");
};

//скрыть диалоговое окно
hideRecordCardDialog = function () {       
    cardDialog.hide();

};

//обработка запроса на удаление записи
function deleteRecord(recordId) {
    var confirmValue = 
            confirm("Вы действительно хотите удалить запись %" + 
            getCurrentRecordRow().name + "%?");
    if (!confirmValue) {
        return;
    }
    
    jsonRest.target = listServicePath;
    jsonRest.remove(recordId).then(
        function(){
            alert("Запись удалена!");
            renderContent("");
            
        },
        function(){
            alert("Ошибка удаления записи");
        });
    resetTimer();
}

function createGrid(Button, Cache, Grid, gridxModules) {
    //настройка отображения полей таблицы, заголовок столбцов
    var columns = [
        {
            field: 'id', id: 'id', name: "id", 
//            width: "0px",
//            style: "margin:0px; padding:0px; border:0px;" 
        },
        {
            field: 'group', name: 'Коллекция',
            style: "text-align: center"
            /*formatter: function(groupId){
                return json.query("?id=" + groupId, groupJson);
            }*/
        },
        {field: 'name', name: 'Название'},
        {field: 'author', name: 'Автор'},
        {
            field: 'action', 
            name: 'Действие', 
            style: "text-align: center",
            widgetsInCell: true,
            onCellWidgetCreated: function(cellWidget, column) {
                
                    var openRecordButton = new Button({
                        label: "просмотр",
                        onClick: viewRecordButtonClick
                    });
                    openRecordButton.placeAt(cellWidget.domNode);                             
            }
        }
    ];
       
    //создание таблицы   
    recordGrid = new Grid({
            cacheClass: Cache,
            structure: columns,
            //baseSort: [{attribute: "author", descending: false}],
            modules: [
                gridxModules.HiddenColumns,
                gridxModules.TouchScroll,
                gridxModules.SelectRow,
                gridxModules.CellWidget,
                gridxModules.Menu,
                gridxModules.RowHeader,
                gridxModules.SingleSort
            ],
            selectRowTriggerOnCell: true
        }, 'contentGrid');

        recordGrid.startup();
        recordGrid.hiddenColumns.add("id");
    
    //определение id-номера выделенной записи в таблице
    recordGrid.connect(recordGrid, "onRowMouseOver", function(e){
        currentRowId = e.rowId;
    });   
}

//контекстное меню для списка записей
function addRecordMenuToGrid(Menu, MenuItem) {
    
    //меню только для админа
    if (!nodeIsExists("addRecord"))
        return;
    
    recordMenu = new Menu();

    recordMenu.addChild(new MenuItem({
        label: "редактировать",
        onClick: editRecordButtonClick
    }));

    recordMenu.addChild(new MenuItem({
        label: "удалить",
        onClick: function() {deleteRecord(recordGrid.menu.context.row.id);}
    }));

    //контекстное в строках таблицы
    recordGrid.menu.bind(recordMenu, {
        hookPoint: "row",
    });      
}

//отображает 1-ю порцию списка записей
//определяет кол-во порций и
//отображает кнопки для перехода между ними
function getPartOfContent(query, start, count, Store, Button) {
    jsonRest.target = listServicePath;

    //выдать порцию записей
    var results = jsonRest.query(query, {
        start: start,
        count: count
    });

    //получение ответа
    results.then(function(contentJson){

        //заполнить таблицу очередной порцией
        recordGrid.setStore(new Store({                   
            data: contentJson
        })); 

        if (!fstPageRender) 
            return;
        
        fstPageRender = false;
        domConstruct.empty("pageLinks");
        
        for(var i=1; i <= pageButtons.length; i++) {
            if (typeof pageButtons[i] !== "undefined")
                    pageButtons[i].destroyRecursive(true);
        }
        
        //сформировать кнопки для перехода на остальные порции
        results.total.then(function(total){


            var buttonCount = Math.ceil(total/count);
            for (var i=1; i <= buttonCount; i++) {
                
                
                
                //создать dom-узел очередной кнопки
                domConstruct.create("button", 
                    {
                        id: "button" + i
                    },
                    dom.byId("pageLinks")
                );

                //создать очередную кнопку
                var pageButton = new Button({
                    label: i,
                    onClick: function() {
                        var partNumber = parseInt(this.label);
                        var startRenderContent = count*(partNumber-1);
                        var countRenderContent = count;
                        if (partNumber === buttonCount && total - partNumber*count < 0)
                                countRenderContent = total - startRenderContent;
                        
                        lastPageButton = partNumber;
                        
                        getPartOfContent(query,
                            startRenderContent,
                            countRenderContent,
                            Store, Button);
                        resetTimer();
                    }
                },"button" + i);
                pageButton.startup();
                pageButtons[i] = pageButton;
            }                   
        }); 
        
        if (lastPageButton > 0 && 
            typeof pageButtons[lastPageButton] !== "undefined")
            pageButtons[lastPageButton].onClick();
    }); 
}

//инициализация ajax-запроса к rest-сервису
function initAjaxRequest(JsonRest) {
    
    jsonRest = new JsonRest();
    
    /*список коллекций*/
    jsonRest.target = groupsServicePath;
    
    jsonRest.query("").then(function(jsonResult){
        groupJson = jsonResult;
    }); 
    
    

    // обработчик ответов rest-сервиса  
    notify("done", function (response) {

        if (response instanceof Error) {
            console.log(response.message);
            errorResponse = response;
        } else {
            successResponse = response;
        }
    });
}

//истина - если пользователь админ
function nodeIsExists(id) {
    var node = dom.byId(id);

    return (node !== null);
}

function renderContent(query){
    require([
        "dojo/store/Memory",
        "dijit/form/Button"], 
    function(Store, Button){
        jsonRest.target = listServicePath;
        //lastPageButton = 0;
        fstPageRender = true;
        //получить 1-ю порцию списка и кнопки для перехода на следующие
        getPartOfContent(query, 0, count, Store, Button);
    });
}

//возвращает значения полей текущей записи
function getCurrentRecordRow() {
    //извлечение выбранной записи таблицы
    if (typeof currentRowId === "undefined")
        return undefined;
    else
        return recordGrid.model.byId(currentRowId).rawData;
}

require([
        "dojo/dom",
        "dojo/dom-construct",  
        "dojo/on",    
        "dojo/request",
        "dojo/request/notify",
        "dojo/store/Memory",
        "dojo/store/JsonRest", 
        "dijit/form/Button",
        "gridx/core/model/cache/Sync",
        "gridx/Grid",
        "gridx/allModules",
        "dijit/layout/BorderContainer",
        "dijit/layout/ContentPane",
        "dijit/Menu",
        "dijit/MenuItem",
        "dojo/parser",       
        "dojo/domReady!"], function (
            dom, domConstruct, on, request, notify,
            Store, JsonRest, 
            Button, Cache, Grid, gridxModules,
            BorderContainer, ContentPane,
            Menu, MenuItem) {       
 
    //глобальный доступ к dojo-модулям  
    window.dom = dom;
    window.domConstruct = domConstruct;
    window.request = request;
    window.notify = notify;

    //создание таблицы для списка
    createGrid(Button, Cache, Grid, gridxModules);
    
    //подключение контекстного меню
    addRecordMenuToGrid(Menu, MenuItem);
   
    //инициализация запроса
    initAjaxRequest(JsonRest);
    
    //выдавать по 15 записей
    count = 15;
    pageButtons = [];
    lastPageButton = 0;
    
    jsonRest.target = listServicePath;
    renderContent("");
    
     //совместное изменение размеров таблицы и окна браузера
    on(window, "resize", function(){
        recordGrid.resize();
    });
});