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

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class SixthSection11Test {
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
    public void RegistrationTest() throws InterruptedException {
        String s = CommonMethods.getRandomNumber(8);
        driver.get("http://127.0.0.1/litecart");
        driver.findElement(By.xpath("//a[@href and text()='New customers click here']")).click();

        sleep(2000);
        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Kate");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Skob");
        driver.findElement(By.xpath("//input[@name='address1']")).sendKeys("Green Park");
        driver.findElement(By.xpath("//input[@name='city']")).sendKeys("New York");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("NewYorkKateTest" + s + "@mail.ru");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("9999990");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("999999");
        driver.findElement(By.xpath("//input[@name='confirmed_password']")).sendKeys("999999");
        driver.findElement(By.xpath("//input[@name='postcode']")).sendKeys("12345");

        driver.findElement(By.xpath("//span[contains(@id,'select2-country')]")).click();
        driver.findElement(By.xpath("//li[@class='select2-results__option' and text()='United States']")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[text()=' Your customer account has been created.']")).isDisplayed());

        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("NewYorkKateTest" + s + "@mail.ru");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("999999");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[text()=' You are now logged in as Kate Skob.']")).isDisplayed());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
