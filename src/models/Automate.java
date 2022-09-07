package models;

import enums.AnswerReferences;
import enums.DialogType;
import enums.FindElementBy;
import enums.MessageType;
import exception.*;
import models.apis.ChromeDriverHelper;
import models.apis.Email;
import storages.BotAnswers;
import storages.DBHelper;
import storages.ExcelHelper;
import ui.Dialog;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.EnvHelper;
import util.TmpValueStorage;
import util.Util;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Automate {
	private static Automate instance;
	
	public static final Logger logger = LogManager.getLogger(Automate.class);
	private List<WebElement> messages;
	private ArrayList<String> links = new ArrayList<>();
	private ArrayList<String> names = new ArrayList<>();


	private Automate() {}
	public static Automate getInstance() {
		if(instance == null) {
			instance = new Automate();
		}
		return instance;
	}
	private void goTo(String link) throws CannotConnectToLinkException{
		if(link == null || link.equals(""))
			throw new CannotConnectToLinkException(Util.getStackTrace(Thread.currentThread().getStackTrace()));
		
		WebDriver driver;
		try {
			driver = ChromeDriverHelper.getInstance().getConnection();
			driver.get(link);
			logger.debug("Bot is in : "+link);
			Util.easySleep(Util.getRandomInt(5000, 10000));
		}catch (WebDriverNotFoundException | CannotSleepInThreadException e) {
			logger.error(e.getMessage());
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot connect to the link : "+link);
		}
	}
	
	public boolean login(String email, String password) throws CannotLoginException{
		boolean login = false;
		try {
			final String FACEBOOK_MOBILE_LINK = EnvHelper.getInstance().getValue("FACEBOOK_MOBILE_LINK");
			final String ENGLISH_ELEMENT_XPATH = EnvHelper.getInstance().getValue("ENGLISH_ELEMENT_XPATH");
			final String LOGIN_FAIL_ELEMENT_XPATH = EnvHelper.getInstance().getValue("LOGIN_FAIL_ELEMENT_XPATH");
			final String INCORRECT_PASS_ELEMENT_XPATH = EnvHelper.getInstance().getValue("INCORRECT_PASS_ELEMENT_XPATH");
			final String EMAIL_ELEMENT_NAME = EnvHelper.getInstance().getValue("EMAIL_ELEMENT_NAME");
			final String PASSWORD_ELEMENT_NAME = EnvHelper.getInstance().getValue("PASSWORD_ELEMENT_NAME");
			final String LOGIN_BTN_NAME = EnvHelper.getInstance().getValue("LOGIN_BTN_NAME");
			final String LOGIN_CODE_ELEMENT_NAME = EnvHelper.getInstance().getValue("LOGIN_CODE_ELEMENT_NAME");

			goTo(FACEBOOK_MOBILE_LINK);
			
			try {
				Util.findElement(FindElementBy.XPATH, ENGLISH_ELEMENT_XPATH).click();
				logger.debug("Go to English(US) page");
				Util.easySleep(3000);
			}catch(ElementNotFoundException e) {
				logger.debug("Already in English (USA) page");
			}

			Util.findElement(FindElementBy.NAME, EMAIL_ELEMENT_NAME).sendKeys(email);
			Util.easySleep(2000);
			Util.findElement(FindElementBy.NAME, PASSWORD_ELEMENT_NAME).sendKeys(password);
			Util.easySleep(2000);

			Util.findElement(FindElementBy.NAME, LOGIN_BTN_NAME).click();
			Util.easySleep(Util.getRandomInt(5000, 10000));
			
			try {
				Util.findElement(FindElementBy.XPATH, LOGIN_FAIL_ELEMENT_XPATH);
			}catch(ElementNotFoundException e) {
				try {
					Util.findElement(FindElementBy.XPATH, INCORRECT_PASS_ELEMENT_XPATH);
				}catch(ElementNotFoundException e2) {
					Dialog loginCntDown = null;
					try {
						Util.findElement(FindElementBy.NAME, LOGIN_CODE_ELEMENT_NAME);
						//2-way-factor login
						loginCntDown = new Dialog("2-way-factor-login", "remainingTime", DialogType.TIMER_MESSAGE, 60);
						loginCntDown.setAlwaysOnTop(true);
						loginCntDown.setVisible(true);
						while(loginCntDown.getRemainingTime() > 0) {
							Util.findElement(FindElementBy.NAME, LOGIN_CODE_ELEMENT_NAME);
							Util.easySleep(100);
						}
					}catch(ElementNotFoundException e3) {
		//LOGIN SUCCESS
						if(loginCntDown != null) {
							loginCntDown.setRemainingTime(0);
						}
						logger.info("[Logged In Facebook]");
						return true;
					}
				}
			}
		//LOGIN FAILS
			throw new ElementNotFoundException(Util.getStackTrace(Thread.currentThread().getStackTrace()));
			
		}catch(CannotSleepInThreadException | CannotConnectToLinkException e) {
			logger.error(e.getMessage());
		}catch(ElementNotFoundException e) {
			throw new CannotLoginException(e.getMessage());
		}

		JOptionPane.showMessageDialog(null, "Facebook login failed. Try again.");
		return login;
	}

	public boolean logout() {
		//TODO add logout function
		close();
		logger.info("[Logout from Facebook]");
		return false;
	}

	public boolean isLoggedIn() {
		boolean isSignIn = false;
		try {
			final String FACEBOOK_MOBILE_LINK = EnvHelper.getInstance().getValue("FACEBOOK_MOBILE_LINK");
			goTo(FACEBOOK_MOBILE_LINK);

			final String LOGIN_STATUS_CHECK_ELEMENTS_XPATH = EnvHelper.getInstance().getValue("LOGIN_STATUS_CHECK_ELEMENTS_XPATH");

			List<WebElement> elements = Util.findElements(FindElementBy.XPATH, LOGIN_STATUS_CHECK_ELEMENTS_XPATH);
			if(elements.size() > 0){
				logger.info("STATUS: LOGIN");
				isSignIn = true;
			}else{
				logger.info("STATUS: LOGOUT");
			}

		} catch (CannotConnectToLinkException | ElementNotFoundException e) {
			logger.error(e.getMessage());
		}

		return isSignIn;
	}

	public void browsePosts(JLabel log) {
		try {
			log.setText("Browsing Posts");
			final String FACEBOOK_MOBILE_LINK = EnvHelper.getInstance().getValue("FACEBOOK_MOBILE_LINK");
			goTo(FACEBOOK_MOBILE_LINK);

			final String RANDOM_HOME_ANCHOR_ELEMENTS_XPATH = EnvHelper.getInstance().getValue("RANDOM_HOME_ANCHOR_ELEMENTS_XPATH");
			List<WebElement> elements = Util.findElements(FindElementBy.XPATH, RANDOM_HOME_ANCHOR_ELEMENTS_XPATH);

			int randNum = (int)(Math.random()*elements.size());
			elements.get(randNum).click();
			logger.debug("Clicked : "+elements.get(randNum));
			Util.easySleep(Util.getRandomInt(5000, 10000));

		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void likePosts(JLabel log) throws WebDriverNotFoundException, ElementNotFoundException{
		//likes
		if(!Util.doItOrNot(1, Util.getRandomInt(10, 20)))
			return;
		
		try {
			log.setText("Like Posts");
			final String FACEBOOK_MOBILE_LINK = EnvHelper.getInstance().getValue("FACEBOOK_MOBILE_LINK");
			goTo(FACEBOOK_MOBILE_LINK);

			final String LIKE_ELEMENTS_XPATH = EnvHelper.getInstance().getValue("LIKE_ELEMENTS_XPATH");
			List<WebElement> elements = Util.findElements(FindElementBy.XPATH, LIKE_ELEMENTS_XPATH);

			int randNum = (int)(Math.random()*elements.size());
			elements.get(randNum).click();
			logger.info("Bot likes a post");
			Util.easySleep(Util.getRandomInt(5000, 10000));
		
		} catch(CannotSleepInThreadException | CannotConnectToLinkException e) {
			logger.error(e.getMessage());
		}
	}

	private void gotoVideoPost(){
		try{
			final String FACEBOOK_VIDEO_LINK = EnvHelper.getInstance().getValue("FACEBOOK_VIDEO_LINK");
			goTo(FACEBOOK_VIDEO_LINK);

			//Comment videos(on/off)
			final String COMMENT_ELEMENTS_XPATH = EnvHelper.getInstance().getValue("COMMENT_ELEMENTS_XPATH");
			List<WebElement> elements = Util.findElements(FindElementBy.XPATH, COMMENT_ELEMENTS_XPATH);
			int randNum = (int)(Math.random()*elements.size());
			elements.get(randNum).click();
			Util.easySleep(Util.getRandomInt(5000, 10000));

		} catch (CannotSleepInThreadException | CannotConnectToLinkException | ElementNotFoundException e) {
			logger.error(e.getMessage());
		}
	}

	public void commentVideo(JLabel log) {
		if(!Util.doItOrNot(1, Util.getRandomInt(10, 20)))
			return;
		
		try {
			log.setText("Leave a comment");
			logger.info("CommentVideos");

			gotoVideoPost();

			final String COMMENT_INPUT_ELEMENT_ID = EnvHelper.getInstance().getValue("COMMENT_INPUT_ELEMENT_ID");
			WebElement element = Util.findElement(FindElementBy.ID, COMMENT_INPUT_ELEMENT_ID);
			
			String[] comments = new String[] {
					"I love it!!", "Yeah, That's it.", "That's my boy.", "My favorite.",
					"Omg>.<", "Yesss :)", "I'm your big fan."
			};
			int randomCommentsIdx = Util.getRandomInt(0, comments.length-1);
			element.sendKeys(comments[randomCommentsIdx]);
			Util.easySleep(Util.getRandomInt(2000, 3000));

			final String COMMENT_POST_ELEMENT_XPATH = EnvHelper.getInstance().getValue("COMMENT_POST_ELEMENT_XPATH");
			Util.findElement(FindElementBy.XPATH, COMMENT_POST_ELEMENT_XPATH).click();
			Util.easySleep(Util.getRandomInt(5000, 10000));
	        logger.debug("Comment completed");

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot comment a video.");
			logger.error("[Cannot comment a video error] " + e.getMessage());
		}
	}

	public void watchVideo(JLabel log) {
		//if(!Util.doItOrNot(1, Util.getRandomInt(10, 20))) return;
		
		try {
			log.setText("Watch a video");
			gotoVideoPost();

			/* play the video.
			Util.findElement(By.xpath("//div[contains(@data-sigil, 'm-video-play-button')]")).click();
			logger.info("--Bot is watching a video");
			Util.easySleep(Util.getRandomInt(6000, 8000));
			*/
			/* like the video.
			if(Util.doItOrNot(1, 3)) {
				Util.findElement(By.xpath("//a[contains(text(), 'Like')]")).click();
				log.setText("Bot likes a video");
				logger.info("--Bot liked a video");
				Util.easySleep(Util.getRandomInt(2000, 3000));
			}
			*/
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot watch a video");
			logger.error("[Cannot watch a video error]\n" + e.getMessage());
		}
	}

	public void loadMoreMessages() throws CannotSleepInThreadException{
        //opening for new message
        
        try{
			final String FACEBOOK_MESSAGES_LINK = EnvHelper.getInstance().getValue("FACEBOOK_MESSAGES_LINK");
            goTo(FACEBOOK_MESSAGES_LINK);//Connect to Facebook messages
			Util.easySleep(Util.getRandomInt(3000, 5000));

			final String LOAD_MORE_MESSAGE_ELEMENT_XPATH = EnvHelper.getInstance().getValue("LOAD_MORE_MESSAGE_ELEMENT_XPATH");
			Util.findElement(FindElementBy.XPATH, LOAD_MORE_MESSAGE_ELEMENT_XPATH).click(); // fetch more messages.
            Util.easySleep(Util.getRandomInt(4000, 6000));
			Util.findElement(FindElementBy.XPATH, LOAD_MORE_MESSAGE_ELEMENT_XPATH).click(); // fetch more messages.
            Util.easySleep(Util.getRandomInt(4000, 6000));
			Util.findElement(FindElementBy.XPATH, LOAD_MORE_MESSAGE_ELEMENT_XPATH).click(); // fetch more messages.
            Util.easySleep(Util.getRandomInt(4000, 6000));
			Util.findElement(FindElementBy.XPATH, LOAD_MORE_MESSAGE_ELEMENT_XPATH).click(); // fetch more messages.
            Util.easySleep(Util.getRandomInt(4000, 6000));
			Util.findElement(FindElementBy.XPATH, LOAD_MORE_MESSAGE_ELEMENT_XPATH).click(); // fetch more messages.
        }catch(ElementNotFoundException e) {//If there is no new Message.
            logger.debug("No more message to fetch or no 'touchable primary' element");
        } catch (CannotConnectToLinkException e) {
			logger.error(e.getMessage());
		}
    	Util.easySleep(Util.getRandomInt(2000, 5000));
	}

	private String getItemIdFrom(String link) throws CannotSleepInThreadException, ElementNotFoundException, CannotConnectToLinkException{
		goTo(link);//Move to the 1:1 message link
		Util.easySleep(Util.getRandomInt(3000, 5000));

		final String MARKETPLACE_ITEM_ANCHOR_XPATH = EnvHelper.getInstance().getValue("MARKETPLACE_ITEM_ANCHOR_XPATH");
		String itemId;
		itemId = Util.findElement(FindElementBy.XPATH, MARKETPLACE_ITEM_ANCHOR_XPATH).getAttribute("href");
		itemId = itemId.split("refid")[0];
		itemId = itemId.replaceAll("[^0-9]", "");//remove all except itemId(number) from a href link
		
		return itemId;
	}

	private void findNewMessageLinksAndNames(){
		//find <a> which contains ) or ·
		//FindElementByXpath("//a[contains(text(), ')') and contains(text(), '·')]");
		links = new ArrayList<>();
		names = new ArrayList<>();
		
		//property info in name
		List<WebElement> elements = null;

		try{
			final String UNREAD_MESSAGE_ELEMENTS_XPATH = EnvHelper.getInstance().getValue("UNREAD_MESSAGE_ELEMENTS_XPATH");
			elements = Util.findElements(FindElementBy.XPATH, UNREAD_MESSAGE_ELEMENTS_XPATH);
		}catch(ElementNotFoundException e){
			logger.info("No unreadMessage class");
		}

		try{
			if(elements != null && elements.size() > 0){//fetching links and name
				for(WebElement element : elements) {
					String messageName = element.findElement(By.xpath(".//strong")).getAttribute("innerHTML");
					if(messageName.contains("·") && messageName.contains("(")) {
						names.add(messageName);// add property info
						links.add(element.getAttribute("href")); // add the 1:1 message link
					}
				}
			}
		}catch( Exception e){
			logger.info(e.getMessage());
		}

		logger.info("Total new message : "+ links.size());
		logger.debug("links : "+links);
		logger.debug("names : "+names);

	}

	private void answerMsg(String customerMsg, String totalMessage, String name, String itemId, String href, ResultSet record) throws SQLException, CannotSleepInThreadException, ElementNotFoundException{
		HashMap<String, String> response = Util.fetchDetails(customerMsg);//get name, email, phone num from messages
		String email = response.get("email");
		String phoneNumber = Util.formatPhoneNumber(response.get("number"));
		String customerName = (response.get("name").contentEquals("")) ? name.split("·")[0].trim() : response.get("name");
		customerMsg = customerMsg.toLowerCase();
		String mailText;
		String botAnswer;
		String agentName = record.getString("agent_name");
		String agentNumber = record.getString("agent_number");
		String subject = record.getString("subject");
		//End get msg text
	
		//msg has email
		if(!email.contentEquals("")) {
			//send email
			if(record.getString("email").contentEquals("")) {//in case no agent email
				logger.debug("No data in db.");
				return;
			}
			//Send Email to an agent
			logger.debug("Message has email : "+email);
			if(!phoneNumber.contentEquals("")) {//Email + Number
				logger.debug("Message has phone number : " + phoneNumber);
				botAnswer = BotAnswers.getInstance().getAnswerHasNumber().replace(AnswerReferences.AGENT_NAME.toString(), agentName)
																		.replace(AnswerReferences.AGENT_NUMBER.toString(), agentNumber)
																		.replace(AnswerReferences.PROPERTY_SUBJECT.toString(), subject);
			}else {//Email
				botAnswer = BotAnswers.getInstance().getAnswerHasEmail().replace(AnswerReferences.AGENT_NAME.toString(), agentName)
																		.replace(AnswerReferences.AGENT_NUMBER.toString(), agentNumber)
																		.replace(AnswerReferences.PROPERTY_SUBJECT.toString(), subject);
			}
			sendMessage(botAnswer);
			ExcelHelper.saveMessage(name, customerMsg);// save the client's info in Excel
			
			mailText = String.format(BotAnswers.getInstance().getMailFormToAgent(), customerName, phoneNumber, email);
			//Email it to responsible agent.
			Email.send(record.getString("subject"), record.getString("email"), mailText);
		}
		//MSG has only phone number
		else if(!phoneNumber.contentEquals("")) {
			//send mail
			if(record.getString("email").contentEquals("")) {
				logger.debug("No data in db");
				return;
			}
			//Send Email to an agent
			logger.debug("Message has phone number : " + phoneNumber);
			mailText = String.format(BotAnswers.getInstance().getMailFormToAgent(), customerName, phoneNumber, "-");
			Email.send(record.getString("subject"), record.getString("email"), mailText);
			
			botAnswer = BotAnswers.getInstance().getAnswerHasNumber().replace(AnswerReferences.AGENT_NAME.toString(), agentName)
																	.replace(AnswerReferences.AGENT_NUMBER.toString(), agentNumber)
																	.replace(AnswerReferences.PROPERTY_SUBJECT.toString(), subject);
			sendMessage(botAnswer);
			ExcelHelper.saveMessage(name, customerMsg);
		}
		//itembased msg or keywordbased msg
		else {
			//Get message by keyword
			String message = DBHelper.getKeywordBasedMessage(itemId, customerMsg);
			//Did I answer same message before?
			boolean didIAnswer = true;
			if(message == null || message.trim().equals("")) {//Item based Message
				didIAnswer = false;
				logger.debug("Not answering message");
			}else {//tag based Message
				//try {
					if( !totalMessage.contains((message.contains("\n") ? message.split("\n")[0].toLowerCase() : message.toLowerCase())) ){
						didIAnswer = false;
						logger.debug("answer needed message");
					}else
						logger.debug("I answered it before(no need to answer)");
			}
			
			if(message != null && !message.contentEquals("")) {
				logger.debug("Tag based message");
				if(!didIAnswer)
					sendMessage(message);
			}else {//item based msg
				message = DBHelper.getTitleBasedMessage(itemId, customerMsg);
				logger.debug("Item based message");
				if(message != null && !message.equals("")) {
					if(!totalMessage.contains((message.contains("\r\n") ? message.split("\r\n")[0].toLowerCase() : message.toLowerCase())) )
						sendMessage(message);
				}else {
					//check if question
					if(customerMsg.contains("?") && !record.getString("agent_name").contentEquals("")
						&& !record.getString("agent_number").contentEquals("")) {
						//check if this msg has already been sent or not
						String oldMsgs = "";
						for(WebElement msg : messages) {
							oldMsgs = oldMsgs.concat(msg.getText().concat(". "));
						}
						
						String answerFormat = BotAnswers.getInstance().getAnswerHasQuestion();
						if(!didIAnswer(oldMsgs, MessageType.MESSAGE_WITH_A_QUISTION_MARK, agentName, agentNumber, subject)) {
							botAnswer = answerFormat.replace(AnswerReferences.AGENT_NAME.toString(), agentName)
													.replace(AnswerReferences.AGENT_NUMBER.toString(), agentNumber)
													.replace(AnswerReferences.PROPERTY_SUBJECT.toString(), subject);
							sendMessage(botAnswer);
						}
					}else {
						CheckHardToReplyMessage(customerMsg, href);
					}
				}
			}
		}
		
	}
	
	public boolean didIAnswer(String oldMessage, MessageType messageType, String agentName, String agentNumber, String subject){
		boolean didIAnswer = false;
		List<String> orgMsgs = null;
		String newMsg;
		
		switch(messageType) {
		case MESSAGE_WITH_CLIENT_NUMBER:
			orgMsgs = BotAnswers.getInstance().getAnswerHasNumberList();
			break;
		case MESSAGE_WITH_CLIENT_EMAIL:
			orgMsgs = BotAnswers.getInstance().getAnswerHasEmailList();
			break;
		case MESSAGE_WITH_A_QUISTION_MARK:
			orgMsgs = BotAnswers.getInstance().getAnswerHasQuestionList();
			break;
		}
		
		for(String orgMsg : orgMsgs) {
			newMsg = orgMsg.replace(AnswerReferences.AGENT_NAME.toString(), agentName)
							 	  	  .replace(AnswerReferences.AGENT_NUMBER.toString(), agentNumber)
							 	  	  .replace(AnswerReferences.PROPERTY_SUBJECT.toString(), subject);
			System.out.println(newMsg);
			if(oldMessage.contains(newMsg.split("\n")[0])) {
				return true;
			}
		}
		
		return didIAnswer;
	}

	private String getCustomerMsg(String name) throws InterruptedException{
		String customerMsg = "";
		int totalMsgs = messages.size();
		int startFrom;
		int newMsgsCount;
		
        newMsgsCount = Integer.parseInt(name.split("\\(")[1].split("\\)")[0]);//(number) -> number
		startFrom = totalMsgs - newMsgsCount;//unread 1:1 message start index
		startFrom = Math.max(0, startFrom);

		for(int j = startFrom; j < messages.size(); j++) {
			customerMsg = customerMsg.concat( messages.get(j).getText().concat(". "));
		}
	    
	    return customerMsg.trim();
	}

	private void seeOld1to1Messages() {
        try{
			final String LOAD_MORE_1_TO_1_MESSAGES_ELEMENT_XPATH = EnvHelper.getInstance().getValue("LOAD_MORE_1_TO_1_MESSAGES_ELEMENT_XPATH");
        	while(true) {
				Util.findElement(FindElementBy.XPATH, LOAD_MORE_1_TO_1_MESSAGES_ELEMENT_XPATH).click(); // fetch more messages.
        		Util.easySleep(Util.getRandomInt(3000, 5000));
        	}
        }catch(Exception ex){//If there is no new Message.
        	logger.debug("No more old 1:1 Messages");
        }
	}
	private void CheckHardToReplyMessage(String message, String href) {
		if(message.contains("do") || message.contains("how") || message.contains("util") || message.contains("is")
			 || message.contains("yet") || message.contains("allow") || message.contains("can") || message.contains("does")
			 || message.contains("available") || message.contains("did")|| message.contains("what")|| message.contains("why")
			 || message.contains("where") || message.contains("when")) {
			
			//In case the message has the bot's last answer either.
			String lastAnswer = BotAnswers.getInstance().getAnswerHasNumber();
			if(message.contains(lastAnswer)) {
				message = message.replace(lastAnswer, "").trim();
			}
			
			String msg = BotAnswers.getInstance().getMailFormToMaintenance();
			msg = String.format(msg, message, href);
			
			Email.send("Need to reply the Facebook message", "nick.k@smartland.com", msg);
			Email.send("Need to reply the Facebook message", "victor.f@smartland.com", msg);
			//Email.send("Need to reply the Facebook message", "soapunny@gmail.com", msg);
		}
	}

	public boolean isTimeToSleep(String sleepTime, String wakeUpTime) {
		Date date_now = new Date(System.currentTimeMillis());
		SimpleDateFormat curTimeFormat = new SimpleDateFormat("HHmm");
		String curTime = curTimeFormat.format(date_now);
		
		if(wakeUpTime.compareTo(sleepTime) <= 0) {//wakeUpTime - sleepTime
			return sleepTime.compareTo(curTime) <= 0 || curTime.compareTo(wakeUpTime) < 0;
		}else {//sleepTime - wakeUpTime
			return sleepTime.compareTo(curTime) <= 0 && curTime.compareTo(wakeUpTime) < 0;
		}
	}

	private void sendMessage(String message) throws CannotSleepInThreadException, ElementNotFoundException{
		Util.findElement(FindElementBy.NAME, "body").sendKeys(message);
		Util.easySleep(Util.getRandomInt(5000, 10000));
		Util.findElement(FindElementBy.NAME, "send").click();
		Util.easySleep(Util.getRandomInt(2000, 5000));
	}

	public void close() {
		try {
			ChromeDriverHelper.getInstance().disconnect();
		} catch (WebDriverNotFoundException e) {
			logger.error(e.getMessage());
		}
	}


	public void doMessage(JLabel log, TmpValueStorage<Boolean> isThreadOn) throws WebDriverNotFoundException, ElementNotFoundException{
		if(!isThreadOn.getValue()) return;
		//if(Util.doItOrNot(1, 3)) return;

		final String FACEBOOK_MOBILE_LINK = EnvHelper.getInstance().getValue("FACEBOOK_MOBILE_LINK");
		logger.info("##Start Messaging##");

		//Move to Facebook messages and fetch messages
		try {
			loadMoreMessages();
		} catch (CannotSleepInThreadException e1) {
			logger.error(e1.getMessage());
		}

		//Get new message link
		findNewMessageLinksAndNames();

		for(int i =0;i<links.size();i++) {
			try {
				String itemId;
				try {
					itemId = getItemIdFrom(links.get(i));
				}catch(Exception e) {
					logger.error(e.getMessage());
					Util.easySleep(Util.getRandomInt(4000, 8000));
					continue;
				}

				logger.debug("Item id : "+itemId);
				ResultSet record = DBHelper.getRecordByTitle(itemId);//get record about property by ID

				//Click [See Older 1:1 Messages]
				//seeOld1to1Messages();

				//Get messages between the bot and client.
				messages = new ArrayList<>();
				String ONE_TO_ONE_MESSAGES_ELEMENTS_XPATH = EnvHelper.getInstance().getValue("ONE_TO_ONE_MESSAGES_ELEMENTS_XPATH");
				messages = Util.findElements(FindElementBy.XPATH, ONE_TO_ONE_MESSAGES_ELEMENTS_XPATH);

				//Get unread customer messages
				String customerMsg = getCustomerMsg(names.get(i));
				if(customerMsg.contentEquals("")) {//in case no new message
					log.setText("Cannot get customer msg");
					continue;
				}

				//Get totalMessage(unread + read) just in case.
				String totalMessage = "";
				for(WebElement msg : messages){
					totalMessage = totalMessage.concat(msg.getText().concat(". "));
				}
				totalMessage = totalMessage.toLowerCase();
				//logger.debug("totalMessage : "+totalMessage);

				//answer the message.
				answerMsg(customerMsg, totalMessage, names.get(i), itemId, links.get(i), record);

			} catch(InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Error: "+e.getMessage());
			} catch (SQLException e) {
				logger.fatal(e.getMessage());
			} catch(Exception e) {
				logger.error(e.getMessage());
			} finally{
				try {
					Util.easySleep(Util.getRandomInt(5000, 10000));
				} catch (CannotSleepInThreadException e1) {
					logger.error(e1.getMessage());
				}
			}
			if(!isThreadOn.getValue())
				return;
		}
		try {
			goTo(FACEBOOK_MOBILE_LINK);//return to the init messages link
		}catch (CannotConnectToLinkException e) {
			logger.error(e.getMessage());
		}

	}
}
