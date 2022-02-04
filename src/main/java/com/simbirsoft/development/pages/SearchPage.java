package com.simbirsoft.development.pages;

import com.simbirsoft.development.utility.WaitElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage {

    private final WebElement searchEdit;

    public SearchPage(WebDriver driver) {
        searchEdit = WaitElement.waitElementVisible(driver, By.name("q"));
    }

    @Step("Поиск по строке '{text}'")
    public void searchText(String text) {
        searchEdit.sendKeys(text, Keys.ENTER);
    }
}
