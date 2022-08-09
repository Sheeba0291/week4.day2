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
4. Click On Learning
5. Select SalesForce Certification Under Learnings
6. Choose Your Role as Salesforce Architect
7. Get the Text(Summary) of Salesforce Architect 
8. Get the List of Salesforce Architect Certifications Available
9. Click on Application Architect 
10.Get the List of Certifications available
 */

public class Architect_Certifications {

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

		// 6. Choose Your Role as Salesforce Architect
		driver.findElement(By.xpath("(//a[@class='roleMenu_no-underline'])[2]")).click();

		// 7. Get the Text(Summary) of Salesforce Architect 
		String p1 = driver.findElement(By.xpath("//h1[text()='Salesforce Architect']/following::div")).getText();

		System.out.println(p1);

		// 8. Get the List of Salesforce Architect Certifications Available

		List<WebElement> findElements = driver.findElements(By.xpath("//div[@class='credentials-card_title']/a"));

		System.out.println("*******************List of Salesforce Architect Certifications Available *****************");
		for (int i =0; i<findElements.size(); i++)
		{
			String text = findElements.get(i).getText();
			System.out.println(text);
		}

		// 9. Click on Application Architect 
		driver.findElement(By.linkText("Application Architect")).click();

		//	10.Get the List of Certifications available

		List<WebElement> findElts = driver.findElements(By.xpath("//div[@class='credentials-card_title']/a"));

		System.out.println("*******************List of Certifications Available *****************");

		for (int i =0; i<findElts.size(); i++)
		{
			String text = findElts.get(i).getText();
			System.out.println(text);
		}

	}

}
