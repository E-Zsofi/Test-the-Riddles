package com.codecool;

import com.codecool.pages.*;
import com.codecool.utilitiy.CreateGame;
import com.codecool.utilitiy.DBPopulateQuiz;
import com.codecool.utilitiy.DBPopulateUser;
import com.codecool.utilitiy.DatabaseMod;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.utilitiy.DatabaseMod.PostgresTruncateMultipleTables;
import static org.junit.jupiter.api.Assertions.*;

class RiddleTest {
    WebDriver driver;
    WebDriverWait wait;
    QuizGamePage quizGamePage;
    LobbyPage lobbyPage;
    ResultPage resultPage;
    GameListPage gameListPage;
    DBPopulateUser dbPopulateUser;
    DBPopulateQuiz dbPopulateQuiz;
    String username,email,password, BASE_URL;
    

    @BeforeEach
    void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        dbPopulateQuiz = new DBPopulateQuiz(driver, wait);
        List<String> answers = new ArrayList<>();
        answers.add("Answer1");
        answers.add("Answer2");
        Dotenv dotenv = Dotenv.load();
        username = dotenv.get("PLAYER_USERNAME");
        email = dotenv.get("PLAYER_EMAIL");
        password = dotenv.get("PLAYER_PASSWORD");
        driver.get("http://localhost:3000");
        dbPopulateUser = new DBPopulateUser(driver,wait,username,email,password);
        dbPopulateUser.populateUser();
        dbPopulateQuiz.populateQuiz("Quiz1","Test Question1",answers);
        CreateGame.createGame(driver,wait,0);
        quizGamePage = new QuizGamePage();
        lobbyPage = new LobbyPage(driver,wait);
    }


    @AfterEach
    void tearDown() {
        PostgresTruncateMultipleTables();
        //driver.quit();
        
    }
    
    @Test
    void testPlayerSuccessfullyJoin() throws InterruptedException {
        
        quizGamePage.joinALobby();
        Assertions.assertTrue( quizGamePage.goodLuckIsVisible());
    }

   @Test
    void testPlayerCanJoinOnceOnly(){
        quizGamePage.joinALobby();
       quizGamePage.driver.navigate().to("http://localhost:3000");
       quizGamePage.joinALobby();
       int actualPlayersNumber = quizGamePage.playersNumber();
        Assertions.assertEquals(1,actualPlayersNumber);
    }
    
    @Test
    void testPlayerSeeTheDetailsOf(){
        quizGamePage.joinALobby();
        String playersText = quizGamePage.playersText();
        String questionsText = quizGamePage.questionText();
        Assertions.assertFalse(playersText.isEmpty());
        Assertions.assertFalse(questionsText.isEmpty());
       
    }
    
    @Test
    void testGameMasterSeeTheResults(){
        quizGamePage.joinALobby();
        lobbyPage.clickStart();
        lobbyPage.clickResult();
        /* quizGamePage.clickRandomButton(); */
        String result = lobbyPage.scoreBoardText();
        Assertions.assertFalse(result.isEmpty());
    }
    
    @Test
    void NoPlayersNoGame(){
        boolean actualResult = lobbyPage.startButton();
        assertFalse(actualResult);
    }
}