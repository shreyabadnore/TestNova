package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Constants.AppConstants;
import Constants.AppError;
import base.BaseTest;

public class HomePageTest extends BaseTest {

	@BeforeClass
	public void homePageSetUp() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}

	@Test

	public void homePageTitle() {
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);

	}

	@Test

	public void homePageURLTest() {
		Assert.assertTrue(homePage.getHomePageURL().contains(AppConstants.HOME_PAGE_URL_FRACTION),
				AppError.URL_NOT_FOUND_ERROR);

	}

	@Test
	public void logoutLinkExistsTest() {
		Assert.assertTrue(homePage.isLogoutLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);
	}

	@Test
	public void headersTest() {
		List<String> actualHeaders = homePage.getHeadersList();
		System.out.println("home page headers: ==>" + actualHeaders);

	}
	@DataProvider
	public Object[][] getSearchtData() {
		return new Object[][] {
			{"macbook",3},
			{"imac", 1},
			{"Samsung", 2},
			{"Canon", 1},
			{"Airtel", 0}
		};
		
		
	}
	
	
	
	
	

	@Test(dataProvider="getSearchtData")
	public void searchTest(String searchKey, int count) {
		searchResultPage = homePage.doSearch(searchKey);

		Assert.assertEquals(searchResultPage.getProductResultsCount(), count);
	}

}
