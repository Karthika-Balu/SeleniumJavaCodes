package day17.evaluation2;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Azure {

	public static void main(String[] args) throws InterruptedException {

		// 1) Go to https://azure.microsoft.com/en-in/
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		DesiredCapabilities cap = new DesiredCapabilities(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap); 
		
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://azure.microsoft.com/en-in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// 2) Click on Pricing
		driver.findElementByXPath("//a[text()='Pricing']").click();
		
		// 3) Click on Pricing Calculator
		driver.findElementByXPath("//a[contains(text(),'Pricing calculator')]").click();
		
		// 4) Click on Containers
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement container = driver.findElementByXPath("//button[text()='Containers']");
		js.executeScript("arguments[0].click();", container);
		
		// 5) Select Container Instances
		driver.findElementByXPath("(//span[text()='Container Instances'])[3]").click();
		
	    // 6) Click on Container Instance Added View
		WebDriverWait wait = new WebDriverWait(driver,30);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()='View']"))).click();
			System.out.println("Clicked View");
		} catch (Exception e) {
			System.out.println("Exception is catched when View try click");
		}
		
		//	7) Select Region as "South India"
		Select regionSelect = new Select(driver.findElementByName("region"));
		regionSelect.selectByVisibleText("South India");
		
		// 8) Set the Duration as 180000 seconds
		driver.findElementByXPath("//h5[text()='Duration']/parent::div//input[@aria-label='Seconds']").clear();
		driver.findElementByXPath("//h5[text()='Duration']/parent::div//input[@aria-label='Seconds']").sendKeys("180000");
		
		// 9) Select the Memory as 4GB
		Select memorySelect = new Select(driver.findElementByName("memory"));
		memorySelect.selectByVisibleText("4 GB");
		
		// 10) Enable SHOW DEV/TEST PRICING
		WebElement toggleDevPricing = driver.findElementByXPath("//span[text()='Show Dev/Test Pricing']");
		js.executeScript("arguments[0].click();", toggleDevPricing);
		
		// 11) Select Indian Rupee  as currency
		Thread.sleep(1500);
		Select currencySelect = new Select(driver.findElementByXPath("//select[@class='select currency-dropdown']"));
		currencySelect.selectByValue("INR");
		
		// 12) Print the Estimated monthly price
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("(//div[@class='row row-size1 column estimate-total']//span[@class='numeric']/span)[2]")));
		String estMonthlyCost = driver.findElementByXPath("(//div[@class='row row-size1 column estimate-total']//span[@class='numeric']/span)[2]").getText();
		System.out.println("The estimated Monthly Cost is :: "+estMonthlyCost);
		
		// 13) Click on Export to download the estimate as excel
		driver.findElementByXPath("//button[contains(@class,'export-button')]").click();
		Thread.sleep(2000);
		
		// 14) Verify the downloded file in the local folder
		String fileName = "ExportedEstimate";
		String filePath = "C:\\Users\\hp\\Downloads\\"+fileName+".xlsx";
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
			System.out.println("File Download is validated and also deleted the downloaded entry");
		}
		else {
			System.out.println("File is not exists in the downloaded path");
		}
		
		// 15) Navigate to Example Scenarios and Select CI/CD for Containers
		Thread.sleep(2000);
		WebElement exScenarios = driver.findElementByXPath("//a[text()='Example Scenarios']");
		js.executeScript("arguments[0].click();", exScenarios);
		Thread.sleep(1000);
		WebElement ciCdContainer = driver.findElementByXPath("//span[text()='CI/CD for Containers']");
		wait.until(ExpectedConditions.elementToBeClickable(ciCdContainer));
		js.executeScript("arguments[0].click()",ciCdContainer);
		
		// 16) Click Add to Estimate
		WebElement addEstimate = driver.findElementByXPath("//button[text()='Add to estimate']");
		wait.until(ExpectedConditions.elementToBeClickable(addEstimate));
		js.executeScript("arguments[0].click()",addEstimate);
		
		// 17) Change the Currency as Indian Rupee
		Thread.sleep(500);
		Select currencySelect1 = new Select(driver.findElementByXPath("//select[@class='select currency-dropdown']"));
		currencySelect1.selectByValue("INR");
		
		// 18) Enable SHOW DEV/TEST PRICING
		Thread.sleep(500);
		driver.findElementByXPath("//span[text()='Show Dev/Test Pricing']").click();
		
		// 19) Export the Estimate
		driver.findElementByXPath("//button[contains(@class,'export-button')]").click();
		Thread.sleep(2000);
		
		// 20) Verify the downloded file in the local folder
	
		if(file.exists()) {
			file.delete();
			System.out.println("File Download is validated for CI/CD Container and also deleted the downloaded entry");
		}
		else {
			System.out.println("File for CI/CD Container is not exists in the downloaded path");
		}
		
		driver.quit();
		
	}

}
