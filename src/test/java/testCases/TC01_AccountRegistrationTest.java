package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC01_AccountRegistrationTest extends BaseClass{

	
	@Test(groups={"Regression", "Master"})
	public void verfy_account_registration() {
		logger.info("****Starting TC01_AccountRegistrationTest****");
		
		try {
		HomePage hp = new HomePage(driver);
		hp.clickMyaccount();
		logger.info("Clicked on My Account Link");
		
		hp.clickRegister();
		logger.info("Clicked on Register Link");
		
		AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
		logger.info("Providing customer details");
		regPage.setFirstName("John");
		regPage.setLastName("David");
		regPage.setEmail(randomString()+"@gmail.com");
		regPage.setTelephone(randomNumber());
		
		String password = randomAlphNumeric();
		regPage.setPassword(password);
		regPage.setConfiremPassword(password);
		
		regPage.setPrivacyPlicy();
		regPage.clickContinue();
		
		logger.info("Validating expected message");
		String confmag = regPage.getConfirmationMsg();
		if(confmag.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
			
		}
		else {
			logger.error("Test failed...");
			logger.debug("Debog log...");
			Assert.assertFalse(false);
			
		}
		}
		catch(Exception e) {
			
			Assert.fail();
		}
	}
	
}