package factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import Constants.AppConstants;
import exceptions.FrameworkException;
import io.qameta.allure.Step;


public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public static final org.apache.log4j.Logger log=LogManager.getLogger(DriverFactory.class);
	// for each and every thread it will get local copy of the driver
	@Step("init the driver using properties : {0}")
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");

		System.out.println("Browser Name is" + browserName);
		
		log.info("browser name is "+browserName);
		
		
		
		optionsManager = new OptionsManager(prop);
		highlight = prop.getProperty("highlight");

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			break;

		case "firefox":
			driver = new FirefoxDriver();
			break;

		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			break;

		case "safari":
			driver = new SafariDriver();
			break;

		default:
			
			log.error("pls pass the valid browser name..."+ browserName);
			
	
			System.out.println("pls pass the valid browserName..." + browserName);

			throw new FrameworkException("Invalid browserName");

		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}

	public static WebDriver getDriver() {

		return tlDriver.get();

	}

	// supply env name using maven command line
	// mvn clean install lDenv=qa

	public Properties initProp() throws IOException {

		String envName = System.getProperty("env");
		System.out.println("Running the test suite on env " + envName);
		log.info("Running the test suite on env "+envName);
		FileInputStream ip = null;
		
		prop =new Properties();

		try {
			if (envName == null) {
				System.out.println("No env is passed, hence running the test suite on qa env..");
				ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
			} else {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
					break;

				case "stage":
					ip = new FileInputStream(AppConstants.CONFIG_STAGE_PROP_FILE_PATH);
					break;

				case "dev":
					ip = new FileInputStream(AppConstants.CONFIG_DEV_PROP_FILE_PATH);
					break;

				case "uat":
					ip = new FileInputStream(AppConstants.CONFIG_UAT_PROP_FILE_PATH);
					break;

				case "prod":
					ip = new FileInputStream(AppConstants.CONFIG_PROD_FILE_PATH);
					break;

				default:
					

					throw new FrameworkException("===Invalid Env===");

				}
			}
			prop.load(ip);
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir it will take the ss
		String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}
}
