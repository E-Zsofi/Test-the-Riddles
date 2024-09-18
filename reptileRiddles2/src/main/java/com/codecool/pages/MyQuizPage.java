package com.codecool.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MyQuizPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By quizListLocator = By.cssSelector(".flex.flex-row.border-2.m-2.p-1.rounded-md");
    private By deleteButtonLocator = By.cssSelector(".bg-red-400");
    private By editButtonLocator = By.cssSelector(".bg-yellow-400");
    private By playButtonLocator = By.cssSelector(".bg-green-400");
    private By addQuizButtonLocator = By.cssSelector(".bg-green-400.w-32");

    public MyQuizPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public List<WebElement> getQuizzes() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(quizListLocator));
        return driver.findElements(quizListLocator);
    }

    public void clickDeleteButton(int index) {
        List<WebElement> quizzes = getQuizzes();
        if (index >= 0 && index < quizzes.size()) {
            WebElement quiz = quizzes.get(index);
            WebElement deleteButton = quiz.findElement(deleteButtonLocator);
            wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        } else {
            throw new IndexOutOfBoundsException("No quiz found at index " + index);
        }
    }

    public void clickEditButton(int index) {
        List<WebElement> quizzes = getQuizzes();
        if (index >= 0 && index < quizzes.size()) {
            WebElement quiz = quizzes.get(index);
            WebElement editButton = quiz.findElement(editButtonLocator);
            wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        } else {
            throw new IndexOutOfBoundsException("No quiz found at index " + index);
        }
    }

    public void clickPlayButton(int index) {
        List<WebElement> quizzes = getQuizzes();
        if (index >= 0 && index < quizzes.size()) {
            WebElement quiz = quizzes.get(index);
            WebElement playButton = quiz.findElement(playButtonLocator);
            wait.until(ExpectedConditions.elementToBeClickable(playButton)).click();
        } else {
            throw new IndexOutOfBoundsException("No quiz found at index " + index);
        }
    }

    public void clickAddQuizButton() {
        WebElement addQuizButton = wait.until(ExpectedConditions.elementToBeClickable(addQuizButtonLocator));
        addQuizButton.click();
    }
}
