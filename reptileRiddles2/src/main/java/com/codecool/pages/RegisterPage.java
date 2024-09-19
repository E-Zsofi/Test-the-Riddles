package com.codecool.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public RegisterPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By emailInput = By.id("email");
    private By registerButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/button");

    public void enterUsername(String username) {
        WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        usernameElement.sendKeys(username);
    }

    public void enterEmail(String email) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailElement.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passwordElement.sendKeys(password);
    }

    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
        wait.until(ExpectedConditions.urlContains("http://localhost:3000/login"));
    }
    
    public void register(String username, String email,String password ) {
        enterUsername(username);
        enterPassword(password);
        enterEmail(email);
        clickRegister();
    }

}
