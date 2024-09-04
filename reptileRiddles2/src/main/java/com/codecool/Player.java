package com.codecool;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Random;

public class Player {
    WebDriver driver;
    
    public Player() {
        driver = new ChromeDriver();
        driver.get("http://localhost:3000");
    }
    
    public void PlayerLogIn(){
        Dotenv dotenv = Dotenv.load();

        String username = dotenv.get("PLAYER_ID");
        String password = dotenv.get("PLAYER_PASSWORD");

        LogIn log = new LogIn();
        log.logIn(driver, username,password);
    }
    
    public void PlayerJoinAGame(){
        WebElement games = driver.findElement(By.cssSelector(".antialiased:nth-child(1) .hidden"));
        games.click();

        List<WebElement> joinButtons = driver.findElements(By.cssSelector(".antialiased:nth-child(1) .hidden"));
        
        if(joinButtons.size() > 1){
            Random rand = new Random();
            int randomIndex = rand.nextInt(joinButtons.size());
            joinButtons.get(randomIndex).click();
        } else {
            joinButtons.getFirst().click();
        }
        
        WebElement joinConfirm = driver.findElement(By.cssSelector(".pb-16"));
    }
    
    
    
    
}
