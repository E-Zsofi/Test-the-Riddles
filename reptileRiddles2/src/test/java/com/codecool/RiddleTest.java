package com.codecool;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RiddleTest {
     WebDriver driver;
    

    @BeforeEach
    void setUp() {

        driver = new FirefoxDriver();
        driver.get("http://localhost:3000");

        Dotenv dotenv = Dotenv.load();

        String usernameGameMaster = dotenv.get("GAMEMASTER");
        String passwordGameMaster = dotenv.get("GAMEMASTER_PASSWORD");

        LogIn log = new LogIn();
        log.logIn(driver, usernameGameMaster,passwordGameMaster);
        
        
    }

    @AfterEach
    void tearDown() {
        //logout
        
        //driver.quit();
    }
    
    @Test
    void testPlayerSuccessfullyJoin() {
        Player player = new Player();
        player.PlayerLogIn();
        player.PlayerJoinAGame();
        WebElement goodLuck = player.driver.findElement(By.xpath("//button[text()='Good luck!']"));
        Assertions.assertTrue(goodLuck.isDisplayed());
    }

    @Test
    void testPlayerCanJoinOnceOnly(){
        Player player = new Player();
        player.PlayerLogIn();
        player.PlayerJoinAGame();
        player.driver.navigate().to("http://localhost:3000");
        player.PlayerJoinAGame();
        driver.navigate().to("http://localhost:3000");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement games = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".antialiased:nth-child(1) .hidden")));
        games.click();
        WebElement firstDiv = driver.findElement(By.cssSelector(".grow > div:first-child"));
        System.out.println(firstDiv.getText());
        List<WebElement> playersSpan = firstDiv.findElements(By.cssSelector("span"));
        String playersText = playersSpan.get(1).getText();
        System.out.println(playersText);
        String expectedPlayersText = "1 players";
        Assertions.assertEquals(expectedPlayersText,playersText);
    }
    
    @Test
    void testPlayerSeeTheDetailsOf(){
        Player player = new Player();
        player.PlayerLogIn();
        player.PlayerJoinAGame();
        
        WebElement playersDiv = player.driver.findElement(By.cssSelector(".p-4"));
        String playersText = playersDiv.getText();
        WebElement questionsDiv = player.driver.findElement(By.cssSelector(".p-2"));
        String questionsText = questionsDiv.getText();

        Assertions.assertFalse(playersText.isEmpty());
        Assertions.assertFalse(questionsText.isEmpty());
       
    }
    
    @Test
    void testGameMasterSeeTheResults(){
        GameMaster gameMaster = new GameMaster();
        gameMaster.startQuiz(driver);
        WebElement scoreboardDiv = driver.findElement(By.cssSelector(".mx-auto"));
        String scoreboardText = scoreboardDiv.getText();
        Assertions.assertFalse(scoreboardText.isEmpty());
    }
    
    @Test
    void testSetToTimeLimitTo3600(){
        
    }
}