package com.codecool.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavbarComponent {
    private By games = By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[1]/ul/li[1]/a/span");
    private By quizzes = By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[1]/ul/li[2]/a/span");
    private By myQuizzes = By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[1]/ul/li[3]/a/span");
    private By account = By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[1]/ul/li[4]/a/span");
    private By logout = By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[2]/a/button/span");
    private By login = By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[2]/a[1]/button/span");
    private By signup = By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div/div[2]/a[2]/button/span");
    private WebDriver driver;
    private WebDriverWait wait;

    public NavbarComponent(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickOnGames(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(games)).click();
    }

    public void clickOnQuizzes(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(quizzes)).click();
    }

    public void clickOnMyQuizzes(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(myQuizzes)).click();
    }

    public void clickOnAccount(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(account)).click();
    }

    public void clickOnLogout(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(logout)).click();
    }

    public void clickOnLogin(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(login)).click();
    }

    public void clickOnSignup(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(signup)).click();
    }
}
