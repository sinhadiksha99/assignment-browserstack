package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpinionPage {

    WebDriver driver;

    @FindBy(xpath = "//article")
    private List<WebElement> articles;

    public OpinionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Returns the list of article elements on the opinion page.
     */
    public List<WebElement> getArticles() {
        return articles;
    }

    /**
     * Extracts the title text from a given article element.
     * @param article The WebElement representing a single article.
     * @return The title text if found, otherwise an empty string.
     */
    public String getTitle(WebElement article) {
        try {
            return article.findElement(By.xpath(".//header/h2/a")).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Extracts the URL (href attribute) from a given article element's title link.
     * @param article The WebElement representing a single article.
     * @return The URL string if found, otherwise an empty string.
     */
    public String getUrl(WebElement article) {
        try {
            return article.findElement(By.xpath(".//header/h2/a")).getAttribute("href");
        } catch (Exception e) {
            return "Url not found";
        }
    }

    /**
     * Gets the first image WebElement inside the given article element.
     * @param article The WebElement representing a single article.
     * @return The image WebElement if found, otherwise null.
     */
    public WebElement getImageElement(WebElement article) {
        try {
            return article.findElement(By.xpath(".//img"));
        } catch (Exception e) {
            return null;
        }
    }

    public String getImageUrl(WebElement article) {
        try {
            WebElement img = getImageElement(article);
            return img != null ? img.getAttribute("src") : "";
        } catch (Exception e) {
            return "no image";
        }
    }
}
