package com.codecool.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;

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
    private final By scoreboardDiv =(By.cssSelector(".mx-auto"));
    
    public void clickCreateLobby() {wait.until(ExpectedConditions.visibilityOfElementLocated(crateLobbyButton)).click();}

    public void clickStart(){wait.until(ExpectedConditions.visibilityOfElementLocated(startGameButton)).click();}
    
    public void clickResult(){wait.until(ExpectedConditions.visibilityOfElementLocated(resultButton)).click();}
    
    public void clickNext(){wait.until(ExpectedConditions.visibilityOfElementLocated(nextButton)).click();}

    public String scoreBoardText(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(scoreboardDiv));
        return driver.findElement(By.cssSelector(".mx-auto")).getText();
    }
    
    public boolean startButton() {
        WebElement startButton = driver.findElement(startGameButton);
        return startButton.isEnabled();
    }
    
    
    
    
}
