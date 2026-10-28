package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;

import Constants.AppConstants;
import Util.ElementUtil;
import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public LoginPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	// 1. By locator" page objects

	private By emailId = By.id("input-email");
	private By pass = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");

	private By forgotPwdLink = By.linkText("Forgotten Password");

	// 2. Public page actions- methods feature
	
	
	@Step("Get Login Page Title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleContains(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("This is login page title " + title);

		ChainTestListener.log("Login Page Title" + title);
		return title;
	}
	@Step("Get Login Page url")
	public String getLoginPageURL() {

		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);

		System.out.println("This is url " + url);
		return url;

	}
	
	@Step("ForogtPwdLink displayed")

	public boolean isForogtPwdLinkExist() {
		return eleUtil.doIsElementDisplayed(forgotPwdLink);
	}
	
	@Step("login iwht username{0} and password {1}")

	public HomePage doLogin(String userName, String password) {
		System.out.println("App crednetials are ===>" + userName + " :" + password);
		eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_TIME_OUT).sendKeys(userName);
		eleUtil.waitForElementVisible(pass, AppConstants.SHORT_TIME_OUT).sendKeys(password);
		eleUtil.doClick(loginButton);
		return new HomePage(driver);

	}

}
