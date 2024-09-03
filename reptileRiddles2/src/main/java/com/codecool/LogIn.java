package com.codecool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogIn {
    
    public void logIn(WebDriver driver, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement logInButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".text-gray-900 > .hidden")));
        wait.until(ExpectedConditions.elementToBeClickable(logInButton));
        logInButton.click();

        WebElement userNameInput = driver.findElement(By.id("user-name"));
        userNameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys(password);
        WebElement submitButton = driver.findElement(By.cssSelector(".mr-4"));
        submitButton.click();
        
    }
}
