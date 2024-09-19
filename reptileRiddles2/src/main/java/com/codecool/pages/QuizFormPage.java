package com.codecool.pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QuizFormPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By addQuestionButtonLocator = By.cssSelector(".bg-green-800.hover\\:bg-green-700");
    private By quizTitleInputLocator = By.id("name");
    private By saveQuizButtonLocator = By.cssSelector(".bg-green-800.hover\\:bg-green-700.w-40");
    private By deleteQuizButtonLocator = By.cssSelector(".bg-zinc-950.border-2");

    public QuizFormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickAddQuestion() {
        WebElement addQuestionButton = wait.until(ExpectedConditions.elementToBeClickable(addQuestionButtonLocator));
        addQuestionButton.click();
    }

    public void setQuizTitle(String title) {
        WebElement quizTitleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(quizTitleInputLocator));
        quizTitleInput.clear();
        quizTitleInput.sendKeys(title);
    }

    public void clickSaveQuiz() {
        WebElement saveQuizButton = wait.until(ExpectedConditions.elementToBeClickable(saveQuizButtonLocator));
        saveQuizButton.click();
    }

    public void clickDeleteQuiz() {
        WebElement deleteQuizButton = wait.until(ExpectedConditions.elementToBeClickable(deleteQuizButtonLocator));
        deleteQuizButton.click();
    }
}

