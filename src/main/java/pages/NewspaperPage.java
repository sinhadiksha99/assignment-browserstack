package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewspaperPage {
    WebDriver driver;

    @FindBy(linkText = "Opinión")
    public WebElement opinion;

    @FindBy(linkText = "Internacional")
    public WebElement international;

    @FindBy(linkText = "Economía")
    public WebElement economic;

    public NewspaperPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
