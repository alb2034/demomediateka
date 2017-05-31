package com.alb.test.functional;

import com.alb.test.functional.pom.ContentPage;
import com.alb.test.functional.pom.LoginPage;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import io.github.bonigarcia.wdm.*;
import org.testng.annotations.*;

/**
 * Проверка правильности авторизации пользователя в качестве администратора.
 * 
 * @author Альберт
 */
@SuppressWarnings("FieldMayBeFinal")
public class AdminTest {
    
    private LoginPage loginPage;
    private ContentPage contentPage;
    
    /**
     * Инициализирует страницы с авторизацией и со списком.
     */
    public AdminTest() {
        loginPage = new LoginPage();
        contentPage = new ContentPage();
    }
    
    /**
     * Проверка возможности авторизации в качестве администратора.
     */
    @Test(enabled = true)
    public void canLogin() {

        loginPage.shouldBeOpened()
            .setLogin("admin")
            .clickAuthorize();
  
        contentPage.getAddButton().shouldBe(visible);
    }
    
    /**
     * Проверка возможности просмотра карточки.
     */
    @Test(enabled = true)
    public void canViewCard() {
        contentPage.openCardById(1)
            .getSaveButton().shouldNotBe(visible); 
        contentPage.getCancelButton()
            .shouldBe(visible).click();
        
    }
    
    /**
     * Проверка на правильность редактирования записи.
     */
    @Test(enabled = true, priority = 1)
    public void canEditCard() {
        
        //навести курсор на строку таблицы (id=1)    
        contentPage.getRowById(1)
                .shouldBe(exist).hover().contextClick();
        
        //активировать контекстное меню, выбрать "редактировать"
        contentPage.getMenuItemByLabel("редактировать")
                .shouldBe(visible).click();
        
        //добавить в карточке к описанию слово "тест"
        String newDescription = 
                contentPage.getDescription().getValue() + " тест";
        
        contentPage.getDescription().setValue(newDescription);
        
        //нажать на кнопку Сохранить
        contentPage.getSaveButton()
                .shouldBe(visible).click();
        switchTo().alert().accept();
        
        //вновь открыть карточку, проверить описание
        contentPage.openCardById(1)
            .getDescription().shouldHave(exactValue(newDescription));
        contentPage.getCancelButton().click();
    }
    
    /**
     * Проверка на правильность выхода администратором из авторизации.
     */
    @Test(enabled = true, priority = 2)   
    public void canLogout() {
        
        contentPage.getLogoutHref()
                .shouldBe(visible).followLink();
        switchTo().alert().accept();
        
        loginPage.shouldBeOpened();
    }
    
    /**
     * Инициализация адреса страницы авторизации, выбор браузера для теста.
     */
    @BeforeTest
    public synchronized void setupTest() {
        baseUrl = "http://localhost:8084/library";
        ChromeDriverManager.getInstance().setup();        
        browser = CHROME;
        
        open("/");
    }
}