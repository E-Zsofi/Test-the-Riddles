package com.codecool.utilitiy;

import com.codecool.pages.GameListPage;
import com.codecool.pages.LobbyPage;
import com.codecool.pages.MyQuizPage;
import com.codecool.pages.NavbarComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateGame {
    
    public static void createGame(WebDriver driver, WebDriverWait wait, int quizNumber){
        NavbarComponent navbarComponent = new NavbarComponent(driver,wait);
        navbarComponent.clickOnMyQuizzes();
        MyQuizPage myQuizPage = new MyQuizPage(driver, wait);
        myQuizPage.clickPlayButton(quizNumber);
        LobbyPage lobbyPage = new LobbyPage(driver, wait);
        lobbyPage.clickCreateLobby();
    }
    
}
