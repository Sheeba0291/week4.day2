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
2. Login with username as "ramkumar.ramaiah@testleaf.com " and password as "Password$123"
3. Click on Learn More link in Mobile Publisher
4. Click confirm on Confirm redirect
5. Click Resources and mouse hover on Learning On Trailhead
6. Clilck on Salesforce Certifications
6. Click on Ceritification Administrator
7. Navigate to Certification - Administrator Overview window
8. Verify the Certifications available for Administrator Certifications should be displayed
 */

public class Administer_Certifications {

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

		// 4. Click confirm on Confirm redirect
		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		// Interact with Shadow DOM
		Shadow dom = new Shadow(driver);

		// 5. Click Learning 
		dom.findElementByXPath("//span[text()='Learning']").click();

		// mouse hover on Learning On Trailhead
		WebElement learningByTrailhead = dom.findElementByXPath("//span[text()='Learning on Trailhead']");

		Actions builder = new Actions(driver);
		builder.moveToElement(learningByTrailhead).perform();

		// 6. Click on Salesforce Certifications
		WebElement salesForce = dom.findElementByXPath("//a[text()='Salesforce Certification']");

		driver.executeScript("arguments[0].click();", salesForce);

		// 7. Click on Ceritification Administrator
		driver.findElement(By.linkText("Administrator")).click();

		// 8. Navigate to Certification - Administrator Overview window
		String title = driver.getTitle();
		System.out.println("Navigated to "+title);	

		// 9. Verify the Certifications available for Administrator Certifications should be displayed
		List<WebElement> findElements = driver.findElements(By.xpath("//div[@class='trailMix-card-body_title']/a"));

		boolean flag = true;

		for (int i =0; i<findElements.size(); i++)
		{
			String text = findElements.get(i).getText();
			if(!text.contains("Administrator"))
			{
				flag = false;
			}	
		}

		if (flag)
		{
			System.out.println("Certifications available for Administrator Certifications are displayed !!!");
		}
		else
		{
			System.out.println("Certifications available for Administrator Certifications are NOT displayed !X!X!");
		}

	}

}
