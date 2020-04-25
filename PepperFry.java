package day8;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PepperFry {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		// 1) Go to https://www.pepperfry.com/
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();	
		
		/*** To handle any intermittent pop-up ***/
		DesiredCapabilities cap = new DesiredCapabilities(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);
		
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.pepperfry.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@class='popup-close'])[5]")));
		driver.findElementByXPath("(//a[@class='popup-close'])[5]").click();
				
		// 2) Mouse over on Furniture and click Office Chairs under Chairs
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElementByLinkText("Furniture")).build().perform();
		Thread.sleep(100);
		driver.findElementByLinkText("Office Chairs").click();
		
		// 3) click Executive Chairs
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//h5[text()='Executive Chairs']"))).click();
		//driver.findElementByXPath("//h5[text()='Executive Chairs']").click();

		// 4) Change the minimum Height as 50 in under Dimensions
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//input[@class='clipFilterDimensionHeightValue'])[1]"))).clear();
		driver.findElementByXPath("(//input[@class='clipFilterDimensionHeightValue'])[1]").sendKeys("50", Keys.TAB);
		
		// 5) Add "Poise Executive Chair in Black Colour" chair to Wishlist
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//a[text()='Poise Executive Chair in Black Colour']")));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", 
		driver.findElementByXPath("(//a[text()='Poise Executive Chair in Black Colour']/following::a[@id='clip_wishlist_'])[1]"));
		//driver.findElementByXPath("(//a[text()='Poise Executive Chair in Black Colour']/following::a[@id='clip_wishlist_'])[1]").click();
		int wish_count = 0;
		wish_count++;
		System.err.println("wish count1 "+wish_count);
		
		// 6) Mouseover on Homeware and Click Pressure Cookers under Cookware
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByLinkText("Homeware")));
		Actions action1 = new Actions(driver);
		action1.moveToElement(driver.findElementByLinkText("Homeware")).build().perform();
		Thread.sleep(1000);
		driver.findElementByXPath("//a[text()='Pressure Cookers']").click();
		Thread.sleep(2000);
		
		// 7) Select Prestige as Brand
		wait.until(ExpectedConditions.elementToBeClickable
		(driver.findElementByXPath("//input[@id='brandsnamePrestige']/following::label[text()='Prestige']")))
		.click();
		Thread.sleep(2000);
		
		// 8) Select Capacity as 1-3 Ltr
		wait.until(ExpectedConditions.elementToBeClickable
		(driver.findElementByXPath("//input[@id='brandsnamePrestige']/following::label[text()='1 Ltr - 3 Ltr']")))
		.click();
		
		// 9) Add "Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr" to Wishlist
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable
		(driver.findElementByXPath("//a[text()='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr']//following::div[3]/a")))
		.click();
		wish_count++;
		System.err.println("wish count2 "+wish_count);
				
		// 10) Verify the number of items in Wishlist
		Thread.sleep(2000);
		int wishcount = Integer.parseInt(driver.findElementByXPath("//div[@class='wishlist_bar']//span").getText());
		
		if(wish_count == wishcount) {
			System.out.println("verified wish count as : "+wishcount);
		}
		else {
			System.out.println("Wish count is not matched");
		}
		
		// 11) Navigate to Wishlist
		driver.findElementByXPath("//div[@class='wishlist_bar']/a").click();
		
		// 12) Move Pressure Cooker only to Cart from Wishlist
		Thread.sleep(2000);
		driver.findElementByXPath
		("//a[text()='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr By...']//following::a[text()='Add to Cart']")
		.click();
		
		// 13) Check for the availability for Pincode 600128
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//input[@class='srvc_pin_text']"))).clear();
		driver.findElementByXPath("//input[@class='srvc_pin_text']").sendKeys("600128");
		
		driver.findElementByXPath("//a[text()='Check']").click();
			
		// 14) Click Proceed to Pay Securely
		Thread.sleep(500);
		driver.findElementByXPath("//a[contains(text(),'Proceed to pay securely')]").click();
		
		// 15 Click Proceed to Pay
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='PLACE ORDER'])[1]"))).click();
		
		// 16) Capture the screenshot of the item under Order Item
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementById("ordersummary_accordian_header"))).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//li[@class='onepge_ordersummary slick-slide slick-current slick-active']/figure")));
		
		WebElement orderSummary = driver.findElementByXPath("//li[@class='onepge_ordersummary slick-slide slick-current slick-active']/figure");
		File srcFile = orderSummary.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./ScreenShots/pepperFry_orders.png");
		FileUtils.copyFile(srcFile, destFile);
		
		Thread.sleep(500);
		
		// 17) Close the browser
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
