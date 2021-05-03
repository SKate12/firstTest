package Methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Random;

import static java.lang.Thread.sleep;

public class CommonMethods {
    public static String getRandomNumber(int n) {
        StringBuilder number = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            number.append(random.nextInt(8) + 1);
        }
        return number.toString();
    }

    public static void goToLitecart(WebDriver driver) {
        driver.get("http://127.0.0.1/litecart");
    }

    public static void goBackToMainPage(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//a[@href='http://127.0.0.1/litecart/en/']")).click();
        sleep(2000);
    }
}
