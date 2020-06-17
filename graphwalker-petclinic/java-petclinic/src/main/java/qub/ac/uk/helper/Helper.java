package qub.ac.uk.helper;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Random;

public class Helper {

    private static final Logger log = LoggerFactory.getLogger(Helper.class);

    static private Random random = new Random(System.currentTimeMillis());

    final static int timeOut = 10;

    public static int getRandomInt(int max) {
        return random.nextInt(max) + 1;
    }

    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "/user/bin/chromedriver");
        ChromeDriverManager.getInstance().version("78.0.3904").setup();
    }

    public static void tearDown() {
        getActiveInstance().quit();
    }

    private static class WebDriverHolder {
        private static final WebDriver INSTANCE = new ChromeDriver();
    }

    public static WebDriverWait getWaiter() {
        return new WebDriverWait(getActiveInstance(), 10);
    }

    public static WebDriver getActiveInstance() {
        return WebDriverHolder.INSTANCE;
    }

    public static void pause(int time) {
        try{
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}