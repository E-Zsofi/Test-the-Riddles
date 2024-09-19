package com.codecool.pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class QuizGamePage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    public QuizGamePage() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("http://localhost:3000");

        Dotenv dotenv = Dotenv.load();

        String username = dotenv.get("PLAYER");
        String password = dotenv.get("PLAYER_PASSWORD");

        LoginPage loginPage = new LoginPage(driver,wait);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    private final By joinButton =(By.xpath("//button[text()='Join']"));
    private final By buttonContainer = (By.cssSelector(".p-1"));
    
    public void clickJoinButton() {wait.until(ExpectedConditions.visibilityOfElementLocated(joinButton)).click();}

    public List<WebElement> getAllButtons() {
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonContainer));
        return container.findElements(By.tagName("button"));
    }
    
    public void clickRandomButton() {
        List<WebElement> buttons = getAllButtons();
        if (!buttons.isEmpty()) {
            Random random = new Random();
            WebElement randomButton = buttons.get(random.nextInt(buttons.size()));
            randomButton.click();
        }
    }
    
}

/*
Game logic:
game master play
game master create lobby
player join to the open game
player join again
game master starts the game
player give an answer
game master shows the result
game master click on the Next button
...player give an answer
...game master shows the result
...game master click on the Next button
 */

        




    

    

    

    