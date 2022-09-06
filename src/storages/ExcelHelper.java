package storages;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import enums.MessageType;
import util.EnvHelper;


public class ExcelHelper {
	private static Workbook wb;
	private static Sheet sh;
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private final static String CONTACTS_EXCEL_FILE_NAME = EnvHelper.getInstance().getValue("CONTACTS_EXCEL_FILE_NAME");
	private final static String ANSWERS_EXCEL_FILE_NAME = EnvHelper.getInstance().getValue("ANSWERS_EXCEL_FILE_NAME");
	
	//Read sheet from the Excel file
	private static void readExcelFile(String fileName, String sheetName) throws EncryptedDocumentException, IOException{
		fis = new FileInputStream(fileName);
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet(sheetName);
	}
	
	//Write into the Excel file
	private static void writeExcelFile() throws IOException {
		fos = new FileOutputStream(CONTACTS_EXCEL_FILE_NAME);
		wb.write(fos);
	}
	
	private static void closeExcelFile() throws IOException {
		if(wb != null){
			wb.close();
			wb = null;
		}
		if(fos != null) {
			fos.flush();
			fos.close();
			fos = null;
		}
		if(fis != null) {
			fis.close();
			fis = null;
		}
	}
	
	public static List<String> readMessage(MessageType messageType) {
		if(messageType == null)
			return null;
		
		List<String> answers = new ArrayList<>();
		try {
			readExcelFile(ANSWERS_EXCEL_FILE_NAME, "AnswerPool");
			//Check the Answer Cnt
			Row r = sh.getRow(1);
			Cell cell = r.getCell(messageType.getColNumber());
			//get one answer from the AnswerPool
			int answerCnt = (int)cell.getNumericCellValue();
			
			//Save all answer for each messageType
			for(int i = 0;i<answerCnt;i++) {
				r = sh.getRow(2 + i);
				cell = r.getCell(messageType.getColNumber());
				answers.add(cell.getStringCellValue());
			}
			closeExcelFile();
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
		return answers;
	}
	
	public static void saveMessage(String name, String lastMessage) {
		try {
			readExcelFile(CONTACTS_EXCEL_FILE_NAME, "Sheet1");
			
			Row r = sh.createRow(sh.getLastRowNum()+1);
			
			Cell productNameCell = r.createCell(0);
			productNameCell.setCellValue(name);
			Cell customerNameCell = r.createCell(1);
			customerNameCell.setCellValue(lastMessage);
			
			writeExcelFile();
			closeExcelFile();
		}  catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
	}
}
