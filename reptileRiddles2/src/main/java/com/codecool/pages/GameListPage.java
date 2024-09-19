package com.codecool.pages;

import kotlin.coroutines.CoroutineContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class GameListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By joinButtonLocator= By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div/button");

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
        wait.until(ExpectedConditions.presenceOfElementLocated(joinButtonLocator)).click();
    }

}
