import java.util.HashMap;
import java.util.List;

import enums.MessageType;
import exception.CannotLoginException;
import exception.CannotSleepInThreadException;
import models.Automate;
import storages.BotAnswers;
import storages.ExcelHelper;
import util.Util;


public class Test {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		//testPropertyFileTest();
		//testAI();
		//testUtil();
		//testLoginAndLikePost();
		//testLoadMoreMessages();
		//Automate.getInstance().close();
		//testReadMessageFromExcel();
		//testDidIAnswer();
	}
	
	private static void testDidIAnswer() {
		String oldMsg = "Thank you. Lisa will soon reach out by text message on the number you have provided.";
		System.out.println(Automate.getInstance().didIAnswer(oldMsg, MessageType.MESSAGE_WITH_A_QUISTION_MARK, "Soap", "010-9230-7740", "Apartment"));
	}

	private static void testReadMessageFromExcel() {
		List<String> answer = ExcelHelper.readMessage(MessageType.MESSAGE_WITH_A_QUISTION_MARK);
		System.out.println(answer);
		answer = ExcelHelper.readMessage(MessageType.MESSAGE_WITH_CLIENT_EMAIL);
		System.out.println(answer);
		answer = ExcelHelper.readMessage(MessageType.MESSAGE_WITH_CLIENT_NUMBER);
		System.out.println(answer);
	}

	private static void testLoadMoreMessages() {
		Util.getLog4j();
		Automate automate = Automate.getInstance();
		try {
			automate.loadMoreMessages();
		} catch (CannotSleepInThreadException e) {
			e.printStackTrace();
		}
	}

	private static void testLoginAndLikePost() {
		String email = "";
		String password = "";
		Util.getLog4j();
		Automate automate = Automate.getInstance();
		try {
			automate.login(email, password);
		} catch (CannotLoginException e) {
			System.out.println(e.getMessage());
			automate.close();
		}

		try {
			for(int i=0;i<10;i++) {
				automate.likePosts(null);
			}
			Util.easySleep(2000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		automate.logout();
	}

	private static void testUtil() {
		int numOfTrue = 0;
		for(int i =0;i<10000;i++) {
			if(Util.doItOrNot(1, Util.getRandomInt(20, 30))) {
				numOfTrue++;
			}
		}
		System.out.println("Did it "+numOfTrue+" out of 10000");
	}

	private static void testAI() {
		//AI test
		HashMap<String, String> personalInfo = Util.fetchDetails("Josean García 407-232-5174 josean_garcia@yahoo.com");
		System.out.println("Name : "+personalInfo.get("name"));
		System.out.println("Number : "+personalInfo.get("number"));
		System.out.println("Email  : "+personalInfo.get("email"));
	}

	private static void testPropertyFileTest() {
		//properties file test
		BotAnswers botAnswers = BotAnswers.getInstance();
		
		String message = "Thank you. Lisa will soon reach out by text message on the number you have provided. Can you help me";
		
		String lastAnswer = BotAnswers.getInstance().getAnswerHasNumber();
		if(message.contains(lastAnswer)) {
			message = message.replace(lastAnswer, "").trim();
		}
		
		String msg = BotAnswers.getInstance().getMailFormToMaintenance();
		msg = String.format(msg, message, "http://www.google.com");
		System.out.println(msg);
	}
	
}
