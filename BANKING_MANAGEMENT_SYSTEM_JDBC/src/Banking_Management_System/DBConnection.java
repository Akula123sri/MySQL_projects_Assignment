package Banking_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	static final String url="jdbc:mysql://localhost:3306/JDBC_Projects";
    static final String username="root";
    static final String password="root";
    static Connection getConnection() throws Exception{
    	Class.forName("com.mysql.cj.jdbc.Driver");
		 Connection con=DriverManager.getConnection(url,username,password);

    	return con;
    }
   

}
