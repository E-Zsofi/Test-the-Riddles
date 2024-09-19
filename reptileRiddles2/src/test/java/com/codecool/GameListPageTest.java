package com.codecool;

import com.codecool.pages.GameListPage;
import com.codecool.pages.NavbarComponent;
import com.codecool.utilitiy.DBPopulateQuiz;
import com.codecool.utilitiy.DBPopulateUser;
import com.codecool.utilitiy.DatabaseMod;
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
import java.util.ArrayList;
import java.util.List;

class GameListPageTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private DBPopulateUser dbPopulateUser;
    private DBPopulateQuiz dbPopulateQuiz;
    private NavbarComponent navbarComponent;
    private GameListPage gameListPage;
    private String username;
    private String password;
    private String email;
    private DatabaseMod databaseMod;
    private String BASE_URL;

    @BeforeEach
    public void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        databaseMod = new DatabaseMod();
        databaseMod.PostgresTruncateMultipleTables();
        navbarComponent = new NavbarComponent(driver, wait);
        gameListPage = new GameListPage(driver, wait);

        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();
        username = dotenv.get("PLAYER_USERNAME");
        password = dotenv.get("PLAYER_PASSWORD");
        email = dotenv.get("PLAYER_EMAIL");
        BASE_URL = dotenv.get("BASE_URL");
        driver.get(BASE_URL);

        dbPopulateUser = new DBPopulateUser(driver, wait, username, email, password);
        dbPopulateUser.populateUser();
        List<String> answers = new ArrayList<>();
        answers.add("Blue");
        answers.add("Green");
        dbPopulateQuiz = new DBPopulateQuiz(driver, wait);
        dbPopulateQuiz.populateQuiz("Color", "Test question, green?", answers);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        databaseMod.PostgresTruncateMultipleTables();
    }

    @Test
    void showGameListWithoutPlayableGames() {
        navbarComponent.clickOnGames();
        boolean isThereGame = gameListPage.isThereAnyPlayableGames();
        Assertions.assertFalse(isThereGame);
    }
}