package day13;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class Shiksha {

	public static void main(String[] args) throws InterruptedException {

		// 1) Go to https://studyabroad.shiksha.com/
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		DesiredCapabilities cap = new DesiredCapabilities(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap); 
		
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://studyabroad.shiksha.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// 2) Mouse over on Colleges and click MS in Computer Science &Engg under MS Colleges
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElementByXPath("//label[@for='sm3' and text()='Colleges ']")).build().perform();
		
		driver.findElementByLinkText("MS in Computer Science &Engg").click();
		
		// 3) Select GRE under Exam Accepted and Score 300 & Below 
		driver.findElementByXPath("//p[text()='GRE']/preceding-sibling::span").click();
		Thread.sleep(500);
		Select score = new Select(driver.findElementByXPath("//li[@class='labelInputs active']//select[@class='score-select-field']"));
		score.selectByVisibleText("300 & below");
		Thread.sleep(1000);
		
		// 4) Max 10 Lakhs under 1st year Total fees, USA under countries
		driver.findElementByXPath("//p[text()='Max 10 Lakhs']/preceding-sibling::span").click();
		Thread.sleep(500);
		
		// 5) Select Sort By: Low to high 1st year total fees
		Select sortBy = new Select(driver.findElementById("categorySorter"));
		sortBy.selectByVisibleText("Low to high 1st year total fees");
		Thread.sleep(2000);
		
		// 6) Click Add to compare of the College having least fees with Public University, Scholarship and Accomadation facilities
		try {
			driver.findElementByXPath("//div[@class='cokkie-box']//a[text()='OK']").click();
			System.out.println("Accepted Cookies");
		} catch (Exception e) {
			System.out.println("catched exception");
		}
			
		/*List<WebElement> facilityList = driver.findElementsByXPath("(//div[@class='uni-course-details flLt']/div[3]//span)");
		for (int i = 1; i <= facilityList.size(); i=(i+3)) {
			
			String tick1 = driver.findElementByXPath("(//div[@class='uni-course-details flLt']/div[3]//span)["+i+"]").getText();
			String tick2 = driver.findElementByXPath("(//div[@class='uni-course-details flLt']/div[3]//span)["+(i+1)+"]").getText();
			String tick3 = driver.findElementByXPath("(//div[@class='uni-course-details flLt']/div[3]//span)["+(i+2)+"]").getText();
			//String tick1 = driver.findElementByXPath("(//div[@class='uni-course-details flLt']/div[3]//span)["+(i+3)+"]").getText();
			if(tick1.equals("✔") && tick2.equals("✔") && tick3.equals("✔")) {
				
				System.out.println("3 facilities are ticked ");
				
				List<WebElement> eleAdd = driver.findElementsByXPath("//p[text()='Add to compare']/preceding-sibling::span");
				WebElement eleAddtoCompare = eleAdd.get(i-1);
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("arguments[0].click();", eleAddtoCompare);
				//driver.findElementByXPath("(//p[text()='Add to compare']/preceding-sibling::span)["+i+"]").click();
			}
			else {
				System.out.println("All 3 facilities are not ticked");
			}
		}*/
		
		List<WebElement> eleUniv = driver.findElementsByXPath("//p[text()='Public university']/span");
		System.out.println("university size is "+eleUniv.size());
		List<WebElement> eleScholar = driver.findElementsByXPath("//p[text()='Scholarship']/span");
		System.out.println("Scholarship size is "+eleScholar.size());
		List<WebElement> eleAccomodation = driver.findElementsByXPath("//p[text()='Accommodation']/span");
		System.out.println("Accomodation size is "+eleAccomodation.size());
		for(int i=0; i<eleUniv.size();i++) {
			if(eleUniv.get(i).getAttribute("class").contains("tick")&&eleScholar.get(i).getAttribute("class").contains("tick")&&eleAccomodation.get(i).getAttribute("class").contains("tick")) {
				List<WebElement> eleAdd = driver.findElementsByXPath("//p[contains(text(),'compare')]/preceding-sibling::span");
				Thread.sleep(2000);
				WebElement eleAddtoCompare = eleAdd.get(i);
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("arguments[0].click();", eleAddtoCompare);
				}
		}
		
		// 7) Select the first college under Compare with similar colleges 
		
		// 8) Click on Compare College>
		Thread.sleep(2000);
		driver.findElementByXPath("//div[@class='compare-col flLt']/a[contains(@class,'button')]").click();
		
		// 9) Select When to Study as 2021
		Thread.sleep(3000);
		driver.findElementByXPath("(//strong[text()='2021']/preceding::span)[10]").click();
		Thread.sleep(500);
		
		// 10) Select Preferred Countries as USA
		driver.findElementByXPath("//div[text()='Preferred Countries']/following-sibling::div").click();
		Thread.sleep(200);
		driver.findElementByXPath("//li[@class='ctry-row']//label[contains(@for,'USA')]/span").click();
		
		// 11) Select Level of Study as Masters
		Thread.sleep(200);
		driver.findElementByXPath("//label[contains(@for,'Masters')]/span").click();
		
		// 12) Select Preferred Course as MS
		Thread.sleep(200);
		driver.findElementByXPath("//div[text()='Preferred Course']/following-sibling::div").click();
		Thread.sleep(200);
		driver.findElementByXPath("//div[@class='city-lr prefCourse']//li[text()='MS']").click();
		
		// 13) Select Specialization as "Computer Science & Engineering"
		Thread.sleep(500);
		driver.findElementByXPath("//div[text()='Preferred Specialisations']/following-sibling::div").click();
		driver.findElementByXPath("//li[text()='Computer Science & Engineering']").click();
		
		// 14) Click on Sign Up
		WebElement signUp = driver.findElementById("signup");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", signUp);
		
		// 15) Print all the warning messages displayed on the screen for missed mandatory fields
		List<WebElement> warningMsg = driver.findElementsByXPath("//div[@class='helper-text' and contains(text(),'Please')]");
		
		for (WebElement warning : warningMsg) {
			
			String warningText = warning.getText();
			System.out.println("The warning messages are :: "+warningText);
		}
		
		// 14) Close the Browser
		driver.quit();
	}

}
