package com.codecool.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class GameListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By joinButtonLocator= By.cssSelector(".bg-green-400");

    public GameListPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isThereAnyPlayableGames() {
        try {
            WebElement joinButton = wait.until(ExpectedConditions.presenceOfElementLocated(joinButtonLocator));
            return joinButton.isDisplayed();
        } catch (NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void joinGame() {
        //wait.until(ExpectedConditions.presenceOfElementLocated(joinButtonLocator)).click();

        List<WebElement> joinButtons = driver.findElements(joinButtonLocator);
        joinButtons.getFirst().click();
    }

}
