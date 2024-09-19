package com.codecool.utilitiy;

import com.codecool.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DBPopulateQuiz {
    private QuizFormPage quizformpage;
    private QuizFormComponent quizformcomponent;
    private NavbarComponent navbarComponent;
    private AllQuizPage allquizpage;
    private WebDriver driver;
    private WebDriverWait wait;


    public DBPopulateQuiz(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.navbarComponent = new NavbarComponent(driver, wait);
        this.quizformcomponent = new QuizFormComponent(driver, wait);
        this.allquizpage = new AllQuizPage(driver, wait);
        this.quizformpage = new QuizFormPage(driver, wait);
    }

    public void populateQuiz(String quizTitle, String questionText, List<String> answers) {
        navbarComponent.clickOnQuizzes();
        allquizpage.clickAddQuizButton();
        quizformpage.setQuizTitle(quizTitle);
        quizformpage.clickAddQuestion();
        quizformcomponent.setQuestionText(questionText);
        for (int i = 0; i < answers.size(); i++) {
            quizformcomponent.setAnswerOption(i+1, answers.get(i));
        }
        quizformcomponent.markAnswerAsCorrect(2);
        quizformcomponent.clickSave();
        quizformcomponent.acceptAlert();
        quizformpage.clickSaveQuiz();
        quizformpage.acceptAlert();
    }
}
