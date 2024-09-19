package com.codecool.utilitiy;

import com.codecool.pages.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DBPopulateWithQuiz {

    private RegisterPage registerpage;
    private LoginPage loginpage;
    private QuizFormPage quizformpage;
    private QuizFormComponent quizformcomponent;
    private NavbarComponent navbarComponent;
    private AllQuizPage allquizpage;
    private WebDriver driver;
    private WebDriverWait wait;
    private String email;
    private String username;
    private String password;

    public DBPopulateWithQuiz(WebDriver driver, WebDriverWait wait, String username, String email, String password) {
        this.driver = driver;
        this.wait = wait;
        this.email = email;
        this.username = username;
        this.password = password;
        this.registerpage = new RegisterPage(driver, wait);
        this.loginpage = new LoginPage(driver, wait);
        this.quizformpage = new QuizFormPage(driver, wait);
        this.quizformcomponent = new QuizFormComponent(driver, wait);
        this.navbarComponent = new NavbarComponent(driver, wait);
        this.allquizpage = new AllQuizPage(driver, wait);
    }

    public void populate(){
        navbarComponent.clickOnSignup();
        registerpage.enterUsername(username);
        registerpage.enterPassword(password);
        registerpage.enterEmail(email);
        registerpage.clickRegister();
        loginpage.enterUsername(username);
        loginpage.enterPassword(password);
        loginpage.clickLogin();
        navbarComponent.clickOnQuizzes();
        allquizpage.clickAddQuizButton();
        quizformpage.setQuizTitle("Color");
        quizformpage.clickAddQuestion();
        quizformcomponent.setQuestionText("Test question green?");
        quizformcomponent.setAnswerOption(1, "blue");
        quizformcomponent.setAnswerOption(2, "green");
        quizformcomponent.markAnswerAsCorrect(2);
        quizformcomponent.clickSave();
        quizformcomponent.acceptAlert();
        quizformpage.clickSaveQuiz();
        quizformpage.acceptAlert();
    }

}
