package storages;

import util.EnvHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class DBHelper {
	
	public static boolean addProductMessage(String title, String email, String subject, String agentName, 
			String agentNumber, String question, String message) throws SQLException {
		title = title.trim();
		message = message.trim();
		question = question.trim();
		email = email.trim();
		if(!title.contentEquals("") && !message.contentEquals("") && !email.contentEquals("") && !question.contentEquals("")) {
			PreparedStatement ps;
			ps = DB.getInstance().prepareStatement("INSERT INTO 'product' VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)");
			int startIdx = 1;
			ps.setString(startIdx++, title);
			ps.setString(startIdx++, email);
			ps.setString(startIdx++, subject);
			ps.setString(startIdx++, agentName);
			ps.setString(startIdx++, agentNumber);
			ps.setString(startIdx++, question);
			ps.setString(startIdx, message);
			return ps.executeUpdate() == 1;
		}
		return false;
	}

	public static boolean editProductMessage(int id, String title, String email, String subject, String agentName,
			String agentNumber, String question, String message) throws SQLException {
		title = title.trim();
		message = message.trim();
		question = question.trim();
		email = email.trim();
		subject = subject.trim();
		agentName = agentName.trim();
		agentNumber = agentNumber.trim();
		if(!title.contentEquals("") && !message.contentEquals("") && !email.contentEquals("") && !question.contentEquals("")) {
			PreparedStatement ps 
			= DB.getInstance().prepareStatement("UPDATE 'product' SET title = ?, email = ?, question = ?, subject = ?, agent_name = ?, agent_number = ?, message = ? WHERE id = ?");
			ps.setString(1, title);
			ps.setString(2, email);
			ps.setString(3, question);
			ps.setString(4, subject);
			ps.setString(5, agentName);
			ps.setString(6, agentNumber);
			ps.setString(7, message);
			ps.setInt(8, id);
			return ps.executeUpdate() == 1;
		}
		return false;
	}
	
	public static boolean deleteProduct(int id) throws SQLException{
		PreparedStatement ps = DB.getInstance().prepareStatement("DELETE FROM product WHERE id = ?");
		ps.setInt(1, id);
		return ps.executeUpdate() == 1;
	}
	
	public static boolean addKeywordMessage(String productId, String keyword, String andKey, String orKey, String message) throws SQLException{
		keyword = keyword.trim();
		message = message.trim();
		if(!keyword.contentEquals("") && !message.contentEquals("")) {
			PreparedStatement ps = DB.getInstance().prepareStatement("INSERT INTO 'keyword' VALUES(NULL, ?, ?, ?, ?, ?)");
			ps.setString(1, productId);
			ps.setString(2, keyword);
			ps.setString(3, andKey);
			ps.setString(4, orKey);
			ps.setString(5, message);
			return ps.executeUpdate() == 1;
		}
		return false;
	}

	public static void refreshProducts(DefaultTableModel model) throws SQLException {
		model.setRowCount(0);
		PreparedStatement ps = DB.getInstance().prepareStatement("SELECT * FROM product");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			model.addRow(new Object[] {
				rs.getInt("id"),
				rs.getString("title"),
				rs.getString("email"),
				rs.getString("question"),
				rs.getString("message")
			});
		}
	}

	public static void refreshKeywords(DefaultTableModel model) throws SQLException{
		model.setRowCount(0);
		PreparedStatement ps;
		
		ps = DB.getInstance().prepareStatement("SELECT * FROM keyword");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			String key = rs.getString("keyword");
			String orK = rs.getString("or_keyword");
			String andK = rs.getString("and_keyword");
			key += (!orK.equals("") && !orK.equals("this_text_is_not_possible"))? "|"+orK : "";
			key += (!andK.equals(""))? "&"+andK : "";
			
			model.addRow(new Object[] {
					rs.getInt("id"),
					rs.getString("product_id"),
					key,
					rs.getString("message")
			});
		}
	}
	
	public static String getTitleBasedMessage(String productId, String question) throws SQLException{//title == product_id
		String response = "";
		
		PreparedStatement ps = DB.getInstance().prepareStatement(
				"SELECT * FROM product WHERE title = ? AND ? LIKE '%'||product.question||'%'");
		ps.setString(1, productId);
		ps.setString(2, question);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			response = rs.getString("message");
		}
		return response;
	}

	public static ResultSet getRecordByTitle(String title) throws SQLException{
		ResultSet rs = null;
        
        PreparedStatement ps = DB.getInstance().prepareStatement("SELECT * FROM product WHERE title = ?");
        ps.setString(1, title);
        rs = ps.executeQuery();
        return rs;
	}

	public static ResultSet getProductBasedMsgByID(int id) throws SQLException{
		ResultSet rs;
		
		PreparedStatement ps = DB.getInstance().prepareStatement("SELECT * FROM product WHERE id = ?");
		ps.setInt(1, id);
		rs = ps.executeQuery();
		return rs;
	}
	
	public static String getKeywordBasedMessage(String itemId, String keyword) throws SQLException{
        String response;
        
        response = getAndFromTagBased(itemId, keyword);
        if(!response.equals(""))
            return response;
        
        response = getOrFromTagBased(itemId, keyword);
        if(!response.equals(""))
            return response;
        
        response = getOnlyOneTagBased(itemId, keyword);
        if(!response.equals(""))
        	return response;
        
    	response = getFromAllTagBased(keyword);
        
        return response;
	}
	
	
	private static String getFromAllTagBased(String keyword) throws SQLException{
		String response = "";
    	PreparedStatement ps = DB.getInstance().prepareStatement("SELECT * FROM keyword WHERE ? LIKE '%'||keyword.keyword||'%' AND product_id = ?");
        ps.setString(1, keyword);
        ps.setString(2, EnvHelper.getInstance().getValue("FOR_ALL_PRODUCTS"));
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            response = rs.getString("message");
        }
        return response;
	}

	private static String getAndFromTagBased(String itemId, String keyword) throws SQLException{
        String response = "";
        PreparedStatement ps = DB.getInstance().prepareStatement("SELECT * FROM keyword WHERE ? LIKE '%'||keyword.keyword||'%' AND  ? LIKE '%'||keyword.and_keyword||'%' AND product_id = ?");
        ps.setString(1, keyword);
        ps.setString(2, keyword);
        ps.setString(3, itemId);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            response = rs.getString("message");
        }
        return response;
	}

	private static String getOrFromTagBased(String itemId, String keyword) throws SQLException{
        String response = "";
        PreparedStatement ps = DB.getInstance().prepareStatement("SELECT * FROM keyword WHERE ? LIKE '%'||keyword.keyword||'%' OR  ? LIKE '%'||keyword.or_keyword||'%' AND product_id = ?");
        ps.setString(1, keyword);
        ps.setString(2, keyword);
        ps.setString(3, itemId);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            response = rs.getString("message");
        }
        return response;
	}
	
	private static String getOnlyOneTagBased(String itemId, String keyword) throws SQLException{
        String response = "";

        PreparedStatement ps = DB.getInstance().prepareStatement("SELECT * FROM keyword WHERE ? LIKE '%'||keyword.keyword||'%' AND product_id = ?");
        ps.setString(1, keyword);
        ps.setString(2, itemId);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()) {
            response = rs.getString("message");
        }
        return response;
	}

	public static boolean deleteKeyword(int id) throws SQLException{
		PreparedStatement ps = DB.getInstance().prepareStatement("DELETE FROM keyword WHERE id = ?");
		ps.setInt(1, id);
		return ps.executeUpdate() == 1;
	}
	
	
	public static void release() {
		DB.release();
	}
}
