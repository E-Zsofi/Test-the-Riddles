package com.codecool;

import com.codecool.pages.RegisterPage;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class RegisterPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private RegisterPage registerPage;
    private String username;
    private String email;
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
        registerPage = new RegisterPage(driver, wait);
        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();
        username = dotenv.get("PLAYER_USERNAME");
        email = dotenv.get("PLAYER_EMAIL");
        password = dotenv.get("PLAYER_PASSWORD");
        BASE_URL = dotenv.get("BASE_URL");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void register() {
        driver.get(BASE_URL+"/register");
        registerPage.enterUsername(username);
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.clickRegister();
        Assertions.assertTrue(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @Test
    public void registerWithSameName() {
        driver.get(BASE_URL+"/register");
        registerPage.enterUsername(username);
        registerPage.enterEmail("newuser@newuser.com");
        registerPage.enterPassword("newuserPassword");
        registerPage.clickRegister();
        Assertions.assertFalse(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @Test
    public void registerWithSameEmail() {
        driver.get(BASE_URL+"/register");
        registerPage.enterUsername("newUser");
        registerPage.enterEmail(email);
        registerPage.enterPassword("nUp");
        registerPage.clickRegister();
        Assertions.assertFalse(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @Test
    public void registerWithSamePassword() {
        driver.get(BASE_URL+"/register");
        registerPage.enterUsername("newUser");
        registerPage.enterEmail("newuser@newuser.com");
        registerPage.enterPassword(password);
        registerPage.clickRegister();
        Assertions.assertFalse(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/credentials.csv", numLinesToSkip = 1)
    public void testCSV(String username, String email, String password, boolean expected){
        driver.get(BASE_URL+"/register");
        registerPage.enterUsername(username);
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.clickRegister();
        String expectedURL = BASE_URL+"/login";
        boolean actual = driver.getCurrentUrl().contains(expectedURL);
        Assertions.assertEquals(expected, actual);
    }
}
