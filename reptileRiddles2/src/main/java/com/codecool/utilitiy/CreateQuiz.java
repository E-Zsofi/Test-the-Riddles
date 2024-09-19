package com.codecool.utilitiy;

import com.codecool.pages.LobbyPage;
import com.codecool.pages.MyQuizPage;
import com.codecool.pages.QuizFormComponent;
import com.codecool.pages.QuizFormPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.format.TextStyle;

public class CreateQuiz {

    public void createQuiz(WebDriver driver, WebDriverWait wait){
        MyQuizPage myQuizPage = new MyQuizPage(driver, wait);
        driver.navigate().to("http://localhost:3000/quiz/my");
        myQuizPage.clickAddQuizButton();
        QuizFormPage quizFormPage = new QuizFormPage(driver, wait);
        quizFormPage.setQuizTitle("TEST QUIZ");
        quizFormPage.clickAddQuestion();
        QuizFormComponent quizFormComponent = new QuizFormComponent(driver, wait);
        quizFormComponent.setQuestionText("question1");
        quizFormComponent.setTimeLimit(25);
        quizFormComponent.setAnswerOption(1, "answer1");
        quizFormComponent.setAnswerOption(2, "answer2");
        quizFormComponent.markAnswerAsCorrect(1);
        quizFormComponent.clickSave();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        quizFormPage.clickSaveQuiz();
        alert.accept();
    }
    
    public void createGame(WebDriver driver, WebDriverWait wait, int quizNumber){
        MyQuizPage myQuizPage = new MyQuizPage(driver, wait);
        myQuizPage.clickPlayButton(quizNumber);
        LobbyPage lobbyPage = new LobbyPage(driver, wait);
        lobbyPage.clickCreateLobby();
        
        
    }
}
