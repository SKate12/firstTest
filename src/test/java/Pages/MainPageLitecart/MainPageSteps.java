package Pages.MainPageLitecart;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

public class MainPageSteps extends MainPage {

    public static void checkBucketClear(WebDriver driver) {
        Assert.assertEquals("0", bucketQuantity(driver).getText());
    }

    public static List<String> getProductsHrefs(WebDriver driver, WebDriverWait wait) {
        List<String> products = new ArrayList<>();
        wait.until(visibilityOfAllElements(listProduct(driver)));
        for (WebElement element : listProduct(driver)) {
            products.add(element.findElement(By.xpath("a")).getAttribute("href"));
        }
        return products;
    }

    public static void goToProduct(WebDriver driver, List<String> products, int i) throws InterruptedException {
        productHref(driver, products.get(i)).click();
        sleep(2000);
    }

    public static void goToBucket(WebDriver driver) {
        driver.findElement(By.xpath("//a[text()='Checkout »']")).click();
    }
}
