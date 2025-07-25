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

    public List<WebElement> getArticles() {
        return articles;
    }

    public String getTitle(WebElement article) {
        try {
            return article.findElement(By.xpath(".//header/h2/a")).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getUrl(WebElement article) {
        try {
            return article.findElement(By.xpath(".//header/h2/a")).getAttribute("href");
        } catch (Exception e) {
            return "";
        }
    }

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
            return "";
        }
    }
}
