//обновление формы карточки в соотвествии с режимом работы с записью
function updateCard(Memory) {            
    

    if (card_mode !== CM_FIND) {
        fillGroupsField();          //определить коллекцию
        showDescription();          //отобразить описание
    } else {
        clearGroupsField();
    }

    switch (card_mode) {
        
        case CM_VIEW:               //просмотр
        case CM_EDIT:               //редактирование
          
            clearPlaceHolders();    //очистить подсказки
            showId();               //показать id

            fillFields();           //заполнить данными
            fillDescriptionField(); //подгрузить описание      
            break;        
        case CM_ADD:                //добавление
        case CM_FIND:               //поиск
            
            hideId();               //скрыть id           
            clearFields();          //очистить данные
            fillPlaceHolders();     //заполнить подсказками                
            break;           
    }
    
    switch (card_mode) {
        
        case CM_VIEW:               //просмотр
            
            setReadOnlyFields();    //только для чтения       
            break;        
        case CM_EDIT:               //редактирование
        case CM_ADD:                //добавление
                   
            showWidget(saveButton); //отобразить кнопку Сохранить
            break;        
        case CM_FIND:               //поиск
            hideDescription();      //скрыть описание
            showWidget(findButton); //отобразить кнопку Найти
            //findButton.hidden = false;
            break;           
    }
};



//скрывает id
function hideId() {
    query("#record_card .idField")[0].hidden = true;
}

//скрывает описание
function hideDescription() {
    query("#record_card .descriptionField")[0].hidden = true;
}

//отображает описание
function showDescription() {
    query("#record_card .descriptionField")[0].hidden = false;
}
    
//отображает id
function showId() {
    query("#record_card .idField #id")[0].value = getCurrentRecordRow().id;
    query("#record_card .idField")[0].hidden = false;
}

//инициализация списка Коллекция
function initGroupsField(FilteringSelect, Memory) {
    if (typeof groupSelect === "undefined") {
        groupSelect = new FilteringSelect({
        }, "group");


        var groupStore = new Memory({
            //idProperty: "enotherId",
            data: groupJson
        });
        groupSelect.set("store", groupStore);
        groupSelect.startup();
    }
}

//очистка списка Коллекция
function clearGroupsField() {
    groupSelect.set('displayedValue', '');
    groupSelect.set('placeHolder', 'Все');
    groupSelect.set('required', false);
};

//подгрузка списка Коллекция
function fillGroupsField() {

    groupSelect.set('placeHolder', '');
    groupSelect.set('required', true);

    if (groupSelect.store.data.length > 0) {
        if (typeof getCurrentRecordRow() === "undefined")
            currentGroup = groupSelect.store.data[0].name;
        else
            currentGroup = getCurrentRecordRow().group;

        groupSelect.set("displayedValue", currentGroup);
    }
}

//подгрузка поля Описание
function fillDescriptionField() {
    
    query("#record_card #description")[0].placeholder = "";

    jsonRest.target = descriptionServicePath;
   
    jsonRest.get(getCurrentRecordRow().id).then(function(jsonResult) {
        query("#record_card #description")[0].value = jsonResult.description;
    });
}

//очистка полей
function clearFields() {   
    query("#record_card .localField input").forEach(function(field, index, fieldList){
        fieldList[index].value = "";   
    });  
    query("#record_card #description")[0].value = "";         
}
//очистка подсказок
function clearPlaceHolders() {   
    query("#record_card .localField input").forEach(function(field, index, fieldList){
        fieldList[index].placeholder = "";    
    });    
    query("#record_card #description")[0].placeholder = "";    
}

//заполнение полей формы карточки загруженными данными
function fillFields() {
        
    //извлечение значений полей выбранной записи таблицы   
    recordRow = getCurrentRecordRow();
    
    //массив значений полей записи
    var fieldValueArray = [
        recordRow.name, 
        recordRow.author 
    ]; 
    
    //поиск элементов с классом .localField
    query("#record_card .localField input").forEach(function(field, index, fieldList) {
        //заполнить значениями
        fieldList[index].value = fieldValueArray[index];
    });
};

//заполнение подсказок
function fillPlaceHolders() {
    var placeHolderArray = [
        "введите название",
        "введите имя автора"
    ];
    
    query("#record_card .localField input").forEach(function(field, index, fieldList){
        fieldList[index].placeholder = placeHolderArray[index];    
    });    
    query("#record_card #description")[0].placeholder = 
            "введите краткое описание видео";    
}

//сделать поля доступными только для чтения или для редактирования
function setReadOnlyFields(isReadOnly) {
    
    //определить чтение или редактиование
    if (typeof isReadOnly === "undefined") {
        isReadOnly = true;
    }
    
    //обычные текстовые поля, узлы с классом .localField
    query("#record_card .localField input").forEach(function(field, index, fieldList){              
        fieldList[index].readOnly = isReadOnly;
    });  
    
    //список, коллекция
    groupSelect.disabled = isReadOnly;
    
    //краткое описание
    query("#record_card #description")[0].disabled = isReadOnly;  
}

function getContentJson() {
    return { 
        name: query("#record_card #name")[0].value,
        author: query("#record_card #author")[0].value,
        description: query("#record_card #description")[0].value,
        groupId: groupSelect.value
    };
}

function getContentJsonWithoutDescription() {
    return {
        name: encodeURIComponent(query("#record_card #name")[0].value),
        author: encodeURIComponent(query("#record_card #author")[0].value),
        groupId: groupSelect.value
    };
}

function getContentJsonWithId() {
    return { 
        id: query("#record_card #id")[0].value,
        name: query("#record_card #name")[0].value,
        author: query("#record_card #author")[0].value,
        description: query("#record_card #description")[0].value,
        groupId: groupSelect.value
    };
}

//создать новую кнопку
function getDojoButton(options, Button, buttonFunction) {
    /*options = {id: "id", caption: "caption"}
     * Button - widget class*/
    
    var button = new Button({
            label: options.caption,
            hide: true, 
            onClick: buttonFunction
        }, options.id);
    button.startup();
    
    if (options.hide !== undefined)
        hideWidget(button);
    else
        showWidget(button); 
    return button;
};

// инициализация кнопок Сохранить, Найти
// а также списка Коллекция
function initCardFields(Button, FilteringSelect, Memory) {
    
    if (typeof saveButton !== "undefined") 
        return;
    
    //инициализация кнопки Сохранить
    saveButton = getDojoButton({
        id: "saveRecordButton",
        caption: "сохранить",
        hide: true
    }, Button, function () {
          
        if (card_mode === CM_ADD)
            createRecord();
        else
            editRecord();
        cardDialog.hide();
        renderContent("");
    });

    //инициализация кнопки Найти
    findButton = getDojoButton({
        id: "findRecordButton",
        caption: "найти",
        hide: true}, Button, 
        function () {
            cardDialog.hide();
            lastPageButton = 0;
            renderContent(getContentJsonWithoutDescription(), listServicePath  +"/fsearch");
    }); 

    //инициализация списка Коллекция
    initGroupsField(FilteringSelect, Memory);
}

//Rest-запрос на добавление
function createRecord() {
    
    jsonRest.target = listServicePath;
    jsonRest.add(getContentJson()).then(
        function(){
            alert("Запись успешно добавлена!");
            jsonRest.target = listServicePath;
            renderContent("");
        },
        function(){
            //alert(errorResponse.response.status);
            alert("Ошибка сохранения записи");
           //alert("Ошибка сервера");
        }
    ); 
}

//Rest-запрос на редактирование
function editRecord() {
    jsonRest.target = listServicePath;
    jsonRest.put(getContentJsonWithId()).then(
        function(){
            alert("запись изменена!");
            renderContent("");
        },
        function(){
            alert("ошибка редактирования записи");
        }
    );
}

//скрыть dojo-widget
function hideWidget(widget) {
    domStyle.set(widget.domNode, 'display', 'none'); 
}

//отобразить dojo-widget
function showWidget(widget) {
    domStyle.set(widget.domNode, 'display', 'inline-block'); 
}
  
require([       
    // to turn-on widgets
    "dojo/dom-attr",   
    "dojo/query",
    "dojo/on",
    "dojo/store/Memory",
    "dojo/ready",
    "dijit/registry",
    "dijit/form/Button",
    "dijit/form/FilteringSelect",
    "dojo/dom-style",
    // code work only after dom is loaded
    "dojo/domReady!",
    "dojo/parser"

], function (
    domAttr, query, on, Memory, ready, registry, 
    Button, FilteringSelect,
    domStyle) {
    
    //глобальный доступ к dojo-модулям
    window.domAttr = domAttr;
    window.domStyle = domStyle;
    window.query = query;
    window.registry = registry;
        

    // обновить при первом открытии
    on(cardDialog, "load", function() {          
        initCardFields(Button, FilteringSelect, Memory);
        updateCard(Memory);
    });  
        
    // обновить при последующих открытиях
    on(cardDialog, "show", function() {
        
        
        //выход, если первое открытие
        if (!cardDialog.isLoaded) {
            resetTimer();
            return;   
        }
  
        updateCard(Memory); 
        resetTimer();
    });
       
    // предварительное обновление при закрытии
    on(cardDialog, "hide", function() {           
        
        switch (card_mode) {
            //просмотр
            case CM_VIEW:
                setReadOnlyFields(false);
                break;
            //редактирование
            case CM_EDIT:
            //добавление
            case CM_ADD:
                //скрыть кнопку Сохранить              
                hideWidget(saveButton);
                break;
            //поиск
            case CM_FIND:
                //скрыть кнопку Найти
                hideWidget(findButton);
                break;
        }
    });  
});              