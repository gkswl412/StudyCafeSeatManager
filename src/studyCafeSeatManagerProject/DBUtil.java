package studyCafeSeatManagerProject;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	
	public static Connection dbConnect() {
		//DB연결
		Connection conn = null;
		String id,password,url;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Properties pro = new Properties();
			pro.load(new FileReader("src/studyCafeSeatManagerProject/dbinfo.properties"));
			id = pro.getProperty("id");
			password = pro.getProperty("password");
			url = pro.getProperty("url");
			conn = DriverManager.getConnection(url,id,password);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void dbClose(Connection conn, Statement st, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			System.out.println("자원반납실패");
			e.printStackTrace();
		}
	}
}
