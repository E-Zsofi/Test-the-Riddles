package com.codecool;

import com.codecool.pages.LoginPage;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class LogInTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private String username;
    private String password;
    private String BASE_URL;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        loginPage = new LoginPage(driver, wait);
        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();
        username = dotenv.get("PLAYER_USERNAME");
        password = dotenv.get("PLAYER_PASSWORD");
        BASE_URL = dotenv.get("BASE_URL");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void login() {
        driver.get(BASE_URL+"/login");

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        Assertions.assertTrue(driver.getCurrentUrl().contains(BASE_URL));
    }

    @Test
    public void logout() {
        driver.get(BASE_URL+"/login");

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        loginPage.clickLogOut();

        Assertions.assertTrue(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }
}