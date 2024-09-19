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

class QuestionTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement quizTitleInput;
    private String BASE_URL;
    private String username;
    private String password;
    private String email;
    private DBPopulateUser dbPopulateUser;
    private DBPopulateQuiz dbPopulateQuiz;
    private NavbarComponent navbarComponent;
    private QuizFormPage quizFormPage;
    private QuizFormComponent quizFormComponent;
    private MyQuizPage myQuizPage;
    private List<String> answers;
    private String quizName;
    private String questionText;
    private DatabaseMod databaseMod;

    @BeforeEach
    public void setUp() throws InterruptedException {
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
        BASE_URL = dotenv.get("BASE_URL");
        driver.get(BASE_URL);
        username = dotenv.get("PLAYER") + System.currentTimeMillis();
        password = dotenv.get("PLAYER_PASSWORD");
        email = dotenv.get("PLAYER_EMAIL");
        dbPopulateUser = new DBPopulateUser(driver, wait, username, email, password);
        dbPopulateUser.populateUser();
        navbarComponent = new NavbarComponent(driver, wait);
        quizFormPage = new QuizFormPage(driver, wait);
        quizFormComponent = new QuizFormComponent(driver, wait);
        myQuizPage = new MyQuizPage(driver, wait);
        answers = new ArrayList<>();
        answers.add("Yes");
        answers.add("No");
        dbPopulateQuiz = new DBPopulateQuiz(driver, wait);
        quizName = "something";
        questionText = "why?";
        dbPopulateQuiz.populateQuiz(quizName, questionText, answers);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void addMultipleAnswersToAQuestionTest() {
        navbarComponent.clickOnQuizzes();
        myQuizPage.clickAddQuizButton();
        quizFormPage.setQuizTitle("Test");
        quizFormPage.clickAddQuestion();
        quizFormComponent.setQuestionText("Test Question, yes?");
        for (int i = 0; i < answers.size(); i++) {
            quizFormComponent.setAnswerOption(i+1, answers.get(i));
        }
        quizFormComponent.clickAddOption();
        quizFormComponent.setAnswerOption(3, "Maybe");
        quizFormComponent.markAnswerAsCorrect(1);
        quizFormComponent.clickSave();
        quizFormPage.acceptAlert();
        quizFormPage.clickSaveQuiz();
        quizFormPage.acceptAlert();
    }

    @Test
    void addAnswersToExistingQuestion() {
        navbarComponent.clickOnMyQuizzes();
        driver.navigate().refresh();
        quizFormPage.clickEditQuiz();
        driver.navigate().refresh();
        quizFormPage.clickOnAnAlreadyExistingQuestion(questionText);
        quizFormComponent.clickAddOption();
        quizFormComponent.setAnswerOption(3, "Don't know");
        quizFormComponent.clickSave();
        quizFormPage.acceptAlert();
        quizFormPage.clickSaveQuiz();
        quizFormPage.acceptAlert();
    }

    @Test
    void setOnlyOneCorrectAnswerToAQuestion() {
        navbarComponent.clickOnMyQuizzes();
        quizFormPage.clickEditQuiz();
        quizFormPage.clickOnAnAlreadyExistingQuestion(questionText);
        quizFormComponent.markAnswerAsCorrect(2);
        quizFormComponent.clickSave();
        quizFormPage.acceptAlert();
        quizFormPage.clickSaveQuiz();
        quizFormComponent.acceptAlert();
        assertFalse(driver.getCurrentUrl().contains(BASE_URL+"/quiz/all"));
    }

    @Test
    void setTimerToZero() {
        navbarComponent.clickOnMyQuizzes();
        quizFormPage.clickEditQuiz();
        quizFormPage.clickOnAnAlreadyExistingQuestion(questionText);
        quizFormComponent.setTimeLimit(0);
        quizFormComponent.clickSave();
        quizFormComponent.acceptAlert();
        quizFormPage.clickSaveQuiz();
        quizFormPage.acceptAlert();
        assertFalse(driver.getCurrentUrl().contains(BASE_URL+"/quiz/all"));
    }
}