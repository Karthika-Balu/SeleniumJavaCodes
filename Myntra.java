package day1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Myntra {
	
	@Test
	public void login() throws InterruptedException {
		
		// Setting Property and diable notifications
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		
		options.addArguments("--disable-notifications");
		
		ChromeDriver driver = new ChromeDriver(options);
		
		// 1) Launch the URL
				
		driver.get("https://www.myntra.com/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		Thread.sleep(500);

		
		// 2) to hover on Women option and Select Jackets and coats
		
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElementByXPath("//div[@class='desktop-navContent']//a[text()='Women']")).perform();;
						
		WebDriverWait wait = new WebDriverWait(driver,15);

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByLinkText("Jackets & Coats"))).click();

		// 3) To get the total count and verify with category sum
		
		String text = driver.findElementByClassName("title-count").getText();
		
		String count = text.replaceAll("[^0-9]", "");
		
		int totalCount = Integer.parseInt(count);
		
		String text1 = driver.findElementByXPath("(//span[@class='categories-num'])[1]").getText();
		String jack_count = text1.replaceAll("[^0-9]","");
		int jacketCount = Integer.parseInt(jack_count);
		
		String text2 = driver.findElementByXPath("(//span[@class='categories-num'])[2]").getText();
		String coat_count = text2.replaceAll("[^0-9]","");
		int coatCount = Integer.parseInt(coat_count);
		
		System.out.println(count+" -> "+jack_count+" and "+coat_count);
		
		if(totalCount==jacketCount+coatCount) {
			
			System.out.println("Total count matches with both catagories");
		}
		else {
			System.out.println("Count is not matched");
		}
		
		//4) To filter Coats
		
		driver.findElementByXPath("(//div[@class='common-checkboxIndicator'])[2]").click();
		
		
		// 5) CLick + More option under Brands and Filter MANGO brand
		
		driver.findElementByXPath("//div[@class='brand-more']").click();
		
		driver.findElementByClassName("FilterDirectory-searchInput").sendKeys("MANGO");
		
		Thread.sleep(500);
		
		driver.findElementByXPath("//span[@class='FilterDirectory-count']/following-sibling::div").click();
		
		Thread.sleep(500);
		
		driver.findElementByXPath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']").click();
		
		Thread.sleep(500);
		
		
		// 6) To get the brand names
		List<WebElement> brands = driver.findElementsByClassName("product-brand");
		 
		for (WebElement eachBrand : brands) {
			
			if(eachBrand.getText().equals("MANGO")) {
				System.out.println("Brand name is confirmed :: "+eachBrand.getText());
			}
			else {
				System.out.println("Brand name is not as expected");
			}	
		}
		 
		// 7) To sort By better discount        		
		Actions action1 = new Actions(driver);
		
		action1.moveToElement(driver.findElementByClassName("sort-sortBy")).perform();
		 
		driver.findElementByXPath("//label[text()='Better Discount']").click();
		
		// 8) To get the prices in list and print the first items price
		List<WebElement> discountPrices = driver.findElementsByClassName("product-discountedPrice");
		
		String price = discountPrices.get(0).getText();
		
		System.out.println("Price of the first listed item is :: "+price);
		
		// 9) To hover on the first item's price
		Actions action2 = new Actions(driver);
		
		action2.moveToElement(driver.findElementByClassName("product-price")).perform();
		 
		driver.findElementByXPath("(//span[text()='wishlist now'])[1]").click();
		
		// 10) Too close the browser
		driver.close();
	}
	
	
	
	

}
