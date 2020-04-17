package day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class MakeMyTrip {
	
	@Test
	public void roomBooking() throws InterruptedException {
		
		//1) Go to https://www.makemytrip.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();	
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		//2) Click Hotels
		driver.findElementByLinkText("Hotels").click();
		
		//3) Enter city as Goa, and choose Goa, India
		driver.findElementByXPath("//span[text()='City / Hotel / Area / Building']").click();
		driver.findElementByXPath("//input[@placeholder='Enter city/ Hotel/ Area/ Building']").sendKeys("Goa");
		
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//p[text()='Goa, India']"))).click();
		
		//4) Enter Check in date as Next month 15th (May 15) and Check out as start date+5
		driver.findElementById("checkin").click();
		String checkin_date = driver.findElementByXPath("(//div[text()='15'])[2]").getText();
		driver.findElementByXPath("(//div[text()='15'])[2]").click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Select Checkout Date']")));
		driver.findElementByXPath("(//div[text()='20'])[2]").click();
		
		//5) Click on ROOMS & GUESTS and click 2 Adults and one Children(age 12). Click Apply Button.
		driver.findElementById("guest").click();
		driver.findElementByXPath("//li[@data-cy='adults-2']").click();
		driver.findElementByXPath("//li[@data-cy='children-1']").click();
		
		Select ageSelect = new Select(driver.findElementByClassName("ageSelectBox"));
		ageSelect.selectByVisibleText("12");
		driver.findElementByXPath("//button[text()='APPLY']").click();
		
		//6) Click Search button
		driver.findElementById("hsw_search_button").click();
		System.out.println("clicked search button");
		Thread.sleep(1500);
		
		//7) Select locality as Baga
		driver.findElementByXPath("//body[contains(@class,'overlayWholeBlack')]").click();
		WebElement locality = driver.findElementByXPath("//input[@type='checkbox' ]/following-sibling::label[text()='Baga']");
		locality.click();
		Thread.sleep(500);
		
		//8) Select 5 start in Star Category under Select Filters
		driver.findElementByXPath("//input[@type='checkbox' ]/following-sibling::label[text()='5 Star']").click();
		String parentWindow = driver.getWindowHandle();
		
		//9) Click on the first resulting hotel and go to the new window
		driver.findElementByXPath("(//p[@id='hlistpg_hotel_name'])[1]").click();
		
		Set<String> windowHandles_set = driver.getWindowHandles();
		List<String> windowHandles_list	= new ArrayList<>(windowHandles_set);
		driver.switchTo().window(windowHandles_list.get(1));
		
		//10) Print the Hotel Name 
		String hotelName = driver.findElement(By.id("detpg_hotel_name")).getText();
		System.out.println("Hotel name is -- "+hotelName);
		
		//11) Click MORE OPTIONS link and Select 3Months plan and close
		driver.findElementByXPath("(//span[contains(text(),'MORE OPTIONS')])[1]").click();
		driver.findElementByXPath("//td[text()='3']/following-sibling::td/span").click();
		driver.findElementByClassName("close").click();;
		Thread.sleep(500);
		
		//12) Click on BOOK THIS NOW
		driver.findElementByXPath("//a[text()='BOOK THIS NOW']").click();
		
		//13) Print the Total Payable amount
		String totalAmt = driver.findElementById("revpg_total_payable_amt").getText();
		System.out.println("Total Payable amount is -- "+totalAmt);
		
		//14) Close the browser
		driver.close();
		
	}


}
