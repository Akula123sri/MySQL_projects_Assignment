package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Transaction_history {
 static void transaction_detailes(Scanner sc) throws Exception {
	 Connection con=DBConnection.getConnection();
		System.out.print("Enter Account Number: ");
	    String accountNumber=sc.nextLine();

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            System.out.println("Account number cannot be empty");
            con.close();
            return;
        }
        String accountcheck="select account_id from accounts where account_number=?";
        
        PreparedStatement pscheck=con.prepareStatement(accountcheck);
        pscheck.setString(1,accountNumber);
        ResultSet rs=pscheck.executeQuery();
        if(!rs.next()) {
        	
        	System.out.println("Account not found......");
        	con.close();
        	return;
        }
        int account_Id=rs.getInt("account_id");
        String history="""
        		select transaction_id,transaction_type,amount,transaction_date
        		 from transactions where account_id=? order by transaction_date
        		""";
        PreparedStatement pshistory=con.prepareStatement(history);
        pshistory.setInt(1,account_Id );
        ResultSet rows=pshistory.executeQuery();
        boolean found=false;
        while (rows.next()) {

            found = true;

            int transactionId =rows.getInt("transaction_id");

            String type =rows.getString("transaction_type");

            double amount =rows.getDouble("amount");

            String date =rows.getString("transaction_date");

            System.out.println("Transaction ID : " + transactionId);

            System.out.println("Type            : " + type);

            System.out.println("Amount          : " + amount);
            
            System.out.println("Date            : " + date);
         
            System.out.println("-----------------------------");
        }
        if(!found) {
        	System.out.println("No transactions found");
        }
        pscheck.close();
        pshistory.close();
        rs.close();
        rows.close();
        
 }
}
