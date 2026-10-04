package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Withdraw_Amount {
static void withdraw(Scanner sc) throws Exception {
	Connection con=DBConnection.getConnection();
	System.out.print("Enter Account Number: ");
    String accountnumber=sc.nextLine();
    System.out.print("Enter amount to withdraw: ");
    Double amount=sc.nextDouble();
    if (amount <= 0) {
        System.out.println("Withdrawal amount must be greater than 0");
        con.close();
        return;
    }
   
    String accountcheck="select account_id,balance from accounts where account_number=?";
   
    PreparedStatement pscheck=con.prepareStatement(accountcheck);
    pscheck.setString(1,accountnumber);
    ResultSet rs=pscheck.executeQuery();
    if(!rs.next()) {
    	
    	System.out.println("Account not found......");
    	con.close();
    	return;
    }
    int account_Id=rs.getInt("account_id");
    int balance=rs.getInt("balance");
    if(amount>balance) {
    	System.out.println("Insufficient funds ");
    	System.out.println("available balance: "+balance);
    	con.close();
    	return;
    }
    String upadtebalance="update accounts set balance=balance-? where account_number=?";
    PreparedStatement psbalance=con.prepareStatement(upadtebalance);
    psbalance.setDouble(1,amount);
    psbalance.setString(2, accountnumber);
    int rows=psbalance.executeUpdate();
    if(rows>0) {
   String inserttransaction="""
           insert into transactions
           (account_id, transaction_type, amount)
           values (?, 'WITHDRAW', ?)
           """;
   PreparedStatement psinsert=con.prepareStatement(inserttransaction);
   psinsert.setInt(1,account_Id);
   psinsert.setDouble(2,amount);
   psinsert.executeUpdate();
   System.out.println("Withdraw Successful");
   System.out.println("Withdrawl amount :"+amount);
    }
}
}
