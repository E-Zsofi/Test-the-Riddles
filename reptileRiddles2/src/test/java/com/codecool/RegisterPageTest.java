package com.codecool;

import com.codecool.pages.RegisterPage;
import com.codecool.utilitiy.DatabaseMod;
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
    private DatabaseMod databaseMod;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        registerPage = new RegisterPage(driver, wait);
        databaseMod = new DatabaseMod();
        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();
        username = dotenv.get("PLAYER_USERNAME");
        email = dotenv.get("PLAYER_EMAIL");
        password = dotenv.get("PLAYER_PASSWORD");
        BASE_URL = dotenv.get("BASE_URL");
        driver.get(BASE_URL+"/register");
    }

    @AfterEach
    void tearDown() {
        databaseMod.PostgresTruncateMultipleTables();
        driver.quit();
    }

    @Test
    public void registerTest() {
        registerPage.register(username, email, password);
        Assertions.assertTrue(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @Test
    public void registerWithSameNameTest() {
        registerPage.register(username, email, password);
        driver.get(BASE_URL+"/register");
        registerPage.register(username,"newuser@newuser.com","newuserPassword");
        Assertions.assertFalse(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @Test
    public void registerWithSameEmail() {
        registerPage.register(username, email, password);
        driver.get(BASE_URL+"/register");
        registerPage.register("newUser",email,"nUp");
        Assertions.assertFalse(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @Test
    public void registerWithSamePassword() {
        registerPage.register(username, email, password);
        driver.get(BASE_URL+"/register");
        registerPage.register("newUser","newuser@newuser.com",password );
        Assertions.assertFalse(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/credentials.csv", numLinesToSkip = 1)
    public void testCSV(String username, String email, String password, boolean expected){
        registerPage.register(username, email, password);
        String expectedURL = BASE_URL+"/login";
        boolean actual = driver.getCurrentUrl().contains(expectedURL);
        Assertions.assertEquals(expected, actual);
    }
}
