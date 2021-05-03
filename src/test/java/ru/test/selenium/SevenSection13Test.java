package ru.test.selenium;

import Methods.CommonMethods;
import Pages.BucketPageLitecart.BucketPageSteps;
import Pages.MainPageLitecart.MainPageSteps;
import Pages.ProductPageLitecart.ProductPageSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SevenSection13Test {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 30);
    }

    @Test
    public void RegistrationTest() throws InterruptedException {
        CommonMethods.goToLitecart(driver);

        MainPageSteps.checkBucketClear(driver);
        List<String> productsHrefs = MainPageSteps.getProductsHrefs(driver, wait);

        for (int i = 1; i < 4; i++) {
            MainPageSteps.goToProduct(driver, productsHrefs, i);
            ProductPageSteps.addProductToBucket(driver);
            ProductPageSteps.checkAddedProductInBucket(driver, wait, i);
            CommonMethods.goBackToMainPage(driver);
        }

        MainPageSteps.goToBucket(driver);
        BucketPageSteps.checkBucketNotEmpty(driver, wait);
        BucketPageSteps.removeAndCheckProductInBucket(driver, wait);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
