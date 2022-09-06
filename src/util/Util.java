package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.FindElementBy;
import exception.ElementNotFoundException;
import exception.WebDriverNotFoundException;
import models.apis.ChromeDriverHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import exception.CannotSleepInThreadException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Util {
	public static Logger logger = Logger.getLogger(Util.class);
	
	public static void getLog4j() {
		// log4j.properties 파일 불러오고
		FileInputStream log4jRead;
		try {
			log4jRead = new FileInputStream("log/log4j.properties");
			Properties log4jProperty = new Properties (); 
			log4jProperty.load (log4jRead);
			// property 타입으로 읽어서 configure와 연동
			PropertyConfigurator.configure(log4jProperty);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static String getValue(String key){
        return key.split(":")[1].replaceAll("\"", "");
    }
	public static String getValue(String key, String delimiter) {
		return key.split(delimiter)[1];
	}
	public static String getValue(String key, String delimiter, String from, String to) {
		return key.split(delimiter)[1].replaceAll(from, to);
	}
	public static String formatPhoneNumber(String msg) {
		msg = msg.trim();
		String RegexFrom = "(\\d{3})(\\d{3})(\\d{4})";
		String RegexTo = "$1-$2-$3";
		if(!msg.equals("")) {
			msg = msg.replaceAll("\\D+", "");
			if(msg.length() == 10) {
				return msg.replaceFirst(RegexFrom, RegexTo);
			}else if(msg.length() == 11) {
				if(msg.charAt(0) == '1'){
					return msg.substring(1).replaceFirst(RegexFrom, RegexTo);
				}else {
					return msg.substring(0, 10).replaceFirst(RegexFrom, RegexTo);
				}
			}else if(msg.length() > 11) {
				return msg.substring(0, 10).replaceFirst(RegexFrom, RegexTo);
			}
		}
		return "";
	}
	
	public static String fetchEmail(String msg) {
		msg = msg.replaceAll("\n", " ");
        String regex = ".*(\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b).*";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(msg);
        if(m.matches())
        	return m.group(1);
		return "";
	}
	
	public static HashMap<String, String> fetchDetails(String msg){
        HashMap<String, String> map = new HashMap<>();
        map.put("email", "");
        map.put("number", "");
        map.put("name", "");
        msg = msg.replaceAll("\n", " ").replaceAll("  ", " ");
        try {
            URIBuilder ub = new URIBuilder("https://api.wit.ai/message?v=20210929");
            ub.addParameter("q", msg);
            
            Request request = Request.Get(ub.toString());
            request.addHeader("Authorization", "Bearer OWX3QX3UHITVFMFQSRMCUD3BWX4DXM4Q");
            HttpResponse httpResponse = request.execute().returnResponse();
            if (httpResponse.getEntity() != null) {
                String html = EntityUtils.toString(httpResponse.getEntity());
                //System.out.println(html);
                String[] resp = html.split("role");
                for(int i = 0; i < resp.length; i++){
                    if (i != 0){
                        String label = resp[i].split(",")[0];
                        
                        if(label.contains("email") && map.get("email").equals(""))
                            map.put("email", getValue(resp[i].split(",")[3]).replace("\\u0040", "@"));
                        if(label.contains("phone_number") && map.get("number").equals(""))
                            map.put("number", getValue(resp[i].split(",")[7]).replaceAll("\\D+",""));
                        if(label.contains("contact") && map.get("name").equals(""))
                            map.put("name", getValue(resp[i].split(",")[3]));
                        
                    }
                }
            }       
        } catch (IOException ex) {
            //Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
        }
        return map;
	}

	public static boolean doItOrNot(int times, int outOf) {
		int randNum = (int)(Math.random()*outOf);
		if(randNum < times)
			return true;
		return false;
	}
	
	public static int getRandomInt(int from, int to) {
		int randomNum = (int)(Math.random()*(to-from+1)+from);
		return randomNum;
	}
	
	public static int sleepRandom() {
		int randMins = (int)(Math.random()*10);
		logger.info(randMins + " mins wait until next task");
		try {
			Thread.sleep(1000 * 60 * randMins);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return randMins;
	}
	
	public static int sleepRandom(int minMins, int maxMins) {
		int randMins = (int)(Math.random()*(maxMins-minMins+1) + minMins);
		logger.info(randMins + " mins wait until next task");
		try {
			Thread.sleep(1000 * 60 * randMins);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return randMins;
	}
	
	public static void easySleep(int millis) throws CannotSleepInThreadException{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			String errMsg = getStackTrace(Thread.currentThread().getStackTrace());
			throw new CannotSleepInThreadException(errMsg);
		}
	}
	
	public static String getStackTrace(StackTraceElement[] elements) {
		StringBuilder stackTrace = new StringBuilder("");
		
		int trackRange = elements.length > 4 ? 4 : elements.length;
		
		for(int i = 1; i < trackRange; i++) {
			stackTrace.append(String.format("%s.%s[line:%d]",
											elements[i].getClassName(), 
											elements[i].getMethodName(), 
											elements[i].getLineNumber())
							);
			if(i != trackRange-1)
				stackTrace.append(" > ");
		}
		
		return stackTrace.toString();
	}

	public static WebElement findElement(FindElementBy by, String target) throws ElementNotFoundException {
		WebElement element = null;
		try {
			WebDriver driver = ChromeDriverHelper.getInstance().getConnection();

			if(by.equals(FindElementBy.XPATH)) {
				element = driver.findElement(By.xpath(target));
			}else if(by.equals(FindElementBy.NAME)){
				element = driver.findElement(By.name(target));
			}else if(by.equals(FindElementBy.TAG_NAME)){
				element = driver.findElement(By.tagName(target));
			}else if(by.equals(FindElementBy.CLASS)){
				element = driver.findElement(By.className(target));
			}else if(by.equals(FindElementBy.ID)){
				element = driver.findElement(By.id(target));
			}
		}catch(WebDriverNotFoundException e){
			logger.error(e.getMessage());
		}catch(Exception e) {
			throw new ElementNotFoundException(Util.getStackTrace(Thread.currentThread().getStackTrace()));
		}
		return element;
	}

	public static List<WebElement> findElements(FindElementBy by, String target) throws ElementNotFoundException{
		List<WebElement> elements = null;
		try {
			WebDriver driver = ChromeDriverHelper.getInstance().getConnection();

			if(by.equals(FindElementBy.XPATH)) {
				elements = driver.findElements(By.xpath(target));
			}else if(by.equals(FindElementBy.NAME)){
				elements = driver.findElements(By.name(target));
			}else if(by.equals(FindElementBy.TAG_NAME)){
				elements = driver.findElements(By.tagName(target));
			}else if(by.equals(FindElementBy.CLASS)){
				elements = driver.findElements(By.className(target));
			}else if(by.equals(FindElementBy.ID)){
				elements = driver.findElements(By.id(target));
			}
		}catch(WebDriverNotFoundException e){
			logger.error(e.getMessage());
		}catch(Exception e) {
			throw new ElementNotFoundException(Util.getStackTrace(Thread.currentThread().getStackTrace()));
		}
		return elements;
	}
}
