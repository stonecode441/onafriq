import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created By Chivo
 */
public class AutomationExercise {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            // Step 1: Go to the website and click on Sign-In
            driver.get("https://www.automationexercise.com/");
            driver.findElement(By.xpath("//a[contains(text(), 'Signup / Login')]")).click();

            // Step 2: Sign-In using the given credentials
            driver.findElement(By.name("email")).sendKeys("qat@mailinator.com");
            driver.findElement(By.name("password")).sendKeys("123456");
            driver.findElement(By.xpath("//button[contains(text(), 'Login')]")).click();

            // Step 3: Get the label and associated price of items under FEATURED ITEMS
            List<WebElement> items = driver.findElements(By.cssSelector(".features_items .productinfo"));
            List<Item> itemList = new ArrayList<>();
            for (WebElement item : items) {
                String label = item.findElement(By.cssSelector("p")).getText();
                String price = item.findElement(By.cssSelector("h2")).getText().replaceAll("[^\\d.]", "");
                itemList.add(new Item(label, price));
            }

            // Sort items by price (Low to High)
            itemList.sort(Comparator.comparingDouble(i -> Double.parseDouble(i.price)));

            // Print sorted items
            for (Item item : itemList) {
                System.out.println(item);
            }

            // Step 4: Scroll up and navigate to Women >> Dress >> Tops
            driver.findElement(By.xpath("//a[contains(text(), 'Women')]")).click();
            driver.findElement(By.xpath("//a[contains(text(), 'Dresses')]")).click();
            driver.findElement(By.xpath("//a[contains(text(), 'Tops')]")).click();

            // Select and add Fancy Green Top and Summer White Top to cart
            driver.findElement(By.xpath("//a[contains(text(), 'Fancy Green Top')]")).click();
            driver.findElement(By.xpath("//button[contains(text(), 'Add to cart')]")).click();
            driver.findElement(By.xpath("//a[contains(text(), 'Continue Shopping')]")).click();
            driver.findElement(By.xpath("//a[contains(text(), 'Summer White Top')]")).click();
            driver.findElement(By.xpath("//button[contains(text(), 'Add to cart')]")).click();
            driver.findElement(By.xpath("//a[contains(text(), 'View Cart')]")).click();

            // Step 5: Proceed to checkout and add comments
            driver.findElement(By.xpath("//a[contains(text(), 'Proceed To Checkout')]")).click();
            driver.findElement(By.name("message")).sendKeys("Order placed.");
            driver.findElement(By.xpath("//button[contains(text(), 'Place Order')]")).click();

            // Enter card details
            driver.findElement(By.name("name_on_card")).sendKeys("Test Card");
            driver.findElement(By.name("card_number")).sendKeys("4100 0000 0000 0000");
            driver.findElement(By.name("cvc")).sendKeys("123");
            driver.findElement(By.name("expiry_month")).sendKeys("01");
            driver.findElement(By.name("expiry_year")).sendKeys("1900");
            driver.findElement(By.xpath("//button[contains(text(), 'Pay and Confirm Order')]")).click();

            // Step 6: Confirm order has been placed
            WebElement confirmationMessage = driver.findElement(By.xpath("//h2[contains(text(), 'Order Placed!')]"));
            if (confirmationMessage.isDisplayed()) {
                System.out.println("Order has been placed successfully.");
            } else {
                System.out.println("Order placement failed.");
            }
        } finally {
            // Close the driver
            driver.quit();
        }

    }
}