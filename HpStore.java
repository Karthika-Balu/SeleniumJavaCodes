package day4;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class HpStore {
	
	@Test
	public void placeOrder() throws InterruptedException {
		
		// 1) Go to https://store.hp.com/in-en/
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();	
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://store.hp.com/in-en/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		//driver.findElementByXPath("//button[text()='Accept Cookies']").click();
		try {
			driver.findElementByXPath("//button[text()='Accept Cookies']").click();
		}
		catch (NoSuchElementException exception) {
			System.out.println("Exception2 is catched");
		}
		
		try {
			driver.findElementByXPath("//span[@class='optly-modal-close close-icon']").click();
		}
		catch (NoSuchElementException exception) {
			System.out.println("Exception1 is catched");
		}
		
		/* WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[@class='inside_notify_content']")));
	    driver.findElementById("inside_holder").click();*/

		// 2) Mouse over on Laptops menu and click on Pavilion
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElementByLinkText("Laptops")).perform();
		
		driver.findElementByLinkText("Pavilion").click();
		
		try {
			driver.findElementByXPath("//div[@class='inside_closeButton fonticon icon-hclose']").click();
		}
		catch (NoSuchElementException exception) {
			System.out.println("Exception3 is catched");
		}
		
		try {
			driver.findElementByXPath("//button[text()='Accept Cookies']").click();
		}
		catch (NoSuchElementException exception) {
			System.out.println("Exception2 is catched");
		}
		
		try {
			driver.findElementByXPath("//span[@class='optly-modal-close close-icon']").click();
		}
		catch (NoSuchElementException exception) {
			System.out.println("Exception1 is catched");
		}

		// 3) Under SHOPPING OPTIONS -->Processor -->Select Intel Core i7
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.scrollBy(0, 250)");
		driver.findElementByXPath("(//span[text()='Processor'])[2]").click(); 
		driver.findElementByXPath("//span[text()='Intel Core i7']").click(); 
		Thread.sleep(3000); 
		
		// 4) Hard Drive Capacity -->More than 1TB
		driver.findElementByXPath("//span[text()='More than 1 TB']").click();
		
		// 5) Select Sort By: Price: Low to High
		js.executeScript("window.scrollBy(0, 600)");
		WebElement sortBy = driver.findElementById("sorter");
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(sortBy)).click();
		Select sortBySelect = new Select(sortBy);
		sortBySelect.selectByVisibleText("Price : Low to High ");
		
		// 6) Print the First resulting Product Name and Price
		
		String productName = driver.findElementByXPath("(//a[@class='product-item-link'])[1]").getText();	
		String price = driver.findElementByXPath("(//span[@class='price-container price-final_price tax weee']//span[@class='price'])[1]").getText();
		String price_pd = price.replaceAll("\\D","");
		System.out.println("The first resulting product is -- "+productName+" and its price is -- "+price_pd);	
		
		// 7) Click on Add to Cart
		driver.findElementByXPath("(//button[@title='Add To Cart'])[1]").click();
		
		// 8) Click on Shopping Cart icon --> Click on View and Edit Cart
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[@class='action showcart']"))).click();
		driver.findElementByXPath("//span[text()='View and edit cart']").click();

		// 9) Check the Shipping Option --> Check availability at Pincode
		driver.findElementById("standard_delivery").click();
		driver.findElementByName("pincode").sendKeys("600064");
		driver.findElementByXPath("//button[text()='check']").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[@class='available']"))).click();
		System.out.println("The delivery of status is :: "+driver.findElementByXPath("//span[@class='available']").getText());
		
		// 10) Verify the order Total against the product price
		String orderTotal = driver.findElementByXPath("//tr[@class='grand totals']//strong/span").getText();
		String orderAmount = orderTotal.replaceAll("\\D","");
		System.out.println("Order Total amount is :: "+orderAmount);
		
		// 11) Proceed to Checkout if Order Total and Product Price matches
		if(price_pd.equals(orderAmount)) {
			System.out.println("order amount is matched");
			driver.findElementById("sendIsCAC").click();
		}
		else {
			System.out.println("order amount is not matched");
		}
		
		// 12) Click on Place Order
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='place-order-primary']//span[text()='Place Order']"))).click();
		
		// 13) Capture the Error message and Print
		String errorMessage = driver.findElementById("customer-email-error").getText();
		System.out.println("The error message displayed is -- "+errorMessage);
		
		// 14) Close Browser
		driver.quit();

		
	}
	
}
