package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import demo.Product;
import net.bytebuddy.asm.Advice.Enter;

import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4l));
    WebDriverWait wait;

    public void launchURl(String url, WebDriver driver) {
        driver.get(url);
    }

    public void searchProduct(String productName, WebDriver driver) throws InterruptedException {
        boolean status = false;
        try {
            System.out.println("searching for product : " + productName);
            wait = new WebDriverWait(driver, Duration.ofSeconds(4l));
            WebElement searchTab = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='Pke_EE']")));
            searchTab.clear();
            searchTab.sendKeys(productName);
            String verifySearchedText = searchTab.getAttribute("value");
            System.out.println("Entered product is " + verifySearchedText);
            // if (verifySearchedText.contains("iphone")) {
            // List<WebElement> searchList =
            // wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='YGcVZO
            // _2VHNef']")));
            // for(WebElement option : searchList){
            // if(option.getText().equalsIgnoreCase(productName)){
            // option.click();
            // status = true;
            // }
            // }
            searchTab.sendKeys(Keys.ENTER);
            status = true;
            // }

        } catch (Exception e) {
            System.out.println("found exception : " + e.getMessage());
            status = false;
        }
    }

    public Boolean validateSearchInput(WebDriver driver) {
        Boolean status;
        WebElement searchBox = driver.findElement(By.xpath("//input[@class='zDPmFV']"));
        status = searchBox.getAttribute("value").contains("iphone");
        return status;
    }

    public void clickOnElement(WebDriver driver, By locator, String nameOfElement) {
        boolean status = false;
        System.out.println("clicking on element " + nameOfElement);
        WebElement elementToClick = driver.findElement(locator);
        elementToClick.click();
        status = true;
    }

    public Boolean starRatingAndPrintingCount(WebDriver driver, By locator, double starRating) {
        Boolean status = false;
        int starRatingCount = 0;
        wait = new WebDriverWait(driver, Duration.ofSeconds(4l));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            List<WebElement> ratings = driver.findElements(locator);
            // wait.until(ExpectedConditions.visibilityOfAllElements(ratings));
            for (WebElement rating : ratings) {
                if (Double.parseDouble(rating.getText()) <= starRating) {
                    starRatingCount++;
                }
            }
            System.out.println("count of wasing machine with <=4 rating : " + starRatingCount);
            status = true;
        } catch (Exception e) {
            System.out.println("Exception occured");
            e.printStackTrace();
            status = false;
        }
        return status;
    }

    // public Boolean printTitleAndDiscount(WebDriver driver, By locator, int
    // discount){
    // Boolean status = false;
    // try{
    // List<WebElement> productRows = driver.findElements(locator);
    // Map<String, String> titleAndDiscountMap = new HashMap<>();
    // for(WebElement productRow : productRows){
    // String discountPercentage =
    // productRow.findElement(By.xpath(".//div[@class='UkUFwK']/span")).getText();
    // // String discountPercentage = discountElement.getText();
    // int discountValue = Integer.parseInt(discountPercentage.replaceAll("[^\\d]",
    // "").trim());
    // if(discountValue > discount){
    // String productTitle =
    // productRow.findElement(By.xpath(".//div[@class='KzDlHZ']")).getText();
    // titleAndDiscountMap.put(discountPercentage, productTitle);
    // }
    // }
    // for(Map.Entry<String, String> entry : titleAndDiscountMap.entrySet()){
    // String title = entry.getValue();
    // String value = entry.getKey();
    // System.out.println("product title : "+title+ " discount : "+value);
    // status = true;
    // }
    // }catch(NoSuchElementException e){
    // System.out.println("Exception occutred");
    // e.printStackTrace();
    // status = false;
    // }
    // return status;
    // }

    public Boolean printProductAndDiscount(WebDriver driver, int discountThreshold) {
        // Find product names and discounts
        Boolean status = false;
        // Find product names and discounts
        try {
            List<WebElement> products = driver.findElements(By.xpath(
                    "//div[@class='UkUFwK']/../../../preceding-sibling::div[@class='col col-7-12']/div[@class='KzDlHZ']"));
            List<WebElement> discounts = driver.findElements(By.xpath("//div[@class='UkUFwK']/span"));

            // iterate through products and print titles with their discounts
            // int minSize = Math.min(products.size(), discounts.size());
            for (int i = 0; i < discounts.size(); i++) {
                String productName = products.get(i).getText();
                String discountText = discounts.get(i).getText();
                int discountValue = Integer.parseInt(discountText.replaceAll("[^\\d]", "").trim());
                System.out.println("discount value : " + discountValue);

                // check if discountText is valid and contains a percentage symbol
                if (discountText != null && !discountText.isEmpty() && discountText.contains("%")
                        && discountValue >= 15) {
                    System.out.println("Product: " + productName + " | Discount: " + discountText);
                    status = true;
                    // }else{
                    // System.out.println("All Discount value is less than 17");
                    // status = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public Boolean printTopFiveProductTitleAndImageURLWithHighestNumberOfRating(WebDriver driver, By locator) {
        Boolean status = false;
        try {
            List<WebElement> poductCard = driver.findElements(locator);
            // //div[@class='slAVV4']
            // here created a class Product as collection take object as input too,
            // so we can access the required data with getters.
            List<Product> productList = new ArrayList<>();
            // iterate through product card to get title, url and review number
            for (WebElement product : poductCard) {
                String title = product.findElement(By.xpath(".//a[@class='wjcEIp']")).getText();
                String imageurl = product.findElement(By.xpath("./a[@class='VJA3rP']//img")).getAttribute("src");
                String reviewNumber = product
                        .findElement(By.xpath(".//div[@class='_5OesEi afFzxY']//span[@class='Wphh3N']")).getText();
                int numberOfReviews = Integer.valueOf(reviewNumber.replaceAll("[^\\d]", ""));
                productList.add(new Product(title, imageurl, numberOfReviews));
            }
            Collections.sort(productList, Collections.reverseOrder());

            // Print the top 5 products' titles and image URLs
            for (int i = 0; i < Math.min(5, productList.size()); i++) {
                Product topProduct = productList.get(i);
                System.out.println("title : " + topProduct.getTitle() + "  " + "image url : " + topProduct.getImageUrl()
                        + " " + "reviws : " + topProduct.getNumberOfReviews());
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }
}
