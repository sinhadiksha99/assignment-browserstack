package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.nio.file.Files;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
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

    protected void downloadArticleImage(WebElement article, int articleNumber) {
        try {
            // Check if article contains image using //article//img
            List<WebElement> images = article.findElements(By.xpath(".//img"));
            
            if (!images.isEmpty()) {
                System.out.println("Found " + images.size() + " image(s) in article " + articleNumber);
                
                // Download the first image found
                WebElement firstImage = images.get(0);
                String imgSrc = firstImage.getAttribute("src");
                
                if (imgSrc != null && !imgSrc.isEmpty()) {
                    // Handle relative URLs
                    if (imgSrc.startsWith("//")) {
                        imgSrc = "https:" + imgSrc;
                    } else if (imgSrc.startsWith("/")) {
                        imgSrc = "https://elpais.com" + imgSrc;
                    }
                    
                    if (imgSrc.startsWith("http")) {
                        downloadImage(imgSrc, "article_" + articleNumber + "_image.jpg");
                        System.out.println("✓ Downloaded image for article " + articleNumber);
                    } else {
                        System.out.println("⚠ Invalid image URL for article " + articleNumber + ": " + imgSrc);
                    }
                } else {
                    System.out.println("⚠ No valid image source found for article " + articleNumber);
                }
            } else {
                System.out.println("⚠ No images found in article " + articleNumber);
            }
            
        } catch (Exception e) {
            System.out.println("Error checking/downloading image for article " + articleNumber + ": " + e.getMessage());
        }
    }
    
    public void downloadImage(String imageUrl, String fileName) {
        try {
            URL url = new URL(imageUrl);
            try (InputStream in = url.openStream()) {
                Files.copy(in, Paths.get("downloaded_images", fileName), 
                          StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image saved as: downloaded_images/" + fileName);
            }
        } catch (Exception e) {
            System.out.println("Failed to download image: " + e.getMessage());
        }
    }

    public void handleCookieConsent() {
        try {
            // Wait briefly for cookie popup to appear
            Thread.sleep(2000);
            
            // Check if cookie consent button is present and visible
            WebElement agreeButton = driver.findElement(By.id("didomi-notice-agree-button"));
            
            if (agreeButton.isDisplayed()) {
                agreeButton.click();
                System.out.println("✓ Clicked cookie consent agree button");
                Thread.sleep(1000); // Wait for popup to disappear
            }
            
        } catch (Exception e) {
            // Cookie popup not present or already dismissed
            System.out.println("Cookie consent popup not found or already handled");
        }
    }
    
}
