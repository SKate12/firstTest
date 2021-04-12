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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FourSectionTest {
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
    public void checkAdminSection() {
        driver.get("http://127.0.0.1/litecart/admin");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit' and @name='login']")).click();
        Assert.assertTrue(driver.findElement(By.id("sidebar")).isDisplayed());

        List<WebElement> sections = driver.findElements(By.xpath("//ul[@id='box-apps-menu']//li/a"));
        List<String> sectionHref = new ArrayList<>();
        for (WebElement section : sections) {
            sectionHref.add(section.getAttribute("href"));
        }
        for (String hrefSection : sectionHref) {
            //пришлось добавить т.к. с первого раза не нажимается
            while (!driver.findElement(By.xpath("//*[@href ='" + hrefSection + "']/parent::li")).
                    getAttribute("class").equals("selected")) {
                driver.findElement(By.xpath("//*[@href ='" + hrefSection + "']")).click();
            }
            List<WebElement> subSections = driver.findElements(By.xpath("//ul[@class='docs']/li/a"));
            List<String> subSectionHref = new ArrayList<>();
            for (WebElement subSection : subSections) {
                subSectionHref.add(subSection.getAttribute("href"));
            }
            if (subSectionHref.size() != 0) {
                for (String hrefSubsection : subSectionHref) {
                    driver.findElement(By.xpath("//ul[@class='docs']//*[@href ='" + hrefSubsection + "']")).click();
                    Assert.assertTrue(driver.findElement(By.xpath("//h1")).isDisplayed());
                }
            } else {
                Assert.assertTrue(driver.findElement(By.xpath("//h1")).isDisplayed());
            }
        }
    }

    @Test
    public void checkStickerTest() {
        driver.get("http://127.0.0.1/litecart");
        List<WebElement> productCards = driver.findElements(By.xpath("//li[contains(@class,'product')]"));
        for (WebElement productCard : productCards) {
            int sizeSticker = productCard.findElements(By.xpath("a//div[contains(@class ,'sticker')]")).size();
            Assert.assertEquals(1, sizeSticker);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
