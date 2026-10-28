package pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Constants.AppConstants;
import Util.ElementUtil;

public class ProductInfoPage {

	WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

	public String getProductHeader() {
		String header = eleUtil.doElementGetText(productHeader);
		System.out.println("product header" + header);
		return header;
	}

	public int getProductImagesCount() {
		int imageCount = eleUtil.waitForElementsPresence(productImages, AppConstants.SHORT_TIME_OUT).size();
		System.out.println(getProductHeader() + "Image count = " + imageCount);
		return imageCount;

	}

	public Map<String,String> getProductInfo() {
		productMap = new HashMap<String, String>();
		productMap.put("header", getProductHeader());
		productMap.put("Images Count", getProductImagesCount()+"");
		getProductMetaData();
		getProductMetaData();
		return productMap;
	}

	public void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForElementsPresence(productMetaData, AppConstants.SHORT_TIME_OUT);
		for (WebElement e : metaList) {
			String metaText = e.getText();
			String[] meta = metaText.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);

		}
	}

	public void getProductPriceData() {
		List<WebElement> priceList = eleUtil.waitForElementsPresence(productPriceData, AppConstants.SHORT_TIME_OUT);

		String productPrice = priceList.get(0).getText().trim();
		String productExTax = priceList.get(1).getText().split(":")[1].trim();

		productMap.put("price", productPrice);
		productMap.put("extax", productExTax);

	}

}
