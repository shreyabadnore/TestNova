package base;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;

import factory.DriverFactory;
import pages.CommonsPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductInfoPage;
import pages.SearchResultsPage;


//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	WebDriver driver;
	
	DriverFactory df;
	
	protected Properties prop;
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected SearchResultsPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected CommonsPage commonsPage;
	
	@Parameters({"browser"})
	
	@BeforeTest
	public void setup(String browserName) throws IOException  {
		{
			df=new DriverFactory(); 
			prop=df.initProp();
			
			if(browserName!=null) {
				prop.setProperty("browser", browserName);
				
			}
			prop.getProperty("browser");
			driver=df.initDriver(prop);
			loginPage=new LoginPage(driver);
			commonsPage=new CommonsPage(driver);
			
		     ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
		        ChainPluginService.getInstance().addSystemInfo("Headless#", prop.getProperty("headless"));
		        ChainPluginService.getInstance().addSystemInfo("Incognito#", prop.getProperty("incognito"));
		        ChainPluginService.getInstance().addSystemInfo("Owner#", "Shreya Badnore");
			
			
		}
	} 

	
	@AfterMethod
	public void attachScreenShot(ITestResult result) {
		
		if(!result.isSuccess()) {
			//ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
			//ChainTestListener.embed(DriverFactory.getScreenshotByte(), "image/png");
		ChainTestListener.embed(DriverFactory.getScreenshotBase64(), "image/png");
		}
	}
	

	
	@AfterTest
	public void tearDown() {
		driver.quit();
		
	}

}
