package storages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.EnvHelper;

public class DB {
	private static Connection con;
	public static Logger logger = LogManager.getLogger(DB.class);

	public static Connection getInstance() {//singleton
		if(con == null) {
			try {
				final String JDBC_CLASSNAME = EnvHelper.getInstance().getValue("JDBC_CLASSNAME");
				final String DB_URL = EnvHelper.getInstance().getValue("DB_URL");
				Class.forName(JDBC_CLASSNAME);
				con = DriverManager.getConnection(DB_URL);
				logger.info("[SQLITE DB CONNECTED]");
			} catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Database Connection Error" + e);
			} catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Configuration Error | Contact BXTrack Sol. at: +92 317 1507719");
			}
		}
		return con;
	}
	
	public static void release() {
		if(con != null) {
			try {
				con.close();
				con = null;
				logger.info("[SQLITE DB CLOSED]");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
