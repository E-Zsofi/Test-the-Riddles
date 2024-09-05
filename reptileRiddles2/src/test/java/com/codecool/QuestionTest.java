package com.codecool;

import io.github.cdimascio.dotenv.Dotenv;
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

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement quizTitleInput;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();
        driver.get("http://localhost:3000");
        String username = dotenv.get("PLAYER");
        String password = dotenv.get("PLAYER_PASSWORD");
        LogIn log = new LogIn();
        log.logIn(driver, username, password);
    }

    @Test
    void addMultipleAnswersToAQuestionTest() {
        WebElement myQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'My Quizzes')]")));
        myQuizzes.click();
        WebElement addQuizButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/button")));
        addQuizButton.click();
        WebElement quizTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"name\"]")));
        quizTitleInput.sendKeys("Test Quiz");
        WebElement addQuestionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/button")));
        addQuestionButton.click();
        WebElement questionTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"-1question\"]")));
        questionTitleInput.sendKeys("Test Question");
        WebElement answerOptionInput1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"answer-1\"]")));
        answerOptionInput1.sendKeys("test1");
        WebElement answerOptionInput2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"answer-2\"]")));
        answerOptionInput2.sendKeys("test2");
        WebElement addMoreAnswers = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div/div[3]/div[4]/button")));
        addMoreAnswers.click();
        WebElement answerOption3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"answer-3\"]")));
        answerOption3.sendKeys("test3");
        WebElement correctAnswerCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkbox-1\"]")));
        correctAnswerCheckbox.click();
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div/div[4]/button[1]")));
        saveButton.click();
        Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
        alert1.accept();
        WebElement saveQuiz = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/button[1]")));
        saveQuiz.click();
        Alert alert2 = wait.until(ExpectedConditions.alertIsPresent());
        alert2.accept();
    }

    @Test
    void addAnswersToExistingQuestion() {
        WebElement myQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'My Quizzes')]")));
        myQuizzes.click();
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div[1]/button[2]")));
        editButton.click();
        WebElement questionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div/button")));
        questionButton.click();
        WebElement addNewOptionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), '+ Add option')]")));
        addNewOptionButton.click();
        WebElement newInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"answer-3\"]")));
        newInput.sendKeys("Nem is vagyok");
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div/div[4]/button[1]")));
        saveButton.click();
        Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
        alert1.accept();
        WebElement saveQuiz = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/button[1]")));
        saveQuiz.click();
        Alert alert2 = wait.until(ExpectedConditions.alertIsPresent());
        alert2.accept();
    }

    @Test
    void setOneCorrectAnswerToAQuestion() throws InterruptedException {
        WebElement myQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'My Quizzes')]")));
        myQuizzes.click();
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div[1]/button[2]")));
        editButton.click();
        WebElement questionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div/button")));
        questionButton.click();
        WebElement correctCheckbox1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkbox-2\"]")));
        correctCheckbox1.click();
        WebElement correctCheckbox2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkbox-3\"]")));
        correctCheckbox2.click();
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div/div[4]/button[1]")));
        saveButton.click();
        Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
        alert1.accept();
        WebElement saveQuiz = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/button[1]")));
        saveQuiz.click();
        Alert alert2 = wait.until(ExpectedConditions.alertIsPresent());
        alert2.accept();
        Thread.sleep(2000);
        assertTrue(driver.getCurrentUrl().contains("http://localhost:3000/quiz/all"));
    }

    @Test
    void setTimerToZero() throws InterruptedException {
        WebElement myQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'My Quizzes')]")));
        myQuizzes.click();
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div[2]/button[2]")));
        editButton.click();
        WebElement questionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div/button")));
        questionButton.click();
        WebElement timerInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'time')]")));
        timerInput.click();
        timerInput.clear();
        timerInput.sendKeys("0");
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div/div[4]/button[1]")));
        saveButton.click();
        Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
        alert1.accept();
        WebElement saveQuiz = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/button[1]")));
        saveQuiz.click();
        Alert alert2 = wait.until(ExpectedConditions.alertIsPresent());
        alert2.accept();
        Thread.sleep(1000);
        assertTrue(driver.getCurrentUrl().contains("http://localhost:3000/quiz/all"));
    }
}