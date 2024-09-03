package com.codecool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUp {
    public static void Signup(WebDriver driver, String username, String password, String email) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement signupButtonNavBar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[2]/a[2]")));
            signupButtonNavBar.click();

            WebElement usernameInput = driver.findElement(By.id("user-name"));
            usernameInput.sendKeys(username);

            WebElement emailInput = driver.findElement(By.id("email"));
            emailInput.sendKeys(email);

            WebElement passwordInput = driver.findElement(By.id("password"));
            passwordInput.sendKeys(password);

            WebElement signupButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/button"));
            driver.manage().timeouts().wait(1000);
            signupButton.click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
