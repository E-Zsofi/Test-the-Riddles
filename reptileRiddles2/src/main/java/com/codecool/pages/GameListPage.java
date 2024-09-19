package com.codecool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GameListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "#root > div > div.w-full.h-fit.bg-inherit > nav > div > div:nth-child(2) > ul > li:nth-child(2) > a > span")
    private WebElement quizzesButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div[1]/div[1]/button[2]")
    private WebElement playButton;


    public GameListPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void showGameList() {
        quizzesButton.click();
        playButton.click();
    }


}
