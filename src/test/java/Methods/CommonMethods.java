package Methods;

import java.util.Random;

public class CommonMethods {
    public static String getRandomNumber(int n) {
        StringBuilder number = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            number.append(random.nextInt(8) + 1);
        }
        return number.toString();
    }
}
