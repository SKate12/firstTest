package ru.test.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

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
        driver.get("http://127.0.0.1/litecart");
        Assert.assertEquals("0", driver.findElement(By.xpath("//span[@class ='quantity']")).getText());
        List<String> products = new ArrayList<>();
        wait.until(visibilityOfAllElements(driver.findElements(By.xpath("//li[contains(@class,'product')]"))));
        for (WebElement element : driver.findElements(By.xpath("//li[contains(@class,'product')]"))) {
            products.add(element.findElement(By.xpath("a")).getAttribute("href"));
        }

        for (int i = 1; i < 4; i++) {
            driver.findElement(By.xpath("//*[@href='" + products.get(i) + "']")).click();
            sleep(2000);
            if (driver.findElements(By.name("options[Size]")).size() > 0) {
                driver.findElement(By.name("options[Size]")).click();
                driver.findElement(By.xpath("//*[@value='Small']")).click();
            }
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(textToBePresentInElement(driver.findElement(By.xpath("//span[@class ='quantity']")), String.valueOf(i)));
            driver.findElement(By.xpath("//a[@href='http://127.0.0.1/litecart/en/']")).click();
            sleep(2000);
        }

        driver.findElement(By.xpath("//a[text()='Checkout »']")).click();
        wait.until(visibilityOfAllElements(driver.findElements(By.xpath("//tr/td[text()='1']"))));

        for (int i = 0; i < 3; i++) {
            List<WebElement> quantityListBefore = driver.findElements(By.xpath("//tr/td[text()='1']"));
            sleep(2000);
            driver.findElement(By.xpath("//button[text()='Remove']")).click();
            sleep(2000);
            List<WebElement> quantityListAfter = driver.findElements(By.xpath("//tr/td[text()='1']"));
            Assert.assertTrue(quantityListBefore.size() > quantityListAfter.size());
            sleep(2000);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
