package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTest {

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private static final int timeoutInSec = 2;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        signup();
        login();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    // 1- Write a test that creates a note, and verifies it is displayed.
    @Test
    public void createNote() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));

        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        wait.until(webDriver -> webDriver.findElement(By.id("addNoteBtn")));
        WebElement addNoteBtn = driver.findElement(By.id("addNoteBtn"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        addNoteBtn.click();

        wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
        WebElement noteTitleInput = driver.findElement(By.id("note-title"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        noteTitleInput.sendKeys("note test");

        wait.until(webDriver -> webDriver.findElement(By.id("note-description")));
        WebElement noteTitleDescription = driver.findElement(By.id("note-description"));
        noteTitleDescription.sendKeys("note test description");

        wait.until(webDriver -> webDriver.findElement(By.id("submit-note")));
        WebElement submitNoteBtn = driver.findElement(By.id("submit-note"));
        submitNoteBtn.click();

        wait.until(webDriver -> webDriver.findElement(By.id("home-link")));
        WebElement homeLink = driver.findElement(By.id("home-link"));
        homeLink.click();
        noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        wait.until(webDriver -> webDriver.findElement(By.id("noteTitleHead")));
        WebElement noteTitleHead = driver.findElement(By.id("noteTitleHead"));
        WebElement noteDescRow = driver.findElement(By.id("noteDescRow"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("note test", noteTitleHead.getText());
        Assertions.assertEquals("note test description", noteDescRow.getText());

        wait.until(webDriver -> webDriver.findElement(By.id("deleteNoteBtn")));
        WebElement deleteNoteBtn = driver.findElement(By.id("deleteNoteBtn"));
        deleteNoteBtn.click();
        homeLink = driver.findElement(By.id("home-link"));
        homeLink.click();

        wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));
        noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();
    }

    // 2- Write a test that edits an existing note and verifies that the changes are displayed.
    @Test
    public void editNote() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));

        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        wait.until(webDriver -> webDriver.findElement(By.id("addNoteBtn")));
        WebElement addNoteBtn = driver.findElement(By.id("addNoteBtn"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        addNoteBtn.click();

        createNoteAndNavigate(wait, "old title", "old description");

        wait.until(webDriver -> webDriver.findElement(By.id("editNoteBtn")));
        WebElement editNoteBtn = driver.findElement(By.id("editNoteBtn"));
        editNoteBtn.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        createNoteAndNavigate(wait, "new title", "new description");

        wait.until(webDriver -> webDriver.findElement(By.id("noteTitleHead")));
        WebElement noteTitleHead = driver.findElement(By.id("noteTitleHead"));
        WebElement noteDescRow = driver.findElement(By.id("noteDescRow"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals("new title", noteTitleHead.getText());
        Assertions.assertEquals("new description", noteDescRow.getText());

        wait.until(webDriver -> webDriver.findElement(By.id("deleteNoteBtn")));
        WebElement deleteNoteBtn = driver.findElement(By.id("deleteNoteBtn"));
        deleteNoteBtn.click();
        WebElement homeLink = driver.findElement(By.id("home-link"));
        homeLink.click();

        wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));
        noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();
    }



    // 3- Write a test that deletes a note and verifies that the note is no longer displayed.
    @Test
    public void deleteNote() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));

        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        wait.until(webDriver -> webDriver.findElement(By.id("addNoteBtn")));
        WebElement addNoteBtn = driver.findElement(By.id("addNoteBtn"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        addNoteBtn.click();

        createNoteAndNavigate(wait, "note title", "description");

        wait.until(webDriver -> webDriver.findElement(By.id("deleteNoteBtn")));
        WebElement deleteNoteBtn = driver.findElement(By.id("deleteNoteBtn"));

        deleteNoteBtn.click();

        WebElement homeLink = driver.findElement(By.id("home-link"));
        homeLink.click();

        wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));
        noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        Assertions.assertFalse(driver.getPageSource().contains("note title"));
    }

    private void login() {
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);

        // username
        wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys("nalseghair");

        // password
        wait.until(webDriver -> webDriver.findElement(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys("12344321");

        // submit
        wait.until(webDriver -> webDriver.findElement(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
    }

    private void signup() {
        driver.get("http://localhost:" + this.port + "/signup");
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);

        // first name
        wait.until(webDriver -> webDriver.findElement(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys("Najed");

        // last name
        wait.until(webDriver -> webDriver.findElement(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys("Alseghair");

        // username
        wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys("nalseghair");

        // password
        wait.until(webDriver -> webDriver.findElement(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys("12344321");

        // submit
        wait.until(webDriver -> webDriver.findElement(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();
    }

    private void createNoteAndNavigate(WebDriverWait wait, String title, String description) {
        wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
        WebElement noteTitleInput = driver.findElement(By.id("note-title"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        noteTitleInput.clear();
        noteTitleInput.sendKeys(title);

        wait.until(webDriver -> webDriver.findElement(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.clear();
        noteDescription.sendKeys(description);

        wait.until(webDriver -> webDriver.findElement(By.id("submit-note")));
        WebElement submitNoteBtn = driver.findElement(By.id("submit-note"));
        submitNoteBtn.click();

        wait.until(webDriver -> webDriver.findElement(By.id("home-link")));
        WebElement homeLink = driver.findElement(By.id("home-link"));
        homeLink.click();
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
