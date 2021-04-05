package StepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import Util.ConfigReader;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ZeroBank_Steps {


	private WebDriver driver;
	private ConfigReader configReader;
	Properties prop;

	@Before(order = 0)
	public void getProperty() {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
	}

	@Before(order = 1)
	public void launchBrowser() {
		String browser = prop.getProperty("browser");

		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equals("safari")) {
			driver = new SafariDriver();
		} else {
			System.out.println("Please pass the correct browser value: " + browser);
		}
	}

	@After(order = 0)
	public void quitBrowser() {
		driver.quit();
	}

	@After(order = 1)
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			// take screenshot:
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", screenshotName);

		}
	}

    /** To Wait Until Element to be Visible */
    public void explicitWaitvisibilityOfElementLocated(By element, long timeInSeconds) {
        WebDriverWait elementToBeVisible = new WebDriverWait(driver, timeInSeconds);
        elementToBeVisible.until(ExpectedConditions.visibilityOfElementLocated(element));
    }


	@Given("the user logins ZeroBank")
	public void the_user_logins_zero_bank() {
		// Write code here that turns the phrase above into concrete actions
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://zero.webappsecurity.com/index.html");
		driver.findElement(By.xpath("//button[@id='signin_button']")).click();
		driver.findElement(By.xpath("//input[@id='user_login']")).sendKeys("username");
		driver.findElement(By.xpath("//input[@id='user_password']")).sendKeys("password");
		driver.findElement(By.xpath("//input[@value='Sign in']")).click();



	}



	@When("the user is on ZeroBank Home Page")
	public void the_user_is_on_zero_bank_home_page() throws InterruptedException {
    	driver.navigate().refresh();
    	driver.get("http://zero.webappsecurity.com/index.html");
    	Assert.assertTrue(driver.findElement(By.xpath("//li[@id='homeMenu']//strong")).isDisplayed());

		// Write code here that turns the phrase above into concrete actions
	}
	@Then("the user navigates to Transfer Funds")
	public void the_user_navigates_to_transfer_funds() {
		driver.findElement(By.xpath("//li[@id='onlineBankingMenu']//strong")).click();
		driver.findElement(By.xpath("//span[text()='Transfer Funds']")).click();
		// Write code here that turns the phrase above into concrete actions
	}
	@Then("the user Account details to transfer")
	public void the_user_account_details_to_transfer() {
    	Select fromAccount = new Select(driver.findElement(By.xpath("//select[@name='fromAccountId']")));
    	fromAccount.selectByValue("1");

		Select toAccount = new Select(driver.findElement(By.xpath("//select[@name='toAccountId']")));
		toAccount.selectByValue("1");

		driver.findElement(By.xpath("//input[@name='amount']")).sendKeys("10");
		driver.findElement(By.xpath("//input[@name='description']")).sendKeys("Test Automation");

		// Write code here that turns the phrase above into concrete actions
	}
	@Then("the user clicks on Continue Button")
	public void the_user_clicks_on_continue_button() {
		driver.findElement(By.xpath("//button[@id='btn_submit']")).click();

    }
	@Then("the user verifies the data and Submit")
	public void the_user_verifies_the_data_and_submit() {
		driver.findElement(By.xpath("//button[@id='btn_submit']")).click();

	}
	@Then("the user verifies transcation message")
	public void the_user_verifies_transcation_message() {
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert alert-success']")).isDisplayed());
	}
	@Then("the user logs out")
	public void the_user_logs_out() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();",driver.findElement(By.xpath("//a[@id='logout_link']")));
	}

	@Then("the user navigates to PayBill")
	public void the_user_navigates_to_pay_bill() {
		driver.findElement(By.xpath("//li[@id='onlineBankingMenu']//strong")).click();
		driver.findElement(By.xpath("//span[@id='pay_bills_link']")).click();

		driver.findElement(By.xpath("//li[@id='pay_bills_tab']")).click();

	}



	@Then("the user clicks on Add New Payee Tab")
	public void the_user_clicks_on_add_new_payee_tab() {
		driver.findElement(By.xpath("//a[text()='Add New Payee']")).click();
    }
	@Then("the user fills all the details")
	public void the_user_fills_all_the_details() {
		explicitWaitvisibilityOfElementLocated(By.xpath("//input[@id='np_new_payee_name']"),15);
		driver.findElement(By.xpath("//input[@id='np_new_payee_name']")).sendKeys("Test Payee");
		driver.findElement(By.xpath("//textarea[@id='np_new_payee_address']")).sendKeys("Test Payee Name");

    }
	@Then("the user verifies successful message")
	public void the_user_verifies_successful_message() {

    	//wantedly throwing error message to verify screenshot
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='alert_content']")).isDisplayed());
    }

}
