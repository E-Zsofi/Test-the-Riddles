package com.codecool;

import com.codecool.pages.*;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloneQuizTest {

    private WebDriver driver;
    private WebDriver gameMaster;
    private WebDriverWait wait;
    private WebDriverWait masterWait;
    private DatabaseMod databaseMod;
    private DBPopulateQuiz dbPopulateQuiz;
    private DBPopulateUser dbPopulateUser;
    private DBPopulateUser dbPopulateGameMaster;
    private NavbarComponent navbarComponent;
    private MyQuizPage myQuizPage;
    private QuizFormPage quizFormPage;
    private QuizFormComponent quizFormComponent;
    private AllQuizPage allQuizPage;

    @BeforeEach
    public void setUp() {
        databaseMod = new DatabaseMod();
        databaseMod.PostgresTruncateMultipleTables();
        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();
        driver.get("http://localhost:3000");
        String username = dotenv.get("PLAYER");
        String password = dotenv.get("PLAYER_PASSWORD");
        String email = dotenv.get("PLAYER_EMAIL");
        String gameMasterName = dotenv.get("GAMEMASTER");
        String gameMasterPassword = dotenv.get("GAMEMASTER_PASSWORD");
        String gamMasterEmail = dotenv.get("GAMEMASTER_EMAIL");
        dbPopulateUser = new DBPopulateUser(driver, wait, username, email, password);
        dbPopulateUser.populateUser();
        dbPopulateQuiz = new DBPopulateQuiz(driver, wait);
        List<String> answers = new ArrayList<>();
        answers.add("hello");
        answers.add("hi");
        dbPopulateQuiz.populateQuiz("Test", "Test?", answers );

        gameMaster = new ChromeDriver(options);
        masterWait = new WebDriverWait(gameMaster, Duration.ofSeconds(3));
        gameMaster.get("http://localhost:3000");
        dbPopulateGameMaster = new DBPopulateUser(gameMaster, masterWait, gameMasterName, gamMasterEmail, gameMasterPassword);
        dbPopulateGameMaster.populateUser();

        navbarComponent = new NavbarComponent(gameMaster, masterWait);
        myQuizPage = new MyQuizPage(gameMaster, masterWait);
        quizFormComponent = new QuizFormComponent(gameMaster, masterWait);
        quizFormPage = new QuizFormPage(gameMaster, masterWait);
        allQuizPage = new AllQuizPage(gameMaster, masterWait);

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        gameMaster.quit();
        databaseMod.PostgresTruncateMultipleTables();
    }


    @Test
    public void cloneAnotherQuizMastersQuiz() throws InterruptedException {
    navbarComponent.clickOnQuizzes();
    int beforeAdd = allQuizPage.getQuizzesNumber();
    allQuizPage.clickCopyButton(0);
    quizFormPage.clickSaveQuiz();
    quizFormPage.acceptAlert();
    int afterAdd = allQuizPage.getQuizzesNumber();
    Assertions.assertEquals(beforeAdd + 1, afterAdd);
    }
}