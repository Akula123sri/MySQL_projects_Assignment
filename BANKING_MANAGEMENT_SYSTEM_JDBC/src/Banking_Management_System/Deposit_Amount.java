package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Deposit_Amount {
	static void deposit(Scanner sc) throws Exception {
		Connection con=DBConnection.getConnection();
		try {
	    System.out.print("Enter Account Number: ");
	    String accountnumber=sc.nextLine();
	    System.out.print("Enter amount to deposit: ");
	    Double amount=sc.nextDouble();
	    if (amount <= 0) {
            System.out.println("Deposit amount must be greater than 0");
            con.close();
            return;
        }
	    con.setAutoCommit(false);
	    String accountcheck="select account_id from accounts where account_number=?";
	   
	    PreparedStatement pscheck=con.prepareStatement(accountcheck);
	    pscheck.setString(1,accountnumber);
	    ResultSet rs=pscheck.executeQuery();
	    if(!rs.next()) {
	    	
	    	System.out.println("Account not found......");
	    	con.close();
	    	return;
	    }
	    int account_Id=rs.getInt("account_id");
	    String upadtebalance="update accounts set balance=balance+? where account_number=?";
	    PreparedStatement psbalance=con.prepareStatement(upadtebalance);
	    psbalance.setDouble(1,amount);
	    psbalance.setString(2, accountnumber);
	    int rows=psbalance.executeUpdate();
	    if(rows>0) {
	   String inserttransaction="""
               insert into transactions
               (account_id, transaction_type, amount)
               values (?, 'DEPOSIT', ?)
               """;
	   PreparedStatement psinsert=con.prepareStatement(inserttransaction);
	   psinsert.setInt(1,account_Id);
	   psinsert.setDouble(2,amount);
	   psinsert.executeUpdate();
	   con.commit();
	   System.out.println("Deposit Successful");
	   System.out.println("Deposited amount :"+amount);
	    }
		}catch(Exception e) {
			con.rollback();
			con.close();
			e.printStackTrace();
		}
	    
	}

}
