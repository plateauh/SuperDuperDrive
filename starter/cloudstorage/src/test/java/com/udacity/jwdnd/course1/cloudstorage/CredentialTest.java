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

public class CredentialTest {

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

    // Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
    @Test
    public void createCredentials() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        navigateToCredentialsTab(wait);

        wait.until(webDriver -> webDriver.findElement(By.id("add-credential")));
        WebElement addCredentialsBtn = driver.findElement(By.id("add-credential"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        addCredentialsBtn.click();
        fillCredentialsForm(wait, "x.com", "user", "123");
        navigateToCredentialsTab(wait);

        wait.until(webDriver -> webDriver.findElement(By.id("cr-url")));
        WebElement urlHead = driver.findElement(By.id("cr-url"));
        WebElement passwordHead = driver.findElement(By.id("cr-password"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(urlHead.getText().equals("x.com") && !passwordHead.getText().equals("123"));
        deleteCredentials(wait);
        navigateToCredentialsTab(wait);
    }

    // Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
    @Test
    public void editCredentials() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        navigateToCredentialsTab(wait);

        wait.until(webDriver -> webDriver.findElement(By.id("add-credential")));
        WebElement addCredentialsBtn = driver.findElement(By.id("add-credential"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        addCredentialsBtn.click();
        fillCredentialsForm(wait, "old-url.com", "old_user", "123");
        navigateToCredentialsTab(wait);

        wait.until(webDriver -> webDriver.findElement(By.id("editCredentialBtn")));
        WebElement editCredentialsBtn = driver.findElement(By.id("editCredentialBtn"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        editCredentialsBtn.click();

        wait.until(webDriver -> webDriver.findElement(By.id("credential-password")));
        WebElement passwordInput = driver.findElement(By.id("credential-password"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean isPasswordUnencrypted = passwordInput.getAttribute("value").equals("123");

        fillCredentialsForm(wait, "new-url.com", "new_user", "456");
        navigateToCredentialsTab(wait);

        wait.until(webDriver -> webDriver.findElement(By.id("cr-url")));
        WebElement urlHead = driver.findElement(By.id("cr-url"));
        WebElement userHead = driver.findElement(By.id("cr-user"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(isPasswordUnencrypted
                && urlHead.getText().equals("new-url.com")
                && userHead.getText().equals("new_user"));
        deleteCredentials(wait);
        navigateToCredentialsTab(wait);
    }

    // Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
    @Test
    public void deleteCredentials() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        navigateToCredentialsTab(wait);

        wait.until(webDriver -> webDriver.findElement(By.id("add-credential")));
        WebElement addCredentialsBtn = driver.findElement(By.id("add-credential"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        addCredentialsBtn.click();
        fillCredentialsForm(wait, "url.com", "user", "123");
        navigateToCredentialsTab(wait);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        deleteCredentials(wait);

        Assertions.assertFalse(driver.getPageSource().contains("url.com"));
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

    private void navigateToCredentialsTab(WebDriverWait wait) {
        wait.until(webDriver -> webDriver.findElement(By.id("nav-credentials-tab")));
        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();
    }

    private void deleteCredentials(WebDriverWait wait) {
        wait.until(webDriver -> webDriver.findElement(By.id("deleteCredentialBtn")));
        WebElement deleteNoteBtn = driver.findElement(By.id("deleteCredentialBtn"));
        deleteNoteBtn.click();

        WebElement homeLink = driver.findElement(By.id("home-link"));
        homeLink.click();

        navigateToCredentialsTab(wait);
    }

    private void fillCredentialsForm(WebDriverWait wait, String url, String username, String password) {
        wait.until(webDriver -> webDriver.findElement(By.id("credential-url")));
        WebElement urlInput = driver.findElement(By.id("credential-url"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        urlInput.clear();
        urlInput.sendKeys(url);

        WebElement usernameInput = driver.findElement(By.id("credential-username"));
        usernameInput.clear();
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id("credential-password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);

        WebElement submit = driver.findElement(By.id("save-credentials"));
        submit.click();

        WebElement homeLink = driver.findElement(By.id("home-link"));
        homeLink.click();
    }

}
