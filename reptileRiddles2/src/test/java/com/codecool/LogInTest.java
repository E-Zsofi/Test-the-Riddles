package com.codecool;

import com.codecool.pages.LoginPage;
import com.codecool.pages.NavbarComponent;
import com.codecool.utilitiy.DBPopulateUser;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class LogInTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private String username;
    private String password;
    private String email;
    private String BASE_URL;
    private DBPopulateUser dbPopulateUser;
    private NavbarComponent navbarComponent;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();
        username = dotenv.get("PLAYER");
        password = dotenv.get("PLAYER_PASSWORD");
        email = dotenv.get("PLAYER_EMAIL");
        BASE_URL = dotenv.get("BASE_URL");
        dbPopulateUser = new DBPopulateUser(driver, wait, username, email, password);
        navbarComponent = new NavbarComponent(driver, wait);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void login() {
        driver.get(BASE_URL+"/login");
        dbPopulateUser.populateUser();
        Assertions.assertTrue(driver.getCurrentUrl().contains(BASE_URL));
    }

    @Test
    public void logout() {
        driver.get(BASE_URL+"/login");
        dbPopulateUser.populateUser();
        navbarComponent.clickOnLogout();
        Assertions.assertTrue(driver.getCurrentUrl().contains(BASE_URL+"/login"));
    }

    @Test
    void stayLoggedInUnlessUserLogsOutTest() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get(BASE_URL);
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[2]/a/button")).isDisplayed());
    }
}