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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
    }

    @Test
    public void deleteAllQuiz() throws InterruptedException {
        WebElement myQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'My Quizzes')]")));
        myQuizzes.click();
        Thread.sleep(500);
        int count = driver.findElements(By.xpath("//*[contains(text(), 'Delete')]")).size();
        List<WebElement> Deletes = wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[contains(text(), 'Delete')]"), count));
        for (WebElement delete : Deletes) {
            delete.click();
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        }
        Thread.sleep(1000);
        int afterDelete = driver.findElements(By.xpath("//*[contains(text(), 'Delete')]")).size();
        Assertions.assertEquals(0,afterDelete);
    }

    @Test
    public void modifyAnExistingQuiz() {
        WebElement myQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'My Quizzes')]")));
        myQuizzes.click();
        WebElement addQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Add Quiz')]")));
        addQuizzes.click();
        WebElement quizTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        quizTitle.sendKeys("quizName");
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
        myQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'My Quizzes')]")));
        myQuizzes.click();
        WebElement edit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Edit')]")));
        edit.click();
        WebElement existingQuestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div")));
        existingQuestion.click();
        questionTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Test Question']\n")));
        questionTitleInput.clear();
        questionTitleInput.sendKeys("Edited Question");
        saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div/div[4]/button[1]")));
        saveButton.click();
        alert1 = wait.until(ExpectedConditions.alertIsPresent());
        alert1.accept();
        saveQuiz = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/button[1]")));
        saveQuiz.click();
        alert2 = wait.until(ExpectedConditions.alertIsPresent());
        alert2.accept();
    }



}
