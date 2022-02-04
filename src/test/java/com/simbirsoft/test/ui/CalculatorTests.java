package com.simbirsoft.test.ui;

import com.simbirsoft.development.pages.CalculatorWidgetPage;
import com.simbirsoft.development.pages.SearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTests {

    private WebDriver driver;

    @BeforeAll
    static void initDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    @Step("Инициализация WebDriver")
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    @Step("WebDriver quit()")
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка операций с целыми числами")
    void calculatorTest1() {

        driver.manage().window().maximize();
        driver.get(System.getProperty("baseURL"));

        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchText("Калькулятор");

        CalculatorWidgetPage calculator = new CalculatorWidgetPage(driver);
        assertEquals("1", calculator.calculateExpression("(1+2)*3-40/5"));
        assertEquals("(1+2)*3-40/5=", calculator.getMemoryExpressionRawText());
    }

    @Test
    @DisplayName("Проверка деления на ноль")
    void calculatorTest2() {

        driver.manage().window().maximize();
        driver.get(System.getProperty("baseURL"));

        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchText("Калькулятор");

        CalculatorWidgetPage calculator = new CalculatorWidgetPage(driver);
        assertEquals("Infinity", calculator.calculateExpression("6/0"));
        assertEquals("6/0=", calculator.getMemoryExpressionRawText());
    }

    @Test
    @DisplayName("Проверка ошибки при отсутствии значения")
    void calculatorTest3() {

        driver.manage().window().maximize();
        driver.get(System.getProperty("baseURL"));

        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchText("Калькулятор");

        CalculatorWidgetPage calculator = new CalculatorWidgetPage(driver);
        assertEquals("Error", calculator.calculateExpression("sin("));
        assertEquals("sin()=", calculator.getMemoryExpressionRawText());
    }
}
