package com.codecool.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LobbyPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LobbyPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    
    private final By crateLobbyButton =(By.xpath("//button[text()='Create game lobby']"));
    private final By startGameButton =(By.xpath("//button[text()='Start']"));
    private final By resultButton =(By.xpath("//button[text()='Results']"));
    private final By nextButton =(By.xpath("//button[text()='Next']"));
    
    public void clickCreateLobby() {wait.until(ExpectedConditions.visibilityOfElementLocated(crateLobbyButton)).click();}

    public void clickStart(){wait.until(ExpectedConditions.visibilityOfElementLocated(startGameButton)).click();}
    
    public void clickResult(){wait.until(ExpectedConditions.visibilityOfElementLocated(resultButton)).click();}
    
    public void clickNext(){wait.until(ExpectedConditions.visibilityOfElementLocated(nextButton)).click();}
    
    
    
}
