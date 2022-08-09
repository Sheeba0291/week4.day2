package week4.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
1) Go to https://www.nykaa.com/
2) Mouseover on Brands and Search L'Oreal Paris
3) Click L'Oreal Paris
4) Check the title contains L'Oreal Paris(Hint-GetTitle)
5) Click sort By and select customer top rated
6) Click Category and click Hair->Click haircare->Shampoo
7) Click->Concern->Color Protection
8)check whether the Filter is applied with Shampoo
9) Click on L'Oreal Paris Colour Protect Shampoo
10) GO to the new window and select size as 175ml
11) Print the MRP of the product
12) Click on ADD to BAG
13) Go to Shopping Bag 
14) Print the Grand Total amount
15) Click Proceed
16) Click on Continue as Guest
17) Check if this grand total is the same in step 14
18) Close all windows
 */

public class Nykaa {

	public static void main(String[] args) throws InterruptedException {

		// Setup WebDriverManager
		WebDriverManager.chromedriver().setup();

		// Launch ChromeDriver
		ChromeDriver driver = new ChromeDriver();

		// Go to https://www.nykaa.com/
		driver.get("https://www.nykaa.com/");

		// Maxiize the window
		driver.manage().window().maximize();

		// Mouseover on Brands 
		WebElement brands = driver.findElement(By.xpath("//a[text()='brands']"));

		Actions builder = new Actions(driver);

		builder.moveToElement(brands).perform();

		// Search L'Oreal Paris
		driver.findElement(By.id("brandSearchBox")).sendKeys("Loreal Paris");

		// Click L'Oreal Paris
		driver.findElement(By.linkText("L'Oreal Paris")).click();

		// Check if the title contains L'Oreal Paris(Hint-GetTitle)
		if(driver.getTitle().contains("L'Oreal Paris"))
		{
			System.out.println("Page Title is verified for L'Oreal Paris");
			System.out.println("Title of the Page is : "+driver.getTitle());
		}
		else
		{
			System.out.println("Page Title MIS-MATCH");
		}

		// Click sort By 
		driver.findElement(By.xpath("//button[@class=' css-n0ptfk']")).click();

		// Select customer top rated
		driver.findElement(By.xpath("//span[text()='customer top rated']/following::div[1]")).click();

		// Click Category and click Hair->Click haircare->Shampoo
		driver.findElement(By.id("first-filter")).click();

		// Click Hair
		driver.findElement(By.xpath("//span[text()='Hair']/following::span[1]")).click();

		// Click Haircare
		driver.findElement(By.xpath("//span[text()='Hair Care']/following::span[1]")).click();

		// Select Shampoo
		driver.findElement(By.xpath("//label[@for='checkbox_Shampoo_316']/div[2]")).click();

		// Click->Concern->Color Protection
		driver.findElement(By.xpath("//span[text()='Concern']/following::div[1]")).click();

		// Select Color Protection
		driver.findElement(By.xpath("//label[@for='checkbox_Color Protection_10764']/div[2]")).click();

		// check whether the Filter is applied with Shampoo
		/*
		 * Get the list of Filters (webElements)
		 * Loop through it, get the text and verify if it contains Shampoo
		 */
		List<WebElement> findElements = driver.findElements(By.xpath("//span[@class='filter-value']"));

		for (int i=0; i<findElements.size(); i++)
		{
			if(findElements.get(i).getText().contains("Shampoo"))
			{
				System.out.println("Filter is applied with Shampoo");
				break;
			}
			else
			{
				System.out.println("Shampoo is not included");
			}
		}

		// Click on L'Oreal Paris Colour Protect Shampoo
		driver.findElement(By.xpath("//div[@class='css-xrzmfa']")).click();

		// Go to the new window 
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> list = new ArrayList<String>(windowHandles);

		driver.switchTo().window(list.get(1));

		// select size as 175ml
		Select size = new Select(driver.findElement(By.xpath("//select[@class='css-2t5nwu']")));
		size.selectByVisibleText("175ml");

		// Print the MRP of the product
		String price = driver.findElement(By.xpath("(//span[@class='css-1jczs19'])[1]")).getText();
		price = price.replaceAll("\\D", "");

		// convert string to int
		System.out.println("Price of the Product is : "+price);
		int mrp = Integer.valueOf(price);

		// Click on ADD to BAG
		driver.findElement(By.xpath("(//span[text()='Add to Bag'])[1]")).click();

		// Go to Shopping Bag
		driver.findElement(By.xpath("//button[@class='css-g4vs13']")).click();
		 
		// switch to Frame
		driver.switchTo().frame(0);

		// Print the Grand Total amount
		String text = driver.findElement(By.xpath("(//div[@class='value medium-strong'])[1]")).getText();

		text = text.replaceAll("\\D", "");
		System.out.println("Grand Total is : "+text);
		
		// Click Proceed
		driver.findElement(By.xpath("//span[text()='Proceed']")).click();

		driver.switchTo().defaultContent();

		// Click on Continue as Guest
		driver.findElement(By.xpath("//button[text()='CONTINUE AS GUEST']")).click();

		// Check if this grand total is the same in step 14
		String total = driver.findElement(By.xpath("(//div[@class='value'])[2]")).getText();
		total = total.replaceAll("\\D", "");
		System.out.println("Amount to be Paid : "+total);

		// convert string to int
		int grandTotal = Integer.parseInt(total);

		// Declare and initialize the variable - shipping charge
		int shippingCharge = 70;

		// Verify the price
		if ((mrp+shippingCharge)==grandTotal)
		{
			System.out.println("GrandTotal is verified.");
		}
		else
		{
			System.out.println("Total MIS-MATCH");
		} 

		// Close all windows
		driver.quit();

	}

}
