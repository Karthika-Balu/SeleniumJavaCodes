package day2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Nykaa {
	
	@Test
	public void shoping() throws InterruptedException {
		
	// Setting Property and disable notifications
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		
		options.addArguments("--disable-notifications");
		
		ChromeDriver driver = new ChromeDriver(options);
		
		// 1) Launch the URL
				
		driver.get("https://www.nykaa.com/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				
		// 2) Mouse over on Brands and Mouseover on Popular
		
		Actions action1 = new Actions(driver);
		
		action1.moveToElement(driver.findElementByXPath("//a[text()='brands']")).perform();
		
		WebDriverWait wait = new WebDriverWait(driver,15);
		
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()='Popular']"))).click();
		
		String parent_windowHandle = driver.getWindowHandle();
		
		// 3) Click Loreal Paris brand
		
		driver.findElementByXPath("//img[@src='https://adn-static2.nykaa.com/media/wysiwyg/2019/lorealparis.png']").click();
		
		// 4) Go to the newly opened window and check the title contains L'Oreal Paris
		
		Set<String> windowHandles = driver.getWindowHandles();
		
		List<String> windowHandles_list = new ArrayList<>(windowHandles);
		
		driver.switchTo().window(windowHandles_list.get(1));
		
		String title = driver.getTitle();
		
		System.out.println("Title of the current window is ::"+title);
		
		if(title.contains("L'Oreal Paris")) {
			
			System.out.println("The title contains L'Oreal Paris");
		}
		else {
			System.out.println("The title doesn't contain L'Oreal Paris");
		}
		
		// 5) Click sort By and select customer top rated
		
		driver.findElementByXPath("//span[@class='pull-left']").click();
		driver.findElementByXPath("//span[text()='customer top rated']").click();
		Thread.sleep(3000);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		// 6) Click Category and click Shampoo
		driver.findElementByXPath("//div[text()='Category']").click();
		
		WebElement shampoo_category = driver.findElementByXPath("(//span[contains(text(), 'Shampoo')])[1]");
				
		shampoo_category.click();
		
		// 7) check whether the Filter is applied with Shampoo
		
		if(driver.findElementByXPath("//ul[@class='pull-left applied-filter-lists']/li").isDisplayed()) {
			
			String filter_text = driver.findElementByXPath("//ul[@class='pull-left applied-filter-lists']/li").getText();
			
			if (filter_text.contains("Shampoo")) {
				
				System.out.println("The filter applied is :: "+filter_text);
			}
			else {
				System.out.println("The filter applied dont contain :: "+filter_text);
			}
		}
		else {
			
			System.out.println("The filter section is not showing any filters applied");
		}
		
		// 8) Click on L'Oreal Paris Colour Protect Shampoo
		WebElement shampooToSelect = driver.findElementByXPath("//img[@src='https://images-static.nykaa.com/media/catalog/product/tr:w-276,h-276,cm-pad_resize/8/9/8901526102518_color_protect_shampoo_75ml_82.5ml__i1_1.png']");
		
		js.executeScript("arguments[0].scrollIntoView();", shampooToSelect);
		
		shampooToSelect.click();
		
		// 9) GO to the new window and select size as 175ml
		
		Set<String> windowHandles2 = driver.getWindowHandles();
		
		List<String> windowHandles2_list = new ArrayList<>(windowHandles2);
		
		driver.switchTo().window(windowHandles2_list.get(2));
		
		if(driver.findElementByXPath("//span[text()='175ml']").isEnabled()) {
			driver.findElementByXPath("//span[text()='175ml']").click();
		}
		
		// 10) Print the MRP of the product
		
		String mrp_text = driver.findElementByXPath("(//div[@class='price-info']/span[2])[1]").getText();
		
		System.out.println("The price of the product is :: "+mrp_text);
		
		// 11) Click on ADD to BAG
		
		driver.findElementByXPath("(//button[text()='ADD TO BAG'])[1]").click();
		
		// 12) Go to Shopping Bag 
		
		driver.findElementByClassName("AddToBagbox").click();
		
		// 13) Print the Grand Total amount
		
		String grand_total = driver.findElementByXPath("//div[@class='value medium-strong']").getText();
		
		System.out.println("The Grand total in Shopping bag is :: "+grand_total);
		
		// 14) Click Proceed
		
		driver.findElementByClassName("proceed-arrow").click();
		
		// 15) Click on Continue as Guest
		
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//button[text()='CONTINUE AS GUEST']"))).click();
			
		// 16) Print the warning message (delay in shipment)
		
		String warning = driver.findElementByClassName("message").getText();
		
		System.out.println("Warning :: "+warning);
		
		// 17) Close all windows
		
		driver.quit();

		
		
		
		
		
		
		

	}

}
