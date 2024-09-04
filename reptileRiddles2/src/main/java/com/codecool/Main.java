package com.codecool;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Main {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\csacs\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-search-engine-choice-screen");
        WebDriver driver = new ChromeDriver(options);

        driver.get("http://localhost:3000/");

        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .load();

        String username = dotenv.get("USER1NAME");
        String password = dotenv.get("PASSWORD");
        String email = dotenv.get("EMAIL");

        SignUp.Signup(driver, username, password, email);

        LogIn log = new LogIn();
        log.logIn(driver, username,password);
    }


}