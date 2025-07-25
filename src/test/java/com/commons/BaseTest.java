package com.commons;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.nio.file.Files;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest extends GenericFunctions {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    protected void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        createImageDirectory();
    }

    @AfterMethod
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

     protected void createImageDirectory() {
        try {
            Files.createDirectories(Paths.get("downloaded_images"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleCookieConsent() {
        try {
            // Wait briefly for cookie popup to appear
            Thread.sleep(5000);
            
            // Check if cookie consent button is present and visible
            WebElement agreeButton = driver.findElement(By.id("didomi-notice-agree-button"));
            
            if (agreeButton.isDisplayed()) {
                agreeButton.click();
                System.out.println("✓ Clicked cookie consent agree button");
                Thread.sleep(3000); // Wait for popup to disappear
            }
            
        } catch (Exception e) {
            // Cookie popup not present or already dismissed
            System.out.println("Cookie consent popup not found or already handled");
        }
    }
    
}
