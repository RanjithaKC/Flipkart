package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrappers;
    WebDriverWait wait;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */
    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start of testCase01");
        wrappers = new Wrappers();
        driver.get("https://www.flipkart.com/");
        wrappers.searchProduct("Washing Machine", driver);
        Thread.sleep(2000);
        wrappers.clickOnElement(driver, By.xpath("//div[contains(text(),'Popularity')]"), "txtPopularity");
        Thread.sleep(2000);
        Boolean status = wrappers.starRatingAndPrintingCount(driver,
                By.xpath("//span[contains(@id,'productRating')]/child::div[@class='XQDdHH']"), 4.0);
        System.out.println("End of testCase01");
        Assert.assertTrue(status, "Failed to get the count of mashing machine based on rating bellow 4*");

    }

    @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Start of testCase02");
        Boolean status = false;
        wrappers = new Wrappers();
        wait = new WebDriverWait(driver, Duration.ofSeconds(4l));
        driver.get("https://www.flipkart.com/");
        Thread.sleep(3000);
        wrappers.searchProduct("iPhone", driver);
        // driver.navigate().refresh();
        Thread.sleep(3000);
        status = wrappers.validateSearchInput(driver);
        // Boolean status = wrappers.printTitleAndDiscount(driver,
        // By.xpath("//div[contains(@class,'yKfJKb')]"), 15);
        status = wrappers.printProductAndDiscount(driver, 17);
        System.out.println("Searched product is iPhone " + status);
        System.out.println("End of testCase02");
        Assert.assertTrue(status, "Failed to print product along with discount");

    }

    @Test
    public void testCase03() throws InterruptedException {
        System.out.println("Start of testCase03");
        wrappers = new Wrappers();
        wait = new WebDriverWait(driver, Duration.ofSeconds(4l));
        driver.get("https://www.flipkart.com/");
        Thread.sleep(3000);
        wrappers.searchProduct("Coffee Mug", driver);
        // Thread.sleep(3000);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[contains(text(),'4★ & above')]/../div[1]")));
        wrappers.clickOnElement(driver, By.xpath("//div[contains(text(),'4★ & above')]/../div[1]"),
                "4*andAbove CheckBox");
        Thread.sleep(3000);
        Boolean status = wrappers.printTopFiveProductTitleAndImageURLWithHighestNumberOfRating(driver,
                By.xpath("//div[@class='slAVV4']"));
        System.out.println("End of testCase03");
        Assert.assertTrue(status, "Failed to print top 5 product with img url based on number of review");

    }

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}