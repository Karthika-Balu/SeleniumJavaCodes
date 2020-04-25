package day7;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Honda {

	public static void main(String[] args) throws InterruptedException {

		// 1) Go to https://www.honda2wheelersindia.com/
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();	
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.honda2wheelersindia.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		/*
		 * Adding Try catch block for handling
		 * intermittent pop-up
		 */
		WebDriverWait wait = new WebDriverWait(driver,30);
		try {
			driver.findElementByXPath("//button[@class='close']").click();
		} catch (NoSuchElementException e) {
			System.out.println("No such pop-up is present in the home page");
		}
		// 2) Click on scooters and click dio
		driver.findElementByLinkText("Scooter").click();
		Thread.sleep(500);
		driver.findElementByXPath("//img[@src='/assets/images/thumb/dioBS6-icon.png']/parent::a").click();
		
		// 3) Click on Specifications and mouseover on ENGINE
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByLinkText("Specifications"))).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByLinkText("ENGINE")));
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElementByLinkText("ENGINE")).build().perform();
		
		// 4) Get Displacement value
		String field_name_dio = driver.findElementByXPath("//div[@class='engine part-2 axx']//li[3]/span[1]").getText();
		String field_value_dio = driver.findElementByXPath("//div[@class='engine part-2 axx']//li[3]/span[2]").getText();
		//String field_value_dio1 = field_name_dio.replaceAll("c", "");
		float dio_displacement = Float.parseFloat(field_value_dio.replaceAll("c",""));
		System.out.println("The "+field_name_dio+" value of dio scooter is = "+field_value_dio);
		
		// 5) Go to Scooters and click Activa 125
		driver.findElementByLinkText("Scooter").click();
		driver.findElementByXPath("//img[@src='/assets/images/thumb/activa-125new-icon.png']/parent::a").click();

		// 6) Click on Specifications and mouseover on ENGINE
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByLinkText("Specifications"))).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByLinkText("ENGINE")));
		Actions action2 = new Actions(driver);
		action2.moveToElement(driver.findElementByLinkText("ENGINE")).build().perform();

		// 7) Get Displacement value
		String field_name_activa = driver.findElementByXPath("//div[@class='engine part-4 axx']//li[3]/span[1]").getText();
		String field_value_activa = driver.findElementByXPath("//div[@class='engine part-4 axx']//li[3]/span[2]").getText();
		//String field_value_activa1 = field_value_activa.replaceAll("c", "");
		float activa_displacement = Float.parseFloat(field_value_activa.replaceAll("c",""));
		System.out.println("The "+field_name_activa+" value of Activa scooter is = "+field_value_activa);

		// 8) Compare Displacement of Dio and Activa 125 and print the Scooter name having better Displacement.
		if(activa_displacement>dio_displacement) {
			System.out.println("Activa 125 have better displacement than dio");
		}
		else if(activa_displacement<dio_displacement) {
			System.out.println("Dio have better displacement than Activa");
		}
		else {
			System.out.println("Both have equal displacement");
		}
		// 9) Click FAQ from Menu 
		driver.findElementByLinkText("FAQ").click();
		
		// 10) Click Activa 125 BS-VI under Browse By Product
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByLinkText("Activa 125 BS-VI")));
		driver.findElementByLinkText("Activa 125 BS-VI").click();
		
		// 11) Click  Vehicle Price 
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//a[contains(text(),' Vehicle Price')]")));
		driver.findElementByXPath("//a[contains(text(),' Vehicle Price')]").click();
		
		// 12) Make sure Activa 125 BS-VI selected and click submit
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//select[@id='ModelID6']")));
		Select vehicleDropDown = new Select(driver.findElementByXPath("//select[@id='ModelID6']"));
		String selectedBrand = vehicleDropDown.getFirstSelectedOption().getText();
		
		if (selectedBrand.equalsIgnoreCase("Activa 125 BS-VI")) {
			System.out.println("selected Vehicle is Activa - 125 BS-VI");
			driver.findElementByXPath("(//button[text()='Submit'])[6]").click();
		} else {
			System.out.println("selected Vehicle is not Activa - 125 BS-VI");
		}
		
		// 13) click the price link
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByLinkText("Click here to know the price of Activa 125 BS-VI."))).click();

		// 14)  Go to the new Window and select the state as Tamil Nadu and  city as Chennai
		Set<String> windowHandles_set = driver.getWindowHandles();
		List<String> windowHandles_list = new ArrayList<>(windowHandles_set);
		driver.switchTo().window(windowHandles_list.get(1));
		
		Select stateSelection = new Select(driver.findElementById("StateID"));
		Select citySelection = new Select(driver.findElementById("CityID"));
		
		stateSelection.selectByVisibleText("Tamil Nadu");
		Thread.sleep(500);
		citySelection.selectByVisibleText("Chennai");
		
		// 15) Click Search
		driver.findElementByXPath("//button[text()='Search']").click();
		
		// 16) Print all the 3 models and their prices
		/* First row has 3 cells, Hence taking values from Cell2 and Cell3
		 * remaining rows has only 2 cells for model and Price
		 * */
		WebElement table = driver.findElementByXPath("//table[@id='gvshow']/tbody");
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 0; i < rows.size(); i++) {
			String model, price;
			List<WebElement> cell = rows.get(i).findElements(By.tagName("td"));
			if(i==0) {
				model = cell.get(1).getText();
				price = cell.get(2).getText();
			}
			else {
				model = cell.get(0).getText();
				price = cell.get(1).getText();
			}
			System.out.println("Model -> "+model+" Price -> "+price);
		}

		// 17) Close the Browser
		driver.quit();

	}

}
