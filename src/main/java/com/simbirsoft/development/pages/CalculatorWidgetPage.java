package com.simbirsoft.development.pages;

import com.simbirsoft.development.utility.WaitElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.TreeMap;

import static java.util.Map.entry;

public class CalculatorWidgetPage {

    private final WebDriver driver;
    private final WebElement calcExpressionInput;
    private final WebElement calcMemoryExpression;
    private final WebElement equalButton;
    private final Map<String, WebElement> commands;
    private final Map<Integer, String> operations = new TreeMap<>();

    public CalculatorWidgetPage(WebDriver driver) {
        this.driver = driver;
        calcExpressionInput = WaitElement.waitElementVisible(driver, By.id("cwos"));
        calcMemoryExpression = WaitElement.waitElementVisible(driver, By.xpath("//span[@jsname='ubtiRe']"));
        equalButton = WaitElement.waitElementVisible(driver, By.xpath("//div[@role='button' and text()='=']"));
        commands = getCommands(driver);
    }

    @Step("Вычисление выражения '{expression}'")
    public String calculateExpression(String expression) {
        updateOperations(expression, "sin");
        updateOperations(expression, "cos");

        for (int i = 0; i < expression.length(); i++) {
            if (operations.containsKey(i)) {
                commands.get(operations.get(i)).click();
                i += operations.get(i).length();
            } else {
                String command = Character.toString(expression.charAt(i));
                commands.get(command).click();
            }
        }

        equalButton.click();
        WaitElement.waitElementContainAttribute(driver, calcMemoryExpression, "textContent", "=");

        return calcExpressionInput.getText();
    }

    @Step("Получение выражения в строке памяти")
    public String getMemoryExpressionText() {

        return calcMemoryExpression.getText();
    }

    public String getMemoryExpressionRawText() {
        String currentTxt = getMemoryExpressionText();

        return currentTxt.replace(" ", "")
                .replace("×", "*")
                .replace("÷", "/");
    }

    private void updateOperations(String expression, String operation) {
        int operationLength = 0;
        int index = 0;

        while (index != -1) {
            index = expression.indexOf(operation, index + operationLength);
            if (index != -1) {
                operations.put(index, operation);
            }
            operationLength = operation.length();
        }
    }

    private Map<String, WebElement> getCommands(WebDriver driver) {
        return Map.ofEntries(
                entry("0", driver.findElement(By.xpath("//div[@role='button' and text()='0']"))),
                entry("1", driver.findElement(By.xpath("//div[@role='button' and text()='1']"))),
                entry("2", driver.findElement(By.xpath("//div[@role='button' and text()='2']"))),
                entry("3", driver.findElement(By.xpath("//div[@role='button' and text()='3']"))),
                entry("4", driver.findElement(By.xpath("//div[@role='button' and text()='4']"))),
                entry("5", driver.findElement(By.xpath("//div[@role='button' and text()='5']"))),
                entry("6", driver.findElement(By.xpath("//div[@role='button' and text()='6']"))),
                entry("7", driver.findElement(By.xpath("//div[@role='button' and text()='7']"))),
                entry("8", driver.findElement(By.xpath("//div[@role='button' and text()='8']"))),
                entry("9", driver.findElement(By.xpath("//div[@role='button' and text()='9']"))),
                entry("(", driver.findElement(By.xpath("//div[@role='button' and text()='(']"))),
                entry(")", driver.findElement(By.xpath("//div[@role='button' and text()=')']"))),
                entry("+", driver.findElement(By.xpath("//div[@role='button' and text()='+']"))),
                entry("-", driver.findElement(By.xpath("//div[@role='button' and text()='−']"))),
                entry("*", driver.findElement(By.xpath("//div[@role='button' and text()='×']"))),
                entry("/", driver.findElement(By.xpath("//div[@role='button' and text()='÷']"))),
                entry(".", driver.findElement(By.xpath("//div[@role='button' and text()='.']"))),
                entry("sin", driver.findElement(By.xpath("//div[@role='button' and text()='sin']"))),
                entry("cos", driver.findElement(By.xpath("//div[@role='button' and text()='cos']")))
        );
    }
}
