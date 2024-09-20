package com.codecool;

import com.codecool.pages.MyQuizPage;
import com.codecool.pages.NavbarComponent;
import com.codecool.pages.QuizFormComponent;
import com.codecool.pages.QuizFormPage;
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
import java.util.function.BooleanSupplier;

public class QuizTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private String quizName;
    private DatabaseMod databaseMod;
    private DBPopulateQuiz dbPopulateQuiz;
    private DBPopulateUser dbPopulateUser;
    private NavbarComponent navbarComponent;
    private MyQuizPage myQuizPage;
    private QuizFormPage quizFormPage;
    private QuizFormComponent quizFormComponent;


    @BeforeEach
    public void setUp() {
        databaseMod = new DatabaseMod();
        databaseMod.PostgresTruncateMultipleTables();
        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();

        String username = dotenv.get("PLAYER");
        String password = dotenv.get("PLAYER_PASSWORD");
        String email = dotenv.get("PLAYER_EMAIL");
        quizName = dotenv.get("QUIZ_NAME");

        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        dbPopulateUser = new DBPopulateUser(driver, wait, username, email, password);
        dbPopulateUser.populateUser();
        navbarComponent = new NavbarComponent(driver, wait);
        myQuizPage = new MyQuizPage(driver, wait);
        quizFormPage = new QuizFormPage(driver, wait);
        quizFormComponent = new QuizFormComponent(driver, wait);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }


    @Test
    public void newQuizWithExistingNameTest() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            navbarComponent.clickOnMyQuizzes();
            myQuizPage.clickAddQuizButton();
            quizFormPage.setQuizTitle("test");
            quizFormPage.clickAddQuestion();
            quizFormComponent.setQuestionText("Test name");
            List<String> answers = new ArrayList<>();
            answers.add("hello");
            answers.add("hi");
            quizFormComponent.addQuestionToQuiz("test", 20, answers, 2);
            quizFormPage.clickSaveQuiz();
            quizFormPage.acceptAlert();
        }

        Thread.sleep(1000);
        Assertions.assertFalse(driver.getCurrentUrl().contains("http://localhost:3000/quiz/all"));
    }

    @Test
    public void deleteQuizTest() {
        navbarComponent.clickOnMyQuizzes();
        myQuizPage.clickAddQuizButton();
        quizFormPage.setQuizTitle("test");
        quizFormPage.clickAddQuestion();
        quizFormComponent.setQuestionText("Test name");
        List<String> answers = new ArrayList<>();
        answers.add("hello");
        answers.add("hi");
        quizFormComponent.addQuestionToQuiz("test", 20, answers, 2);
        quizFormPage.clickSaveQuiz();
        quizFormPage.acceptAlert();
        driver.navigate().refresh();
        navbarComponent.clickOnMyQuizzes();
        myQuizPage.clickDeleteButton(0);
        int afterDelete = myQuizPage.getQuizzesNumber();

        Assertions.assertEquals(0, afterDelete);
    }

    @Test
    public void deleteAllQuiz() throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            navbarComponent.clickOnMyQuizzes();
            navbarComponent.clickOnMyQuizzes();
            myQuizPage.clickAddQuizButton();
            quizFormPage.setQuizTitle("test");
            quizFormPage.clickAddQuestion();
            quizFormComponent.setQuestionText("Test name");
            List<String> answers = new ArrayList<>();
            answers.add("hello" + i);
            answers.add("hi" + i);
            quizFormComponent.addQuestionToQuiz("test" + i, 20, answers, 2);
            quizFormPage.clickSaveQuiz();
            quizFormPage.acceptAlert();
            driver.navigate().refresh();
            navbarComponent.clickOnMyQuizzes();
        }
        int size = myQuizPage.getQuizzesNumber();
        for (int j = 0; j < size; j++) {
            myQuizPage.clickDeleteButton(0);
        }
        driver.navigate().refresh();
        int afterDelete = myQuizPage.getQuizzesNumber();

        Assertions.assertEquals(0, afterDelete);
    }




}
