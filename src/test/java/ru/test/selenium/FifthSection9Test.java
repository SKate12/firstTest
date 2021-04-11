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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class FifthSection9Test {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkAdminCountries() {
        driver.get("http://127.0.0.1/litecart/admin");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit' and @name='login']")).click();
        Assert.assertTrue(driver.findElement(By.id("sidebar")).isDisplayed());

        driver.findElement(By.xpath("//span[@class='name'and text()='Countries']")).click();

        List<WebElement> countries = driver.findElements(By.xpath("//tr[@class='row']"));
        List<String> countriesName = new ArrayList<>();
        for (WebElement count : countries) {
            countriesName.add(count.findElement(By.xpath("td/a[@href]")).getText());
        }
        Assert.assertTrue(isABC(countriesName));

        List<String> hrefs = new ArrayList<>();
        for (WebElement country : countries) {
            if (!country.findElements(By.xpath("td")).get(5).getText().equals("0"))
                hrefs.add(country.findElement(By.xpath("td/a")).getAttribute("href"));
        }

        for (String href : hrefs) {
            driver.get(href);
            List<String> zones = new ArrayList<>();
            for (WebElement zone : driver.findElements(By.xpath("//*[contains(@name,'][name]')]"))) {
                zones.add(zone.getAttribute("value"));
            }
            Assert.assertTrue(isABC(zones));
        }
    }

    @Test
    public void checkAdminGeoZones() throws InterruptedException {
        driver.get("http://127.0.0.1/litecart/admin");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit' and @name='login']")).click();
        Assert.assertTrue(driver.findElement(By.id("sidebar")).isDisplayed());

        sleep(2000);
        driver.findElement(By.xpath("//span[@class='name'and text()='Geo Zones']")).click();
        List<String> hrefs = new ArrayList<>();
        for (WebElement country : driver.findElements(By.xpath("//tr[@class='row']"))) {
            hrefs.add(country.findElement(By.xpath("td/a")).getAttribute("href"));
        }
        for (String href : hrefs) {
            driver.get(href);
            List<String> zones = new ArrayList<>();
            for (WebElement zone : driver.findElements(By.xpath("//*[contains(@name,'][zone_code]')]/option[@selected ='selected']"))) {
                zones.add(zone.getText());
            }
            Assert.assertTrue(isABC(zones));
        }
    }

    public boolean isABC(List<String> thelist) {
        String previous = "";
        for (final String current : thelist) {
            if (current.compareTo(previous) < 0)
                return false;
            previous = current;
        }
        return true;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
