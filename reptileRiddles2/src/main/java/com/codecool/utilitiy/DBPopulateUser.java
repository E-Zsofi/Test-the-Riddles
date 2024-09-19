package com.codecool.utilitiy;

import com.codecool.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DBPopulateUser {

    private RegisterPage registerpage;
    private LoginPage loginpage;
    private NavbarComponent navbarComponent;
    private WebDriver driver;
    private WebDriverWait wait;
    private String email;
    private String username;
    private String password;

    public DBPopulateUser(WebDriver driver, WebDriverWait wait, String username, String email, String password) {
        this.driver = driver;
        this.wait = wait;
        this.email = email;
        this.username = username;
        this.password = password;
        this.registerpage = new RegisterPage(driver, wait);
        this.loginpage = new LoginPage(driver, wait);
        this.navbarComponent = new NavbarComponent(driver, wait);
    }

    public void populateUser(){
        navbarComponent.clickOnSignup();
        registerpage.enterUsername(username);
        registerpage.enterPassword(password);
        registerpage.enterEmail(email);
        registerpage.clickRegister();
        loginpage.enterUsername(username);
        loginpage.enterPassword(password);
        loginpage.clickLogin();

    }

}
