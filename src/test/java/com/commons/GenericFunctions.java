package com.commons;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GenericFunctions {

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

}
