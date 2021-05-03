package Pages.ProductPageLitecart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage {
    public static List<WebElement> productOption(WebDriver driver) {
        return driver.findElements(By.name("options[Size]"));
    }

    public static WebElement optionSmall(WebDriver driver) {
        return driver.findElement(By.xpath("//*[@value='Small']"));
    }

    public static WebElement addToCardButton(WebDriver driver) {
        return driver.findElement(By.name("add_cart_product"));
    }
}
