package scripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.commons.BaseTest;
import pages.NewspaperPage;
import pages.OpinionPage;
import utils.Translate;

public class ArticleTest extends BaseTest {

    private static final List<String> existingTitles = List.of(
            "The United Kingdom and Europe: It is not enough to go throwing",
            "I welcome professional secrecy",
            "Candidates to be great men",
            "Corruption and its types",
            "‘Inventory of a perfect trip’");

    @Test
    void testScrapeOpinionArticles() throws InterruptedException {
        driver.get("https://elpais.com/");
        handleCookieConsent();

        NewspaperPage newspaperPage = new NewspaperPage(driver);
        wait.until(ExpectedConditions.visibilityOf(newspaperPage.opinion));
        wait.until(ExpectedConditions.elementToBeClickable(newspaperPage.opinion));
        newspaperPage.opinion.click();

        OpinionPage opinionPage = new OpinionPage(driver);
        wait.until(ExpectedConditions.urlContains("/opinion"));

        List<WebElement> articles = opinionPage.getArticles();
        int count = Math.min(5, articles.size());

        List<String> translatedEnglishTitles = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            WebElement article = articles.get(i);
            processArticle(article, i + 1, translatedEnglishTitles, opinionPage);
        }
        boolean allTitlesMatch = compareWithExistingTitles(existingTitles, translatedEnglishTitles);
        Assert.assertTrue(allTitlesMatch,
                "Some translated titles do not match the existing titles");
        printRepeatedWords(translatedEnglishTitles);
    }

    private void processArticle(WebElement article, int articleNumber, List<String> translatedTitles,
            OpinionPage opinionPage) {
        String title = opinionPage.getTitle(article);
        String imgUrl = opinionPage.getImageUrl(article);

        System.out.println("\nArticle " + articleNumber + ":");

        if (!title.isEmpty()) {
            System.out.println("Title: " + title);
            try {
                String englishTitle = Translate.translateSpanishToEnglish(title);
                System.out.println("English Title: " + englishTitle);
                translatedTitles.add(englishTitle);
            } catch (Exception e) {
                System.out.println("English Title: Translation failed: " + e.getMessage());
                translatedTitles.add("");
            }
        } else {
            System.out.println("Title: <Empty>");
            translatedTitles.add("");
        }

        System.out.println("Image URL: " + (imgUrl.isEmpty() ? "Not available" : imgUrl));

        if (!imgUrl.isEmpty()) {
            downloadArticleImage(article, articleNumber);
        }
    }

    /**
     * Prints words repeated more than twice across all translated titles combined.
     * Words are normalized to lowercase and stripped of punctuation.
     */
    private void printRepeatedWords(List<String> titles) {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String title : titles) {
            if (title == null || title.isEmpty()) continue;

            String[] words = title.toLowerCase().split("\\W+");

            for (String word : words) {
                if (word.isEmpty()) continue;
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        boolean foundRepeated = false;
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
                foundRepeated = true;
            }
        }
        if (!foundRepeated) {
            System.out.println("No words repeated more than twice.");
        }
    }

}
