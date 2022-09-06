package storages;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import enums.MessageType;
import util.Util;

public class BotAnswers {
	private static BotAnswers instance;
	private static final Logger logger = Logger.getLogger(BotAnswers.class);
	public static final String ANSWER_FILE = "answers/answers.properties";
	public static final String KEY_MAIL_TO_AGENT = "mail.toAgent";
	public static final String KEY_MAIL_TO_MAINTENANCE = "mail.toMaintenance";
	
	private List<String> answerHasNumber;
	private List<String> answerHasEmail;
	private List<String> answerHasQuestion;
	private String mailFormToAgent;
	private String mailFormToMaintenance;
	
	private BotAnswers(){
		try {
			setAllAnswers();
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Cannot find "+ANSWER_FILE);
			logger.error(e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot read properties from "+ANSWER_FILE);
			logger.error(e.getMessage());
		}
	}
	
	public static BotAnswers getInstance() {
		if(instance == null) {
			instance = new BotAnswers();
		}
		return instance;
	}
	
	private void setAllAnswers() throws IOException{
		FileReader resource;
		try {
			resource = new FileReader(ANSWER_FILE);
	        Properties properties = new Properties();
        	properties.load(resource);
        	
        	answerHasNumber = ExcelHelper.readMessage(MessageType.MESSAGE_WITH_CLIENT_NUMBER);
        	answerHasEmail = ExcelHelper.readMessage(MessageType.MESSAGE_WITH_CLIENT_EMAIL);
        	answerHasQuestion = ExcelHelper.readMessage(MessageType.MESSAGE_WITH_A_QUISTION_MARK);
        	mailFormToAgent = properties.getProperty(KEY_MAIL_TO_AGENT);
        	mailFormToMaintenance = properties.getProperty(KEY_MAIL_TO_MAINTENANCE);
        	
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("[FileNotFoundException] " + Util.getStackTrace(Thread.currentThread().getStackTrace()));
		} catch (IOException e) {
			throw new IOException("[IOException] " + Util.getStackTrace(Thread.currentThread().getStackTrace()));
		}

		try {
			resource.close();
		} catch (IOException e) {
			throw new IOException("[IOException] " + Util.getStackTrace(Thread.currentThread().getStackTrace()));
		}
	}

	public List<String> getAnswerHasNumberList(){return answerHasNumber;}
	public List<String> getAnswerHasEmailList(){return answerHasEmail;}
	public List<String> getAnswerHasQuestionList(){return answerHasQuestion;}
	public String getAnswerHasNumber() {return answerHasNumber.get(Util.getRandomInt(0, answerHasNumber.size()-1)); }
	public String getAnswerHasEmail() {return answerHasEmail.get(Util.getRandomInt(0, answerHasEmail.size()-1));}
	public String getAnswerHasQuestion() {return answerHasQuestion.get(Util.getRandomInt(0, answerHasQuestion.size()-1));}
	public String getMailFormToAgent() {return mailFormToAgent;}
	public String getMailFormToMaintenance() {return mailFormToMaintenance;}
}
