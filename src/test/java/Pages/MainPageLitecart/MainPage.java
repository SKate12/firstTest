package Pages.MainPageLitecart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage {

    public static WebElement bucketQuantity(WebDriver driver) {
        return driver.findElement(By.xpath("//span[@class ='quantity']"));
    }

    public static List<WebElement> listProduct(WebDriver driver) {
        return driver.findElements(By.xpath("//li[contains(@class,'product')]"));
    }

    public static WebElement productHref(WebDriver driver, String href) {
        return driver.findElement(By.xpath("//*[@href='" + href + "']"));
    }
}
