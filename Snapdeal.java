package day11;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Snapdeal {

	public static void main(String[] args) throws InterruptedException {
		
		// 1)	Go to https://www.snapdeal.com/
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();	
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		
		DesiredCapabilities cap = new DesiredCapabilities(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap); 
		
		driver.get("https://www.snapdeal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		// 2)	‎Mouse over on Toys, Kids' Fashion & more and click on Toys
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElementByXPath("//li[@class='navlink lnHeight']//span[contains(text(),'Toys, Kids')]")).build().perform();
		
		// 3)	 Click Educational Toys in Toys & Games
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Educational Toys']"))).click();
		
		// 4)	Click the Customer Rating 4 star and Up
		WebElement ele = driver.findElementByXPath("//label[@for='avgRating-4.0']/a");
		action.moveToElement(ele).click().perform();
		
		// 5)	Click the offer as 40-50
		Thread.sleep(500);
		action.moveToElement(driver.findElementByXPath("//label[@for='discount-40%20-%2050']/a")).click().perform();
		//wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()=' 40 - 50']"))).click();
		
		// 6)	Check the availability for the pincode
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//input[@placeholder='Enter your pincode']")));
		driver.findElementByXPath("//input[@placeholder='Enter your pincode']").sendKeys("600064");
		driver.findElementByXPath("//button[@class='pincode-check']").click();
		
		if (driver.findElementByXPath("//div[@class='sdCheckbox js-pincode-checkbox ']/following::span[@class='delivery-msg']").isDisplayed()) {
			System.out.println("Delivery is available");
		}
		else {
			System.out.println("Delivery is not available");
		}
		
		// 7)	Click the Quick View of the first product
		WebElement ele1 = driver.findElementByXPath("(//img[@class='product-image wooble'])[1]");
		action.moveToElement(ele1).build().perform();
		driver.findElementByXPath("(//div[contains(text(),'Quick View')])[1]").click();
		
		// 8)	Click on View Details
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[contains(text(),'view details')]"))).click();
		
		// 9)	Capture the Price of the Product and Delivery Charge
		String price = driver.findElementByXPath("//span[@itemprop='price']").getText();
		String text = driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText();
		String deliveryCharge = text.replaceAll("[^0-9]", "");
		System.out.println("The Product price - "+price+" and the Delivery charge -> "+deliveryCharge);
		
		// 10)	Validate the You Pay amount matches the sum of (price+deliver charge)
		driver.findElementByXPath("//span[text()='add to cart']").click();
		String youPayAmt = driver.findElementByXPath("//div[@class='you-pay']/span").getText();
		String youPay = youPayAmt.replaceAll("[^0-9]", "");
		
		int price1 = Integer.parseInt(price);
		int delivery_charge = Integer.parseInt(deliveryCharge);
		int you_payAmt = Integer.parseInt(youPay);
		int totalP1 = price1+delivery_charge;
		if(totalP1==you_payAmt) {
			System.out.println("You pay amount matches the sum");
		}
		else {
			System.out.println("AMount is not matched");
		}
		
		// 11)	Search for Sanitizer
		driver.findElementById("inputValEnter").sendKeys("Sanitizer",Keys.ENTER);
		
		// 12)	Click on Product "BioAyurveda Neem Power Hand Sanitizer"
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//img[contains(@title,'BioAyurveda Neem Power')]")));
		driver.findElementByXPath("//img[contains(@title,'BioAyurveda Neem Power')]").click();
		
		/** To switch the control to New Window **/
		Set<String> windowHandles_set = driver.getWindowHandles();
		List<String> window_list = new ArrayList<>(windowHandles_set);
		
		driver.switchTo().window(window_list.get(1));
		
		// 13)	Capture the Price and Delivery Charge
		String sanitizerPrice = driver.findElementByXPath("//span[@itemprop='price']").getText();
		String textDelivery = driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText();
		String deliveryChargeS = textDelivery.replaceAll("[^0-9]", "");
		System.out.println("The Product price - "+sanitizerPrice+" and the Delivery charge -> "+deliveryChargeS);
		
		int price2 = Integer.parseInt(sanitizerPrice);
		int deliveryCharge2 = Integer.parseInt(deliveryChargeS);
		int totalP2 = price2+deliveryCharge2;
		
		// 14)	Click on Add to Cart
		driver.findElementByXPath("//span[text()='add to cart']").click();

		// 15)	Click on Cart
		driver.findElementByXPath("//span[text()='Cart']").click();
		
		// 16)	Validate the Proceed to Pay matches the total amount of both the products
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//input[contains(@value,'PROCEED')]")));
		String proceedToPayAmt = driver.findElementByXPath("//input[contains(@value,'PROCEED')]").getAttribute("value");
		String totalAmount = proceedToPayAmt.replaceAll("[^0-9]", "");
		
		int totalPay = Integer.parseInt(totalAmount);
		
		if (totalPay==totalP1+totalP2) {
			System.out.println("Total prices in cart matched with sum of product cost");
		}
		else {
			System.out.println("Total is not matched");
		}
		
		// 17)	Close all the windows
		driver.quit();
		
	}

}
