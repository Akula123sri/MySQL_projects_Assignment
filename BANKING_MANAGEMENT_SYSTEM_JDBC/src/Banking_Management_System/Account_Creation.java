package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Account_Creation {
	 public static void createAccount(Scanner sc) throws Exception {

	        Connection con = DBConnection.getConnection();

	        System.out.print("Enter customer name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter phone number: ");
	        String phone = sc.nextLine();

	        System.out.print("Enter email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter account number: ");
	        String accountNumber = sc.nextLine();

	        System.out.print("Enter account type (Savings/Current): ");
	        String accountType = sc.nextLine();
	        
	        if (name == null || name.trim().isEmpty()) {
	            System.out.println("Name cannot be empty");
	            con.close();
	            return;
	        }

	        if (!phone.matches("\\d{10}")) {
	            System.out.println("Phone number must contain 10 digits");
	            con.close();
	            return;
	        }

	        if (email == null || email.trim().isEmpty()) {
	            System.out.println("Email cannot be empty");
	            con.close();
	            return;
	        }

	        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
	            System.out.println("Invalid email");
	            con.close();
	            return;
	        }

	        if (accountNumber == null || accountNumber.trim().isEmpty()) {
	            System.out.println("Account number cannot be empty");
	            con.close();
	            return;
	        }

	        if (!accountType.equalsIgnoreCase("Savings") &&
	            !accountType.equalsIgnoreCase("Current")) {

	            System.out.println("Account type must be Savings or Current");
	            con.close();
	            return;
	        }


	        int customerId = 0;

	        String checkCustomer = """
	                SELECT customer_id
	                FROM customers
	                WHERE phone = ?
	                """;

	        PreparedStatement psCheck =
	                con.prepareStatement(checkCustomer);

	        psCheck.setString(1, phone);

	        ResultSet rs = psCheck.executeQuery();

	        if (rs.next()) {

	            customerId = rs.getInt("customer_id");

	            System.out.println("Existing customer found.");
	            System.out.println("Customer ID: " + customerId);

	        } else {

	            String insertCustomer = """
	                    INSERT INTO customers
	                    (customer_name, phone, email)
	                    VALUES (?, ?, ?)
	                    """;

	            PreparedStatement psCustomer =con.prepareStatement(insertCustomer,Statement.RETURN_GENERATED_KEYS);
	                    
	            psCustomer.setString(1, name);
	            psCustomer.setString(2, phone);
	            psCustomer.setString(3, email);

	            psCustomer.executeUpdate();

	            ResultSet generatedKeys =psCustomer.getGeneratedKeys();

	            if (generatedKeys.next()) {

	                customerId =generatedKeys.getInt(1);

	                System.out.println(
	                        "New customer created."
	                );

	                System.out.println(
	                        "Customer ID: " + customerId
	                );
	            }
	        }

	        String insertAccount = """
	                INSERT INTO accounts
	                (customer_id, account_number, account_type, balance)
	                VALUES (?, ?, ?, ?)
	                """;

	        PreparedStatement psAccount =
	                con.prepareStatement(insertAccount);

	        psAccount.setInt(1, customerId);
	        psAccount.setString(2, accountNumber);
	        psAccount.setString(3, accountType);
	        psAccount.setDouble(4, 0);

	        psAccount.executeUpdate();

	        System.out.println("Account created successfully.");

	        con.close();
	    }
}
