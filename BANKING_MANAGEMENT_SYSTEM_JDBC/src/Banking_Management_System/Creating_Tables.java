package Banking_Management_System;

import java.sql.Connection;
import java.sql.Statement;

public class Creating_Tables {

	public static void main(String[] args) throws Exception {
		Connection con=DBConnection.getConnection();
		String customer="""
		        CREATE TABLE customers (
	            customer_id INT PRIMARY KEY AUTO_INCREMENT,
	            customer_name VARCHAR(50) NOT NULL,
	            phone VARCHAR(15) UNIQUE NOT NULL,
	            email VARCHAR(100) UNIQUE NOT NULL
	        )
	        """;
		String account= """
		        CREATE TABLE accounts (
	            account_id INT PRIMARY KEY AUTO_INCREMENT,
	            customer_id INT,
	            account_number VARCHAR(20) UNIQUE NOT NULL,
	            account_type VARCHAR(20) NOT NULL,
	            balance DECIMAL(12,2) DEFAULT 0,
	            FOREIGN KEY (customer_id)
	            REFERENCES customers(customer_id)
	        )
	        """;
		String transaction= """
		        CREATE TABLE transactions (
	            transaction_id INT PRIMARY KEY AUTO_INCREMENT,
	            account_id INT,
	            transaction_type VARCHAR(20),
	            amount DECIMAL(12,2),
	            transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	            FOREIGN KEY (account_id)
	            REFERENCES accounts(account_id)
	        )
	        """;
		Statement cs=con.createStatement();
		cs.executeUpdate(customer);
		cs.executeUpdate(account);
		cs.executeUpdate(transaction);
		System.out.println("tables created");
	}

}
