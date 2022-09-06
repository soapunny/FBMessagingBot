package models.apis;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import exception.WebDriverNotFoundException;
import util.EnvHelper;
import util.Util;

public class ChromeDriverHelper {
	private static ChromeDriverHelper instance;
	private static ChromeDriver conn;
	private static final Logger logger = Logger.getLogger(ChromeDriverHelper.class);
	private final String WEB_DRIVER_ID = EnvHelper.getInstance().getValue("WEB_DRIVER_ID");
	private final String WEB_DRIVER_PATH = EnvHelper.getInstance().getValue("WEB_DRIVER_PATH");
	
	private ChromeDriverHelper() { instance = null; conn = null;}
	
	public static ChromeDriverHelper getInstance() {
		if(instance == null) {
			instance = new ChromeDriverHelper();
		}
		return instance;
	}
	
	public ChromeDriver getConnection() throws WebDriverNotFoundException{
		if(conn == null) {
			try {
				System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
				options.addArguments("--disable-extensions");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				options.setExperimentalOption("prefs", prefs);
				conn = new ChromeDriver(options);
				logger.info("Chrome driver is connected.");
			}catch(Exception e) {
				throw new WebDriverNotFoundException(Util.getStackTrace(Thread.currentThread().getStackTrace()));
			}
		}
		return conn;
	}
	
	public void disconnect() throws WebDriverNotFoundException{
		if(conn != null) {
			try {
				conn.quit();
				conn = null;
				logger.info("Chrome driver is disconnected.");
			}catch(Exception e) {
				throw new WebDriverNotFoundException(Util.getStackTrace(Thread.currentThread().getStackTrace()));
			}
		}
	}
}
