package com.codecool;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Player {
    WebDriver driver;
    private WebDriverWait wait;
    
    public Player() {

        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("http://localhost:3000");
    }
    
    public void PlayerLogIn(){
        Dotenv dotenv = Dotenv.load();

        String username = dotenv.get("PLAYER");
        String password = dotenv.get("PLAYER_PASSWORD");

        LogIn log = new LogIn();
        log.logIn(driver, username,password);
    }
    
    public void PlayerJoinAGame(){
        WebElement games = driver.findElement(By.cssSelector(".antialiased:nth-child(1) .hidden"));
        games.click();

        List<WebElement> joinButtons = driver.findElements(By.cssSelector(".antialiased:nth-child(1) .hidden"));
            joinButtons.getFirst().click();
        
        WebElement joinConfirm = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pb-16")));

        joinConfirm.click();
    }
    
    
    
    
}
