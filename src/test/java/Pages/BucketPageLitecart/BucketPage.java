package Pages.BucketPageLitecart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BucketPage {

    public static WebElement shortCut(WebDriver driver) {
        return driver.findElement(By.xpath("//li[@class='shortcut']"));
    }

    public static List<WebElement> listShortcut(WebDriver driver) {
        return driver.findElements(By.xpath("//li[@class='shortcut']"));
    }

    public static List<WebElement> listItems(WebDriver driver) {
        return driver.findElements(By.xpath("//tr/td[@class='item']"));
    }

    public static WebElement removeButton(WebDriver driver) {
        return driver.findElement(By.xpath("//button[text()='Remove']"));
    }

    public static WebElement item(WebDriver driver) {
        return driver.findElement(By.xpath("//tr/td[@class='item']"));
    }
}
