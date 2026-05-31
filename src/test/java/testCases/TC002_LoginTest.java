package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{

	@Test(groups={"Sanity", "Master"})
	public void verfy_login() {
		logger.info("*** Starting TC002_LoginTest ***");
		try {
		//Home page
		HomePage hp = new HomePage(driver);
		hp.clickMyaccount();
		hp.clickLogin();
		
		//Login
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//My account page
		
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExists();
		//macc.clickLogout();
		Assert.assertEquals(targetPage, true, "Login failed");
		}catch(Exception e) {
			Assert.fail();
		}
		logger.info("*** Finished TC002_Login ***");
		
	}
	
	
}
