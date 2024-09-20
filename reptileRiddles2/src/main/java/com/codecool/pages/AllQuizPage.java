package com.codecool.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AllQuizPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By quizListLocator = By.cssSelector(".flex.flex-row.border-2.m-2.p-1.rounded-md");
    private By copyButtonLocator = By.cssSelector(".bg-yellow-400");
    private By playButtonLocator = By.cssSelector(".bg-green-400");
    private By addQuizButtonLocator = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/button");

    public AllQuizPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public List<WebElement> getQuizzes() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(quizListLocator));
        return driver.findElements(quizListLocator);
    }

    public void clickCopyButton(int index) {
        List<WebElement> quizzes = getQuizzes();
        if (index >= 0 && index < quizzes.size()) {
            WebElement quiz = quizzes.get(index);
            WebElement copyButton = wait.until(ExpectedConditions.elementToBeClickable(quiz.findElement(copyButtonLocator)));
            copyButton.click();
        } else {
            throw new IndexOutOfBoundsException("No quiz found at index " + index);
        }
    }

    public void clickPlayButton(int index) {
        List<WebElement> quizzes = getQuizzes();
        if (index >= 0 && index < quizzes.size()) {
            WebElement quiz = quizzes.get(index);
            WebElement playButton = wait.until(ExpectedConditions.elementToBeClickable(quiz.findElement(playButtonLocator)));
            playButton.click();
        } else {
            throw new IndexOutOfBoundsException("No quiz found at index " + index);
        }
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

    public void clickAddQuizButton() {
        WebElement addQuizButton = wait.until(ExpectedConditions.elementToBeClickable(addQuizButtonLocator));
        addQuizButton.click();
    }
}
