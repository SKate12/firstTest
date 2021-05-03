package ru.test.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class TenSection17Test {
    private EventFiringWebDriver driver;
    private WebDriverWait wait;

    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + " found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);
        }
    }

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new EventFiringWebDriver(new ChromeDriver(options));
        driver.register(new MyFirstTest.MyListener());
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest() throws InterruptedException {
        driver.get("http://127.0.0.1/litecart/admin");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit' and @name='login']")).click();
        Assert.assertTrue(driver.findElement(By.id("sidebar")).isDisplayed());

        wait.until(visibilityOf(driver.findElement(By.xpath("//a[@href ='http://127.0.0.1/litecart/admin/?app=catalog&doc=catalog']"))));
        driver.findElement(By.xpath("//a[@href ='http://127.0.0.1/litecart/admin/?app=catalog&doc=catalog']")).click();

        wait.until(visibilityOf(driver.findElement(By.xpath("//a[@href ='http://127.0.0.1/litecart/admin/?app=catalog&doc=catalog&category_id=1']"))));
        driver.findElement(By.xpath("//a[@href ='http://127.0.0.1/litecart/admin/?app=catalog&doc=catalog&category_id=1']")).click();

        List<WebElement> hrefs = driver.findElements(By.xpath("//a[contains(@href,'&category_id=1&product_id=') and text()]"));
        List<String> hrefsItems = new ArrayList<>();

        for (WebElement href : hrefs) {
            hrefsItems.add(href.getAttribute("href"));
        }
        for (String href : hrefsItems) {
            driver.findElement(By.xpath("//a[@href='" + href + "']")).click();
            driver.manage().logs().get("browser").forEach(l -> System.out.println(l));
            driver.navigate().back();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
