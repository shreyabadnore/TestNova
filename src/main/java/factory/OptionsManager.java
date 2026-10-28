package factory;

import java.util.Properties;

import org.apache.log4j.LogManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

public class OptionsManager {
	
	private Properties prop;
	
	private ChromeOptions co;
	private EdgeOptions ed;
	
	public static final org.apache.log4j.Logger log=LogManager.getLogger(DriverFactory.class);
	
	public OptionsManager(Properties prop) {
		this.prop=prop;
	}
	
public ChromeOptions getChromeOptions() {
	co=new ChromeOptions();
	if(Boolean.parseBoolean(prop.getProperty("headless"))) {
		co.addArguments("--headless");
		System.out.println("You are running in the headless mode");
		
		log.info("Headless mode");
		
	}
	if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
		co.addArguments("--incognito");
		System.out.println("You are running in the incognito mode");
		
	}
	return co;
}

public EdgeOptions getEdgeOptions() {
	ed=new EdgeOptions();
	if(Boolean.parseBoolean(prop.getProperty("headless"))) {
		ed.addArguments("--headless");
		
	}
	if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
		ed.addArguments("--incognito");
		
	}
	return ed;
}
}
