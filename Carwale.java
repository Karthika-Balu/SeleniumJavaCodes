package day12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Carwale {

	public static void main(String[] args) throws InterruptedException {

		//1) Go to https://www.carwale.com/
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		DesiredCapabilities cap = new DesiredCapabilities(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap); 
		
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.carwale.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		//2) Click on Used
		driver.findElementByXPath("//li[@data-tabs='usedCars']").click();

		//3) Select the City as Chennai
		driver.findElementByXPath("//input[@placeholder='Type to select city name, e.g. Mumbai']").sendKeys("Chennai");
		driver.findElementByXPath("//a[@cityname='chennai,tamilnadu']").click();
		
		//4) Select budget min (8L) and max(12L) and Click Search
		driver.findElementByXPath("//ul[@id='minPriceList']/li[text()='8 Lakh']").click();
		driver.findElementByXPath("//ul[@id='maxPriceList']/li[text()='12 Lakh']").click();
		Thread.sleep(500);
		driver.findElementById("btnFindCar").click();

		//5) Select Cars with Photos under Only Show Cars With
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByName("CarsWithPhotos")));
		driver.findElementByName("CarsWithPhotos").click();

		//6) Select Manufacturer as "Hyundai" --> Creta
		Thread.sleep(100);
		driver.findElementByXPath("//input[@placeholder='Select Manufacturer']").sendKeys("Hyundai");
		Thread.sleep(200);
		driver.findElementByXPath("//li[@data-manufacture-en='Hyundai']").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Creta']")));
		driver.findElementByXPath("//span[text()='Creta']").click();
		
		//7) Select Fuel Type as Petrol
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.scrollBy(0, 500)");
		Thread.sleep(200);
		driver.findElementByXPath("//div[@name='fuel']/h3").click();
		driver.findElementByXPath("//li[@name='Petrol']").click();
		
		//8) Select Best Match as "KM: Low to High"
		Thread.sleep(2000);
		Select bestMatch = new Select(driver.findElementByXPath("//select[@id='sort']"));
		bestMatch.selectByVisibleText("KM: Low to High");
		Thread.sleep(5000);
		
		//9) Validate the Cars are listed with KMs Low to High
		List<WebElement> list_kms = driver.findElementsByXPath("//span[@class='slkms vehicle-data__item']");
		List<Integer> kms_actualList = new ArrayList<Integer>();
		
		for (int i = 0; i < list_kms.size(); i++) {
		
			String text = list_kms.get(i).getText();
			String replaceAll = text.replaceAll("[^0-9]", "");
			int kms = Integer.parseInt(replaceAll);
			kms_actualList.add(kms);
		}
		
		List<Integer> kms_sortedList = new ArrayList<Integer>(kms_actualList);
		Collections.sort(kms_sortedList);
		
		if(kms_actualList==kms_sortedList) {
			System.out.println("The cars are displayed from Low to high Kms");
		}
		else {
			System.out.println("The cars aren't displayed from Low to high Kms");
		}
		
		//10) Add the least KM ran car to Wishlist
		int leastKmsDriven = kms_sortedList.get(0);
		
		for (int i = 0; i < kms_sortedList.size(); i++) {
			
			if(leastKmsDriven==kms_actualList.get(i)) {
				
				Thread.sleep(1000);
				WebElement leastPriceCar = driver.findElementByXPath("(//li[@class='listing-adv listingContent cur-pointer card-list-container']"
						+ "//span[contains(@class,'shortlist-icon')])["+(i+1)+"]");
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", leastPriceCar);
				//leastPriceCar.click();
				break;
			}			
		}

		//11) Go to Wishlist and Click on More Details
		try {
			driver.findElementByLinkText("Don't show anymore tips").click();
		} catch (Exception e) {
			System.out.println("Exception is catched");
		}
		Thread.sleep(2000);
		driver.findElementByXPath("//li[contains(@class,'action-box shortlistBtn')]").click();
		Thread.sleep(200);
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElementByXPath("//a[text()='More details »']")).click().build().perform();
		Thread.sleep(500);
		
		Set<String> windowHandles_set = driver.getWindowHandles();
		List<String> win_list = new ArrayList<>(windowHandles_set);
		driver.switchTo().window(win_list.get(1));
		
		//12) Print all the details under Overview 
		Thread.sleep(3000);
		try {
			driver.findElementByLinkText("Got it").click();
			System.out.println("Clicked Got it in Try block");
		}
		catch(Exception e) {
			System.out.println("Exception is catched");	
		}
		driver.findElementByXPath("//li[text()='Overview']").click();
		
		List<WebElement> overview1 = driver.findElementsByXPath("//div[@id='overview']//li/div[@class='equal-width text-light-grey']");
		List<WebElement> overview2 = driver.findElementsByXPath("//div[@id='overview']//li/div[@class='equal-width dark-text']");
		
		Map<String, String> overviewDetails = new LinkedHashMap<String, String>();
		
		for (int i = 0; i < overview1.size(); i++) {
			
			String key = overview1.get(i).getText();
			String value = overview2.get(i).getText();
			
			overviewDetails.put(key, value);
		}
		System.out.println("Overview Details are:");
		for (Entry<String,String> eachEntry : overviewDetails.entrySet()) {
			System.out.println(eachEntry.getKey()+" --> "+eachEntry.getValue());
		}
		
		//13) Close the browser.
		driver.quit();
	}

}
