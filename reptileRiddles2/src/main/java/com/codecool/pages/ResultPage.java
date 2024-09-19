package com.codecool.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class ResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ResultPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    
    private final By title = By.cssSelector(".pt-20");
    
    public String getTitle() {
        WebElement titleText = wait.until(ExpectedConditions.visibilityOfElementLocated(title));
        return titleText.getText();
    }
    
    
    
    
}
