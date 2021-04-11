package ru.test.selenium;

import org.junit.After;
import org.junit.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class FifthSection10Test {
    private WebDriver driver;

    @Test
    public void checkStickerTestChrome() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        checkProduct(driver);
    }

    @Test
    public void checkStickerTestFirefox() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        checkProduct(driver);
    }

    @Test
    public void checkStickerTestIE() {
        System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        caps.setCapability("ignoreZoomSetting", true);
        driver = new InternetExplorerDriver(caps);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        checkProduct(driver);
    }

    public void checkProduct(WebDriver driver) {
        driver.get("http://127.0.0.1/litecart");
        WebElement productCard = driver.findElement(By.xpath("//div[@class = 'sticker sale']//ancestor::li[contains(@class,'product')]"));

        String name = productCard.findElement(By.xpath("a/div[@class='name']")).getText();
        WebElement regPrice = productCard.findElement(By.xpath("a/div[@class='price-wrapper']/*[@class ='regular-price']"));
        WebElement camPrice = productCard.findElement(By.xpath("a/div[@class='price-wrapper']/*[@class ='campaign-price']"));

        Map<String, String> productInfoMainPage = new HashMap<>();
        productInfoMainPage.put("regPrice", regPrice.getText());
        productInfoMainPage.put("camPrice", camPrice.getText());
        productInfoMainPage.put("regPriceColor", regPrice.getCssValue("color"));
        productInfoMainPage.put("camPriceColor", camPrice.getCssValue("color"));
        productInfoMainPage.put("regPriceSize", regPrice.getCssValue("font-size"));
        productInfoMainPage.put("camPriceSize", camPrice.getCssValue("font-size"));
        productInfoMainPage.put("regPriceStyleFontWeight", regPrice.getCssValue("font-weight"));
        productInfoMainPage.put("camPriceStyleFontWeight", camPrice.getCssValue("font-weight"));
        productInfoMainPage.put("regPriceStyle", regPrice.getCssValue("text-decoration"));
        productInfoMainPage.put("camPriceStyle", camPrice.getCssValue("text-decoration"));

        checkElementOnPage(productInfoMainPage);

        productCard.click();

        String nameInProdCard = driver.findElement(By.xpath("//h1[@itemprop='name']")).getText();
        WebElement regPriceInProd = driver.findElement(By.xpath("//div[@class='price-wrapper']/*[@class ='regular-price']"));
        WebElement camPriceInProd = driver.findElement(By.xpath("//div[@class='price-wrapper']/*[@class ='campaign-price']"));

        Map<String, String> productInfoProdPage = new HashMap<>();
        productInfoProdPage.put("regPrice", regPriceInProd.getText());
        productInfoProdPage.put("camPrice", camPriceInProd.getText());
        productInfoProdPage.put("regPriceColor", regPriceInProd.getCssValue("color"));
        productInfoProdPage.put("camPriceColor", camPriceInProd.getCssValue("color"));
        productInfoProdPage.put("regPriceSize", regPriceInProd.getCssValue("font-size"));
        productInfoProdPage.put("camPriceSize", camPriceInProd.getCssValue("font-size"));
        productInfoProdPage.put("regPriceStyleFontWeight", regPriceInProd.getCssValue("font-weight"));
        productInfoProdPage.put("camPriceStyleFontWeight", camPriceInProd.getCssValue("font-weight"));
        productInfoProdPage.put("regPriceStyle", regPriceInProd.getCssValue("text-decoration"));
        productInfoProdPage.put("camPriceStyle", camPriceInProd.getCssValue("text-decoration"));

        checkElementOnPage(productInfoProdPage);

        Assert.assertEquals(name, nameInProdCard);
        Assert.assertEquals(productInfoMainPage.get("regPrice"), productInfoProdPage.get("regPrice"));
        Assert.assertEquals(productInfoMainPage.get("camPrice"), productInfoProdPage.get("camPrice"));
    }

    public void checkElementOnPage(Map<String, String> product) {
        Assert.assertTrue(isPriceMoreThanSecondPrice(product.get("regPrice"),
                product.get("camPrice")));
        checkColorGrey(product.get("regPriceColor"));
        Assert.assertTrue(isColorRed(product.get("camPriceColor")));

        Assert.assertTrue(isSolid(product.get("regPriceStyle")));
        Assert.assertTrue(isNoneSolid(product.get("camPriceStyle")));
        Assert.assertTrue(isWordsMoreThanSecondWords(product.get("camPriceStyleFontWeight"),
                product.get("regPriceStyleFontWeight")));
    }

    public boolean isPriceMoreThanSecondPrice(String firstPrice, String secondPrice) {
        return Integer.parseInt(firstPrice.replace("$", ""))
                > Integer.parseInt(secondPrice.replace("$", ""));
    }

    public boolean isWordsMoreThanSecondWords(String firstPrice, String secondPrice) {
        return Integer.parseInt(firstPrice.replace("px", ""))
                > Integer.parseInt(secondPrice.replace("px", ""));
    }

    public void checkColorGrey(String color) {
        String substring = color.substring(color.indexOf("(") + 1, color.lastIndexOf(")"));
        List<String> split = Arrays.asList(substring.split(", ").clone());
        Assert.assertEquals(split.get(0), split.get(1));
        Assert.assertEquals(split.get(2), split.get(1));
    }

    public boolean isColorRed(String color) {
        return color.contains(", 0, 0");
    }

    public boolean isNoneSolid(String style) {
        if (driver.getClass().toString().contains("ChromeDriver")) {
            return style.contains("none solid");
        } else {
            return !style.contains("line-through");
        }
    }

    public boolean isSolid(String style) {
        if (driver.getClass().toString().contains("ChromeDriver")) {
            return style.contains("solid") & !style.contains("none");
        } else {
            return style.contains("line-through") & !style.contains("none");
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
