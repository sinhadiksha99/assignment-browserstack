package com.example.scripts;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.example.utils.BaseTest;
import com.example.utils.Translate;

import pages.NewspaperPage;
import pages.OpinionPage;

public class ArticleTest extends BaseTest {

    @Test
    void testScrapeOpinionArticles() throws InterruptedException {
        driver.get("https://elpais.com/");
        handleCookieConsent();
        Thread.sleep(3000);

        NewspaperPage newspaperPage = new NewspaperPage(driver);
        wait.until(ExpectedConditions.visibilityOf(newspaperPage.opinion));
        wait.until(ExpectedConditions.elementToBeClickable(newspaperPage.opinion));
        newspaperPage.opinion.click();

        OpinionPage opinionPage = new OpinionPage(driver);
        wait.until(ExpectedConditions.urlContains("/opinion"));

        List<WebElement> articles = opinionPage.getArticles();
        int count = Math.min(5, articles.size());

        for (int i = 0; i < count; i++) {
            WebElement article = articles.get(i);
            String title = opinionPage.getTitle(article);
            String imgUrl = opinionPage.getImageUrl(article);

            System.out.println("\nArticle " + (i + 1) + ":");

            if (!title.isEmpty()) {
                System.out.println("Title: " + title);
                try {
                    String englishTitle = Translate.translateSpanishToEnglish(title);
                    System.out.println("English Title: " + englishTitle);
                } catch (Exception e) {
                    System.out.println("English Title: Translation failed: " + e.getMessage());
                }
            } else {
                System.out.println("Title: <Empty>");
            }

            System.out.println("Image URL: " + (imgUrl.isEmpty() ? "Not available" : imgUrl));
        }
    }
}
