package com.codecool;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.time.Duration;
import java.util.List;

public class GameMaster {
    
    public void createQuiz(WebDriver driver){
        
        WebElement myQuizzes = driver.findElement(By.cssSelector(".antialiased:nth-child(3) .hidden"));
        myQuizzes.click();
        WebElement addQuiz = driver.findElement(By.cssSelector(".w-32"));
        addQuiz.click();
        WebElement quizName = driver.findElement(By.id("name"));
        quizName.sendKeys("quiz1");
        WebElement saveQuiz = driver.findElement(By.xpath("//button[text()='Save quiz']"));
        saveQuiz.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }
    
    public void startQuiz(WebDriver driver){
        WebElement myQuizzes = driver.findElement(By.cssSelector(".antialiased:nth-child(3) .hidden"));
        myQuizzes.click();
        List<WebElement> playButtons = driver.findElements(By.cssSelector(".bg-green-400"));
        playButtons.getFirst().click();
        WebElement createLobby = driver.findElement(By.xpath("//button[text()='Create game lobby']"));
        createLobby.click();
        WebElement start = driver.findElement(By.xpath("//button[text()='Start']"));
        start.click();
        WebElement results = driver.findElement(By.xpath("//button[text()='Results']"));
        results.click();

    }
}
