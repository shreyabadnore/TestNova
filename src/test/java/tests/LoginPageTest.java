package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.chaintest.plugins.ChainTestListener;
import Constants.AppConstants;
import Constants.AppError;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic 100: design login Page from open cart")
@Story("various feature for open kart")
@Feature("Feature 50: Login page feature")
public class LoginPageTest extends BaseTest {
	@Description("Checking the login page title...")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Shreya")
	@Test
	public void loginPageTitleTest() {
		ChainTestListener.log("Verifying login page title");

		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);

	}

	@Description("Checking the login page url...")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
		String actualURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR);

	}
	@Description("Checking the login page link...")
	@Severity(SeverityLevel.NORMAL)
	@Test

	public void forgotPassLinkExistTest() {
		Assert.assertTrue(loginPage.isForogtPwdLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	@Description("Checking the user is able to login with right credentials...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);

	}
	

	@Description("checking page logo....")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void logoTest() {
		Assert.assertTrue(commonsPage.isLogoDisplayed(), AppError.LOGO_NOT_FOUND_ERROR);
	}
	
	
	@DataProvider
	public Object[][] getFooterData() {
		return new Object[][] {
			{"About Us"},
			{"Contact Us"},
			{"Specials"},
			{"Order History"}
		};
	}
	
	@Description("checking page footers....")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getFooterData")
	public void footerTest(String footerLink) {
		Assert.assertTrue(commonsPage.checkFooterLink(footerLink));
	}

}
