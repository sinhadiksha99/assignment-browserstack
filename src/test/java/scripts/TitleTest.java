package scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.commons.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TitleTest extends BaseTest{

    @Test
    void testOfWebsite() {
        driver.get("https://elpais.com/");
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        Assert.assertTrue(title.equals("EL PAÍS: el periódico global") ||
                title.contains("EL PAÍS: el periódico global"),
                "Page title should be 'EL PAÍS: el periódico global'");
        
        WebElement htmlElement = driver.findElement(By.tagName("html"));
        String lang = htmlElement.getAttribute("lang");
        Assert.assertTrue(lang != null && lang.startsWith("es"),
                "HTML lang attribute should indicate Spanish");
        
        System.out.println("✓ Successfully verified El País is displaying Spanish content");
        System.out.println("Language: " + lang);
    } 
    
}