package day16.evaluation1;

import static org.testng.Assert.expectThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ajio {

	public static void main(String[] args) throws InterruptedException {

		// 1)Go to https://www.ajio.com/
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		DesiredCapabilities cap = new DesiredCapabilities(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap); 
		
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.ajio.com/shop/sale");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		try {
			driver.findElementByXPath("//div[@class='ic-close-quickview']").click();
			System.out.println("closed quick view pop-up");
		} catch (Exception e) {
			System.out.println("exception catched");
		}
		
		// 2) Enter Bags in the Search field and Select Bags in Women Handbags
		driver.findElementByXPath("//input[@placeholder='Search AJIO']").sendKeys("Bags", Keys.ENTER);
		driver.findElementByXPath("//label[@for='Women']").click();
		Thread.sleep(500);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", driver.findElementByXPath("//label[@for='Women - Handbags']"));
		//driver.findElementByXPath("//label[@for='Women - Handbags']").click();
		//driver.findElementByXPath("(//span[text()='Women Handbags'])[1]").click();
	    Thread.sleep(1000);
		// 3) Click on five grid and Select SORT BY as "What's New"
		driver.findElementByXPath("//div[@class='five-grid']").click();
		Select sortBy = new Select(driver.findElementByXPath("//div[@class='filter-dropdown']/select"));
		sortBy.selectByVisibleText("What's New");
		
		// 4) Enter Price Range Min as 2000 and Max as 5000
		driver.findElementByXPath("//span[text()='price']").click();
		driver.findElementById("minPrice").sendKeys("2000");
		driver.findElementById("maxPrice").sendKeys("5000", Keys.ENTER);
		//driver.findElementByXPath("//button[@class='rilrtl-button ic-toparw']").click();
		Thread.sleep(500);

		// 5) Click on thefive-gridproduct "Puma Ferrari LS Shoulder Bag"
		WebDriverWait wait = new WebDriverWait(driver,30);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Ferrari LS Shoulder Bag']/parent::div")));
		WebElement elePuma = driver.findElementByXPath("//div[text()='Ferrari LS Shoulder Bag']/parent::div");
		js.executeScript("arguments[0].scrollIntoView(true);",elePuma);
		js.executeScript("arguments[0].click();", elePuma);
		Thread.sleep(1000);
		
		Set<String> windowHandles_set = driver.getWindowHandles();
		List<String> win_list = new ArrayList<String>(windowHandles_set);
		driver.switchTo().window(win_list.get(1));

		// 6) Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount price for the coupon
		
		/*** get the MRP to validate the coupon availability ***/
		String pdtMrp = driver.findElementByXPath("//div[@class='prod-sp']").getText().replaceAll("[^0-9]", "");
		int mrp = Integer.parseInt(pdtMrp);
		String coupCode = "";
		//Double mrp = Double.parseDouble(pdtMrp);
		if (mrp>=2690) {
			System.out.println("Coupon is applicable");
			coupCode = driver.findElementByXPath("//div[@class='promo-title']").getText().replaceAll("\n", "");
			String couponCode = coupCode.substring(8);
			System.out.println("Actual Coupon code is - "+couponCode);
		} else {
			System.out.println("Coupon is not applicable");
		}
		
		String discountText = driver.findElementByXPath("//div[@class='promo-discounted-price']/span").getText().replaceAll("[^0-9]", "");
		double discountPrice = Double.parseDouble(discountText);
		int discountApplied = (int) (mrp-discountPrice);
		System.out.println("The Discounted amount is :: "+discountApplied);

		// 7) Check the availability of the product for pincode 560043, print the expected delivery date if it is available
		driver.findElementByXPath("//span[contains(text(),'Enter pin-code')]").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByName("pincode")));
		driver.findElementByName("pincode").sendKeys("560043",Keys.ENTER);
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[contains(@class,'edd-pincode-msg-details')]")));
		String msgDeliverytext = driver.findElementByXPath("//div[contains(@class,'edd-pincode-msg-details')]").getText();
		System.out.println(msgDeliverytext);

		// 8) Click on Other Informations under Product Details and Print the Customer Care address, phone and email
		driver.findElementByClassName("other-info-toggle").click();
		Thread.sleep(500);
		
		String cusCareText = driver.findElementByXPath("//span[text()='Customer Care Address']").getText();
		String cusCareDetails = driver.findElementByXPath("//span[text()='Customer Care Address']//following-sibling::span[@class='other-info']").getText();
		System.out.println(cusCareText+" --> "+cusCareDetails);
		
		// 9) Click on ADD TO BAG and then GO TO BAG
		driver.findElementByXPath("//span[text()='ADD TO BAG']").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='GO TO BAG']"))).click();
		
		// 10) Check the Order Total before apply coupon
		String orderTotal = driver.findElementByXPath("(//section[@id='orderTotal']/span)[2]").getText().replaceAll("[^0-9]", "");
		Double orderTotAmt = Double.parseDouble(orderTotal);
		
		if (orderTotAmt==mrp) {
			System.out.println("order total is validated");
		} else {
			System.out.println("order total differs in cart");
		}
		
		// 11) Enter Coup.on Code and Click Apply
		driver.findElement(By.id("couponCodeInput")).sendKeys(coupCode);
		driver.findElementByXPath("//button[text()='Apply']").click();
	
		
		// 12) Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("(//span[@class='price-value discount-price'])[2]")));
		String couponSaveAmount = driver.findElementByXPath("(//span[@class='price-value discount-price'])[2]").getText().replaceAll("[^0-9.]", "");
		double couponSave = Double.parseDouble(couponSaveAmount);
		int couponSaveRound = (int) Math.round(couponSave);
		if (couponSaveRound==discountApplied) {
			System.out.println("The discount Amount Matches");
		} else {
			System.out.println("The discounted Amount differs");
		}
		
		// 13) Click on Delete and Delete the item from Bag
		driver.findElementByXPath("//div[@class='delete-btn']").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[text()='DELETE']"))).click();
		
		// 14) Close all the browsers
		driver.quit();

		
	}

}
