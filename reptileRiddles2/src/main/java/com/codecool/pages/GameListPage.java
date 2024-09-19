package com.codecool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GameListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div[1]/div/button")
    private WebElement joinButton;


    public GameListPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public boolean isThereAnyPlayableGames(){
        return joinButton.isDisplayed();
    }

    public void joinGame() {
        joinButton.click();
    }

}
