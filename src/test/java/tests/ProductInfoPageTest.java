package tests;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Constants.AppConstants;
import Util.ExcelUtil;
import base.BaseTest;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetup() {
		homePage = loginPage.doLogin("septbatch2024@open.com", "Selenium@12345");

	}

	@Test(dataProvider="getProductData")
	public void productSearchHeaderTest(String searchKey, String productName) {
		searchResultPage = homePage.doSearch(searchKey);
		productInfoPage=searchResultPage.selectProduct(productName);
		String actualProductHeader=productInfoPage.getProductHeader();
		
		Assert.assertEquals(actualProductHeader, productName);

	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
		};
	}
	
	@Test(dataProvider="getProductImageSheetData")
	public void productImagesCountTest(String searchKey, String productName, String expectedImageCount) {
		searchResultPage = homePage.doSearch(searchKey);
		productInfoPage=searchResultPage.selectProduct(productName);
		int actualImagesCount=productInfoPage.getProductImagesCount();
		
		Assert.assertEquals(actualImagesCount,  Integer.parseInt(expectedImageCount));
		
	}
	
	@DataProvider
	public Object[][] getProductImageData() {
		
		return new Object[][] {
			{"macBook", "MacBook Pro", 4},
			{"macBook", "MacBook Air", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7}
			
		};
		
	}
	
	@DataProvider
	public Object[][] getProductImageSheetData() throws InvalidFormatException, IOException {
		Object productData[][]=ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		
		return productData;
		
	}
	
	@Test
	public void productInfoTest() {
		searchResultPage=homePage.doSearch("macBook");
		productInfoPage=searchResultPage.selectProduct("MacBook Pro");
		Map<String,String> productInfoMap=productInfoPage.getProductInfo();
	productInfoMap.forEach((k,v)-> System.out.println(k+" "+v));
	SoftAssert softAssert=new SoftAssert();//creating the object of the spftassert class
	
	
	softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
	softAssert.assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
	softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
	softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
	softAssert.assertEquals(productInfoMap.get("price"), "$2,000.00");
	softAssert.assertEquals(productInfoMap.get("extax"), "$2,000.00");
	
	softAssert.assertAll();
	
	
	}

}
;