package com.codecool;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

class RiddleTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setUp() {

        driver = new FirefoxDriver();
        driver.get("http://localhost:3000");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Dotenv dotenv = Dotenv.load();

        String usernameGameMaster = dotenv.get("GAMEMASTER");
        String passwordGameMaster = dotenv.get("GAMEMASTER_PASSWORD");

        LogIn log = new LogIn();
        log.logIn(driver, usernameGameMaster,passwordGameMaster);
        
    }

    @AfterEach
    void tearDown() {
        driver.quit();
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
        WebElement myQuizzes = driver.findElement(By.cssSelector(".antialiased:nth-child(3) .hidden"));
        myQuizzes.click();
        List<WebElement> editButtons = driver.findElements(By.cssSelector(".bg-yellow-400"));
        editButtons.getFirst().click();
        WebElement firstQuestion = driver.findElement(By.xpath("//button[contains(.,'1. ,nbklj')]"));
        firstQuestion.click();
        WebElement setTime = driver.findElement(By.id("2time"));
        setTime.clear();
        setTime.sendKeys("3600");
        WebElement saveQuestion = driver.findElement(By.cssSelector(".mr-4"));
        saveQuestion.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        String expectedUrl = "http://localhost:3000/quizform/3";
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl,actualUrl);
    }

    @Test
    void testChangeToTimeLimit(){
        WebElement myQuizzes = driver.findElement(By.cssSelector(".antialiased:nth-child(3) .hidden"));
        myQuizzes.click();
        List<WebElement> editButtons = driver.findElements(By.cssSelector(".bg-yellow-400"));
        editButtons.getFirst().click();
        WebElement firstQuestion = driver.findElement(By.xpath("//button[contains(.,'1.')]"));
        firstQuestion.click();
        WebElement setTime = driver.findElement(By.xpath("//*[contains(@id, 'time')]"));
        setTime.clear();
        setTime.sendKeys("10");
        WebElement saveQuestion = driver.findElement(By.cssSelector(".mr-4"));
        saveQuestion.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        driver.navigate().to("http://localhost:3000/quiz/my");
        List<WebElement> refreshedEditButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".bg-yellow-400")));
        refreshedEditButtons.getFirst().click();
        WebElement refreshedFirstQuestion = driver.findElement(By.xpath("//button[contains(.,'1.')]"));
        refreshedFirstQuestion.click();
        WebElement refreshedSetTime = driver.findElement(By.xpath("//*[contains(@id, 'time')]"));
        String expectedResult = "10";
        System.out.println(refreshedSetTime.getAttribute("value"));
        Assertions.assertEquals(expectedResult,refreshedSetTime.getAttribute("value"));         
    }
    
    @Test
    void NoPlayersNoGame(){
        WebElement myQuizzes = driver.findElement(By.cssSelector(".antialiased:nth-child(3) .hidden"));
        myQuizzes.click();
        List<WebElement> playButtons = driver.findElements(By.cssSelector(".bg-green-400"));
        playButtons.getFirst().click();
        WebElement createLobby = driver.findElement(By.xpath("//button[text()='Create game lobby']"));
        createLobby.click();
        WebElement start = driver.findElement(By.xpath("//button[text()='Start']"));
        Assertions.assertFalse(start.isEnabled());
    }
        
}