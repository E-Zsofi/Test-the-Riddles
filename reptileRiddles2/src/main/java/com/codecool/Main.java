package com.codecool;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LocalLogs;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:3000");

        Dotenv dotenv = Dotenv.load();

        String username = dotenv.get("USER_ID");
        String password = dotenv.get("PASSWORD");
        
        LogIn log = new LogIn();
        log.logIn(driver, username,password);
    }
}