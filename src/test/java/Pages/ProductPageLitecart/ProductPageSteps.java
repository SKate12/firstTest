package Pages.ProductPageLitecart;

import Pages.MainPageLitecart.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class ProductPageSteps extends ProductPage {

    public static void addProductToBucket(WebDriver driver) {
        if (productOption(driver).size() > 0) {
            productOption(driver).get(0).click();
            optionSmall(driver).click();
        }
        addToCardButton(driver).click();
    }

    public static void checkAddedProductInBucket(WebDriver driver, WebDriverWait wait, int i) {
        wait.until(textToBePresentInElement(MainPage.bucketQuantity(driver), String.valueOf(i)));
    }
}
