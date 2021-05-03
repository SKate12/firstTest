package Pages.BucketPageLitecart;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

public class BucketPageSteps extends BucketPage {

    public static void checkBucketNotEmpty(WebDriver driver, WebDriverWait wait) {
        wait.until(visibilityOfAllElements(listItems(driver)));
        wait.until(visibilityOfAllElements(listShortcut(driver)));
    }

    public static void removeAndCheckProductInBucket(WebDriver driver, WebDriverWait wait) {
        int size = listShortcut(driver).size();
        for (int i = 0; i < size - 1; i++) {
            shortCut(driver).click();
            shortCut(driver).click();
            shortCut(driver).click();

            int sizeBefore = listItems(driver).size();
            Assert.assertEquals(sizeBefore, listShortcut(driver).size());
            removeItem(driver, wait);
        }
        Assert.assertEquals(1, listItems(driver).size());
        removeItem(driver, wait);
    }

    private static void removeItem(WebDriver driver, WebDriverWait wait) {
        WebElement removeButton = removeButton(driver);
        WebElement item = item(driver);
        removeButton.click();
        wait.until(stalenessOf(removeButton));
        wait.until(stalenessOf(item));
    }
}
