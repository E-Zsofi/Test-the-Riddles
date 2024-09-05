package com.codecool;

import io.github.cdimascio.dotenv.Dotenv;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloneQuizTest {

    private WebDriver driver;
    private WebDriverWait wait;

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
        String username = dotenv.get("PLAYER2_USERNAME");
        String password = dotenv.get("PLAYER2_PASSWORD");
        LogIn log = new LogIn();
        log.logIn(driver, username, password);
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
    }

    @Test
    public void cloneAnotherQuizMastersQuiz() throws InterruptedException {
        WebElement allQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[1]/ul/li[2]/a/span")));
        allQuizzes.click();
        WebElement copyQuizButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div[1]/button[1]")));
        copyQuizButton.click();
        WebElement saveQuiz = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/button[1]")));
        saveQuiz.click();
        Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
        alert1.accept();
        WebElement myQuizzes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[1]/ul/li[3]/a/span")));
        myQuizzes.click();
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Delete')]")));
       Assertions.assertTrue(deleteButton.isDisplayed());
    }
}