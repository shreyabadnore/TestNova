package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Constants.AppConstants;
import Util.ElementUtil;

public class SearchResultsPage {
	
	WebDriver driver;
	private ElementUtil eleUtil;

	
	public SearchResultsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	private By productResults = By.cssSelector("div.product-thumb");

	public int getProductResultsCount() {
		int count= eleUtil.waitForElementsVisible(productResults, AppConstants.SHORT_TIME_OUT).size();
		System.out.println("product result count " + count);
		return count;
		
	}
	
	public ProductInfoPage selectProduct(String productName) {
		
		System.out.println("Product Name is "+ productName);
		
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
		
		
	}
	
	
	
	
	
}
