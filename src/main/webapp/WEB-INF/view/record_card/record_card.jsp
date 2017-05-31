<%@page contentType="text/html" pageEncoding="utf-8"%>

<body class="claro">
    <table id="record_card">
        <tr class="idField">
            <td><label for="id">ID:</label></td>
            <td><input id="id" data-dojo-type="dijit/form/TextBox" readonly="readonly"></td>
        </tr>
        <tr>
            <td><label for="group">Коллекция:</label></td>
            <td>
                <div id="group"></div>
            </td>
        </tr>       
        <tr>   
            <td><label for="name">Имя:</label></td>
            <td><input class="localField" id="name" data-dojo-type="dijit/form/TextBox"
                ></td> 
        </tr>
        <tr>
            <td><label for="author">Автор:</label>
            <td><input class="localField" id="author" data-dojo-type="dijit/form/TextBox"
                ></td>
        </tr> 
        <tr class="descriptionField">
            <td><label for="description">Описание:</label>
            <td colspan="1"><textarea id="description" rows="7" cols="35"
                    data-dojo-type="dijit/form/SimpleTextarea"
                    data-dojo-props="selectOnClick: true">
            </textarea> </td>  
        </tr>
    </table>
    
    <!--кнопки редактирования записи-->
    <div id="bottomTool">
               
        <!--отмена-->
        <button id="cancelButton" 
                data-dojo-type="dijit/form/Button"
                onclick="hideRecordCardDialog">
            <span>отмена</span>
        </button> 
        
        <!--поиск записи-->
        <button id="findRecordButton" type="button"></button>
        <!--сохранить запись-->
        <button id="saveRecordButton" type="button"></button>
    </div>
    
</body>

