package com.codecool.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MyQuizPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By quizListLocator = By.cssSelector(".flex.flex-row.border-2.m-2.p-1.rounded-md");
    private By deleteButtonLocator = By.xpath("//button[contains(text(), 'Delete')]");
    private By editButtonLocator = By.xpath("//button[contains(text(), 'Edit')]");
    private By playButtonLocator = By.xpath("//button[contains(text(), 'Play')]");
    private By addQuizButtonLocator = By.xpath("//button[contains(text(), 'Add Quiz')]");

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
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }


    public int getQuizzesNumber() {
        int numberOfQuizzes = 0;
        try {
            List<WebElement> quizzes = getQuizzes();
            numberOfQuizzes = quizzes.size();
        } catch (TimeoutException e) {
            System.out.println("Timeout occurred while waiting for quiz elements: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("No quiz elements found on the page: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        return numberOfQuizzes;
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
