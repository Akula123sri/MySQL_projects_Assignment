package Bus_Booking_Application;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db_Connection {
	static final String url="jdbc:mysql://localhost:3306/JDBC_Projects";
    static final String username="root";
    static final String password="root";
    static Connection getConnection() throws Exception{
    	Class.forName("com.mysql.cj.jdbc.Driver");
		 Connection con=DriverManager.getConnection(url,username,password);

    	return con;
    }
}
