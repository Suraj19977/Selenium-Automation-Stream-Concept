package com.selenium.automation.SeleniumAutomation;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandOrderTest {
	public static void main(String[]args) {
		String prodName= "ZARA COAT 3";
WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("mechanicsf76@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("1@Suraj1997");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals(prodName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//ng-animating
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));//invisibilityOfElementLocated method also we can use but it reduces the performance and we need to provide on locator name followed by its path. To increase the performance we have to use only invisibilityOf method by entering driver.findElement followed by path. 
		driver.findElement(By.cssSelector("button[routerlink*=cart]")).click();
		
		 List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cart h3"));
		 boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(prodName));
		 Assert.assertTrue(match);
		 driver.findElement(By.cssSelector(".totalRow button")).click(); //this xpath can also be used---> [class*='subtotal cf ng-star-inserted'] button
	     
		 Actions a = new Actions(driver);
		 a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='ta-results']")));
		 driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		 driver.findElement(By.cssSelector("[class*='actions'] a")).click();
		 String confirmessage = driver.findElement(By.cssSelector("[class*='hero-primary']")).getText();
		 Assert.assertTrue(confirmessage.equalsIgnoreCase("Thankyou for the order."));
		 driver.quit();
	}
	

}

