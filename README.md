# Quiz Game Testing Project

This repository contains the testing framework for a Kahoot-like game application where users can create quizzes, start games, join, and play them. The system under test (SUT) has its own repository, which you can find [here](https://github.com/CodecoolGlobal/reptile-riddles-2-general-E-Zsofi).

## Tech Stack

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- ![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white)
- ![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)

## Prerequisites

- Java JDK (>= 11)
- Maven
- ChromeDriver or other web driver for Selenium

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/E-Zsofi/Test-the-Riddles
   cd reptile-riddles-2-general-E-Zsofi
2. Install Maven dependencies:
```
mvn clean install
```
3. Set up the WebDriver:
Make sure you have ChromeDriver (or another Selenium-compatible driver) installed and available in your system's PATH.
Run the tests:
```
mvn test
```
This will execute all the Selenium tests using JUnit.

Test Structure:
The tests are located in the src/test directory.
Each test case covers a specific feature of the quiz game, including user interactions like creating quizzes, joining games, and playing quizzes.
