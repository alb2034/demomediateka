package com.alb.test.functional.pom;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;

/**
 * Класс для тестирования страницы со списком единиц контента.
 * 
 * @author Альберт
 */
public class ContentPage {
    private final SelenideElement addRecord;
    private final SelenideElement cancelButton;
    private final SelenideElement description;
    private final SelenideElement logoutHref;
    private final SelenideElement saveRecord;

    /**
     * Инициализирует элементы страницы.
     */
    public ContentPage() {
        this.logoutHref = $(byLinkText("выйти"));
        this.addRecord = $("#addRecord");
        this.description = $(byCssSelector("textarea#description"));
        this.saveRecord = $("#saveRecordButton");
        this.cancelButton = $("#cancelButton");
    }
    
    /**
     * Возвращает ссылку на объект кнопки Добавить.
     * 
     * @return ссылка на кнопку Добавить
     */
    public SelenideElement getAddButton() {
        return addRecord;
    }
    
    /**
     * Возвращает ссылку на объект кнопки Отмена.
     * 
     * @return ссылка на кнопку Отмена
     */
    public SelenideElement getCancelButton() {
        return cancelButton;
    }
    
    /**
     * Возвращает ссылку на объект кнопки Сохранить.
     * 
     * @return ссылка на кнопку Сохранить
     */
    public SelenideElement getSaveButton() {
        return saveRecord;
    }
    
    /**
     * Возвращает ссылку на объект гиперссылки Выйти.
     * 
     * @return ссылка на гиперссылку Выйти
     */
    public SelenideElement getLogoutHref() {
        return logoutHref;
    }
    
    /**
     * Возвращает ссылку на строку таблицы, соответствующейзаписи таблицы в бд.
     * 
     * @param   rowId
     *          номер уникальной записи, соотвествующей строки таблицы
     * 
     * @return ссылка на строку таблицы
     */
    public SelenideElement getRowById(long rowId) {
        return $(byXpath("//div[starts-with(@class,'gridxRow') and @rowid='" + 
                String.valueOf(rowId) + "']"));
    }
    
    /**
     * Открывает карточку записи единицы контента.
     * 
     * @param   rowId
     *          уникальной номер контента, для которой должна быть карточка
     *          открыта.
     * 
     * @return  ссылка на данный объект 
     */
    public ContentPage openCardById(long rowId) {
        SelenideElement card = 
                $(byXpath("//div[starts-with(@class,'gridxRow') and @rowid='" + 
                String.valueOf(rowId) + 
                "']//*[contains(@id, 'dijit_form_Button')]"));
        card.shouldBe(exist).hover().contextClick();
        return this;
    }
    
    /**
     * Возвращает ссылку на элемент контекстного меню таблицы со списком.
     * 
     * @param   itemLabel
     *          имя элемента меню
     * 
     * @return  ссылка на элемент контекстного меню
     */
    public SelenideElement getMenuItemByLabel(String itemLabel) {
        return $(byXpath("//tr[starts-with(@id,'dijit_MenuItem') "
                + "and contains(@aria-label,'" 
                + itemLabel + "')]"));
    }
    
    /**
     * Возвращает ссылку на элемент страницы Описание
     * 
     * @return  ссылка на элемент Описание
     */
    public SelenideElement getDescription() {
        return description;
    }
}
