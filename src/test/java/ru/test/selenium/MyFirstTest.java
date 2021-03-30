package ru.test.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyFirstTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest() {
        driver.get("https://market.yandex.ru/");
        driver.findElement(By.xpath("//div[@data-zone-name='category-link']//span[text()='Электроника']/parent::a")).click();
        Assert.assertEquals("Электроника", driver.findElement(By.xpath("//h1")).getText());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
