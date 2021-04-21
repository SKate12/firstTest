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
        wait.until(visibilityOfAllElements(driver.findElements(By.xpath("//tr/td[@class='item']"))));
        wait.until(visibilityOfAllElements(driver.findElements(By.xpath("//li[@class='shortcut']"))));

        int size = driver.findElements(By.xpath("//li[@class='shortcut']")).size();
        for (int i = 0; i < size - 1; i++) {
            driver.findElement(By.xpath("//li[@class='shortcut']")).click();
            driver.findElement(By.xpath("//li[@class='shortcut']")).click();
            driver.findElement(By.xpath("//li[@class='shortcut']")).click();
            int sizeBefore = driver.findElements(By.xpath("//tr/td[@class='item']")).size();
            Assert.assertEquals(sizeBefore, driver.findElements(By.xpath("//li[@class='shortcut']")).size());
            WebElement removeButton = driver.findElement(By.xpath("//button[text()='Remove']"));
            WebElement item = driver.findElement(By.xpath("//tr/td[@class='item']"));
            removeButton.click();
            wait.until(stalenessOf(removeButton));
            wait.until(stalenessOf(item));
        }
        Assert.assertEquals(1, driver.findElements(By.xpath("//tr/td[@class='item']")).size());
        WebElement removeButton = driver.findElement(By.xpath("//button[text()='Remove']"));
        WebElement item = driver.findElement(By.xpath("//tr/td[@class='item']"));
        removeButton.click();
        wait.until(stalenessOf(removeButton));
        wait.until(stalenessOf(item));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
