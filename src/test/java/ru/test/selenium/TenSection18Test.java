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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class TenSection18Test {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setCapability("proxy","localhost:8888");
//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy();
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability("proxy", proxy);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkCountryLinks() throws InterruptedException {
        driver.get("http://127.0.0.1/litecart/admin");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit' and @name='login']")).click();
        Assert.assertTrue(driver.findElement(By.id("sidebar")).isDisplayed());

        sleep(2000);
        driver.findElement(By.xpath("//span[text()='Countries']")).click();

        driver.findElement(By.xpath("//tr[@class='row']/td/a")).click();

        List<String> s = new ArrayList<>();
        for (WebElement element : driver.findElements(By.xpath("//tbody//a[@href and @target='_blank']"))) {
            s.add(element.getAttribute("href"));
        }

        for (int i = 4; i < s.size(); i++) {
            String currentUrlMainPage = driver.getCurrentUrl();

            Set<String> oldWindowsSet = driver.getWindowHandles();
            driver.findElement(By.xpath("//a[@href='" + s.get(i) + "']")).click();

            String newWindowHandle = (new WebDriverWait(driver, 10))
                    .until(new ExpectedCondition<String>() {
                               public String apply(WebDriver driver) {
                                   Set<String> newWindowsSet = driver.getWindowHandles();
                                   newWindowsSet.removeAll(oldWindowsSet);
                                   return newWindowsSet.size() > 0 ?
                                           newWindowsSet.iterator().next() : null;
                               }
                           }
                    );
            driver.switchTo().window(newWindowHandle);
            driver.close();
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(0));
            Assert.assertEquals(driver.getCurrentUrl(), currentUrlMainPage);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
