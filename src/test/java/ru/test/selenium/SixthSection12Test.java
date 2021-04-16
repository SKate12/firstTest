package ru.test.selenium;

import Methods.CommonMethods;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class SixthSection12Test {
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
    public void checkAdminSection() throws InterruptedException {
        driver.get("http://127.0.0.1/litecart/admin");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit' and @name='login']")).click();
        Assert.assertTrue(driver.findElement(By.id("sidebar")).isDisplayed());

        sleep(2000);
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        sleep(2000);
        driver.findElement(By.xpath("//*[text()=' Add New Product']")).click();

        String newProdName = "Apple" + CommonMethods.getRandomNumber(4);

        driver.findElement(By.xpath("//input[@type='radio']")).click();
        driver.findElement(By.xpath("//input[@name='name[en]']")).sendKeys(newProdName);
        driver.findElement(By.xpath("//input[@name='code']")).sendKeys("Code" + CommonMethods.getRandomNumber(4));

        File a = new File("src/test/resources/pictures/1345.jpg");
        String absolute = a.getAbsolutePath();
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(absolute);

        driver.findElement(By.name("date_valid_from")).clear();
        driver.findElement(By.name("date_valid_from")).sendKeys("12/10/2020");
        driver.findElement(By.name("date_valid_to")).clear();
        driver.findElement(By.name("date_valid_to")).sendKeys("12/10/2025");

        driver.findElement(By.xpath("//a[@href='#tab-information']")).click();
        sleep(2000);
        driver.findElement(By.name("keywords")).sendKeys("Apple");
        driver.findElement(By.name("short_description[en]")).sendKeys("Apple");
        driver.findElement(By.xpath("//div[@class='trumbowyg-editor']")).sendKeys("Red sweet apple");
        driver.findElement(By.name("head_title[en]")).sendKeys("Apple");
        driver.findElement(By.name("meta_description[en]")).sendKeys("Apple");

        driver.findElement(By.xpath("//a[@href='#tab-prices']")).click();
        sleep(2000);
        driver.findElement(By.name("purchase_price")).clear();
        driver.findElement(By.name("purchase_price")).sendKeys("12,34");
        driver.findElement(By.xpath("//*[@value='USD']")).click();

        driver.findElement(By.name("prices[USD]")).clear();
        driver.findElement(By.name("prices[USD]")).sendKeys("12.34");

        driver.findElement(By.name("gross_prices[USD]")).clear();
        driver.findElement(By.name("gross_prices[USD]")).sendKeys("1.4");

        driver.findElement(By.name("prices[EUR]")).clear();
        driver.findElement(By.name("prices[EUR]")).sendKeys("16.34");

        driver.findElement(By.name("gross_prices[EUR]")).clear();
        driver.findElement(By.name("gross_prices[EUR]")).sendKeys("2.3");

        driver.findElement(By.name("save")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//a[@href and text() ='" + newProdName + "' ]")).isDisplayed());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
