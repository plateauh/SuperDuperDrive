package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersTest {
    
    @LocalServerPort
    private int port;
    private WebDriver driver;
    private static final int timeoutInSec = 2;

    @BeforeAll
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void createDriver() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void quitDriver() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /*
    Write a test that verifies that an unauthorized user can only access the login and signup pages.
     */
    @Test
    public void testUnauthorizedUser() {
        driver.get("http://localhost:" + this.port + "/login");
        boolean loginAccessibility = driver.getTitle().equals("Login");

        driver.get("http://localhost:" + this.port + "/signup");
        boolean signupAccessibility = driver.getTitle().equals("Sign Up");

        driver.get("http://localhost:" + this.port + "/home");
        boolean homeInaccessibility = driver.getTitle().equals("Login");
        Assertions.assertTrue(loginAccessibility && signupAccessibility && homeInaccessibility);
    }

    /*
    Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
     */
    @Test
    public void testSignupUser() {
        signup();
        boolean signUpSuccess = driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!");

        login();
        boolean loginSuccess = driver.getTitle().equals("Home");

        logout();
        driver.get("http://localhost:" + this.port + "/home");
        boolean logoutSuccess = driver.getTitle().equals("Login");
        Assertions.assertTrue(signUpSuccess && loginSuccess && logoutSuccess);
    }

    private void logout() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.until(webDriver -> webDriver.findElement(By.id("logout")));
        WebElement logoutButton = driver.findElement(By.id("logout"));
        logoutButton.click();
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


}
