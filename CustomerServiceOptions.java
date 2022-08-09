package week4.day2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sukgu.Shadow;

/*
1. Launch Salesforce application https://login.salesforce.com/
2. Login with Provided Credentials
3. Click on Learn More link in Mobile Publisher
4. Clilck on Products and Mousehover on Service 
5. Click on Customer Services
6. Get the names Of Services Available 
7. Verify the title
 */

public class CustomerServiceOptions {

	public static void main(String[] args) throws InterruptedException {
		// Setup WebDriverManager
		WebDriverManager.chromedriver().setup();

		// To disable the browser notification
		ChromeOptions options = new ChromeOptions();

		options.addArguments("-disable-notifications");

		// Create the chromedriver object named driver
		ChromeDriver driver = new ChromeDriver(options);

		// 1. Launch Salesforce application https://login.salesforce.com/
		driver.get("https://login.salesforce.com/");

		// Maximize the window
		driver.manage().window().maximize();

		// 2. Login with username as "ramkumar.ramaiah@testleaf.com " 
		driver.findElement(By.id("username")).sendKeys("ramkumar.ramaiah@testleaf.com");

		// password as "Password$123"
		driver.findElement(By.id("password")).sendKeys("Password$123");

		// click on the login button
		driver.findElement(By.id("Login")).click();

		// Implicit wait for the web elements
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 3. Click on Learn More link in Mobile Publisher
		driver.findElement(By.xpath("(//span[@class=' label bBody'])[2]")).click();

		// Switch to the next window using Windowhandles.
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> listOfWindowHandles = new ArrayList<String>(windowHandles);

		driver.switchTo().window(listOfWindowHandles.get(1));

		// Click confirm on Confirm redirect
		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		// Interact with Shadow DOM
		Shadow dom = new Shadow(driver);

		// 4. Clilck on Products  
		dom.findElementByXPath("//span[text()='Products']").click();

		// Mousehover on Service 
		WebElement service = dom.findElementByXPath("//span[text()='Service']");

		Actions builder = new Actions(driver);
		builder.moveToElement(service).perform();

		// 5. Click on Customer Services
		WebElement customerService = dom.findElementByXPath("//a[text()='Customer Service']");

		driver.executeScript("arguments[0].click();", customerService);

		// 6. Get the names Of Services Available

		List<WebElement> findElements = driver.findElements(By.xpath("//div[@class='caption']//h2"));

		System.out.println("Names Of Services Available are as Below : ");

		for (int i =0; i<findElements.size(); i++)
		{
			String text = findElements.get(i).getText();
			System.out.println(text);	
		}

		// 7. Verify the title
		String title = driver.getTitle();

		if (title.contains("Customer Service"))
		{
			System.out.println("*******************Page Title is Verified******************");
			System.out.println(title);
		}
		else
		{
			System.out.println("*****Page Title MIS-MATCH********");
		}

	}

}
