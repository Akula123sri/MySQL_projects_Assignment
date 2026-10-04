package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Account_Details {
	 static void getAccountDetails(Scanner sc) throws Exception {
		System.out.print("Enter Account Number: ");
	    String accountnumber=sc.nextLine();
	    if (accountnumber == null || accountnumber.trim().isEmpty()) {
            System.out.println("Account number cannot be empty");
            return;
        }
	    String sql = """
	            select
	                c.customer_name,
	                c.phone,
	                a.account_id,
	                a.account_type,
	                a.balance
	            from customers c
	            join accounts a
	                on c.customer_id = a.customer_id
	            where a.account_number = ?
	            """;

	    try (
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)
	    ) {

	        ps.setString(1, accountnumber);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {

	                System.out.println("\n===== ACCOUNT DETAILS =====");

	                System.out.println("Account Holder : "+ rs.getString("customer_name"));

	                System.out.println("Phone Number   : "+ rs.getString("phone"));

	                System.out.println("Account ID     : "+ rs.getInt("account_id"));

	             System.out.println("Account Type   : "+ rs.getString("account_type"));

	                System.out.println("Balance        : "+ rs.getDouble("balance") );

	            } else {

	                System.out.println("Account not found");
	            }

	        }

	    } catch (Exception e) {
            
	        System.out.println("Database error: " + e.getMessage());
	    }
	    ;
	}
}
