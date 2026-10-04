package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Transfer_Amount {
 public static void transfer(Scanner sc) throws Exception{
	 Connection con=DBConnection.getConnection();
	 try {
	 System.out.print("enter sender Account_Number: ");
	 String senderaccount=sc.nextLine();
	 System.out.print("enter Receiver Account_Number: ");
	 String receiveraccount=sc.nextLine();
	 System.out.print("enter amount to transfer: ");
	 int amount_transfer=sc.nextInt();
	 if (senderaccount == null || senderaccount.trim().isEmpty()&& receiveraccount == null || receiveraccount.trim().isEmpty()){
         System.out.println("Account number cannot be empty");
         con.close();
         return;
     }
	 if (amount_transfer <= 0) {
         System.out.println("Transfer amount must be greater than 0");
         return;
     }

     // Sender and receiver cannot be same
     if (senderaccount.equals(receiveraccount)) {
         System.out.println("Sender and receiver accounts cannot be same");
         return;
     }
     con.setAutoCommit(false);
	 String sender="""
	 		select account_id,balance from accounts where account_number=?
	 		""";
	 
	 PreparedStatement pssender=con.prepareStatement(sender);
	 pssender.setString(1,senderaccount);
	 ResultSet senderdetailes=pssender.executeQuery();
	 if(!senderdetailes.next()) {
		 System.out.println("sender not found");
		 
		 con.rollback();
		 return;
	 }
	 int sender_id=senderdetailes.getInt("account_id");
	 int sender_balance=senderdetailes.getInt("balance");
	 if (amount_transfer > sender_balance) {

         System.out.println("Insufficient balance");
         System.out.println(
                 "Available balance: " + sender_balance
         );

         con.rollback();
         return;
     }
	 String receiver="""
		 		select account_id from accounts where account_number=?
		 		""";
		 PreparedStatement psreceiver=con.prepareStatement(receiver);
		 psreceiver.setString(1,receiveraccount);
		 ResultSet receiverdetailes=psreceiver.executeQuery();
		 if(!receiverdetailes.next()) {
			 System.out.println("receiver not found");
			 con.rollback();
			 return;
		 }
		 int receiver_id=senderdetailes.getInt("account_id");
		 
		 //updating sender balance
		 String senderupdate="""
			 		update accounts set balance=balance-? where account_id=?
			 		""";
			 PreparedStatement pssenderupdate=con.prepareStatement(senderupdate);
			 pssenderupdate.setInt(1,amount_transfer);
			 pssenderupdate.setInt(2,sender_id);
			int deduct= pssenderupdate.executeUpdate();
	         if(deduct==0) {
	        	 System.out.println("money transfer from sender failed");
	        	 con.rollback();
	        	 return;
	         }
	       //updating receiver balance
			 String receiverupdate="""
				 		update accounts set balance=balance+? where account_id=?
				 		""";
				 PreparedStatement psreceiverupdate=con.prepareStatement(senderupdate);
				 psreceiverupdate.setInt(1,amount_transfer);
				 psreceiverupdate.setInt(2,receiver_id);
				int add= psreceiverupdate.executeUpdate();
		         if(add==0) {
		        	 System.out.println("money transfer failed");
		        	 con.rollback();
		        	 return;
		         }
		   //Transaction history
		         String senderTransaction = """
		                    insert into transactions
		                    (account_id, transaction_type, amount)
		                    values (?, 'TRANSFER_OUT', ?)
		                    """;

		            PreparedStatement psSenderTransaction =
		                    con.prepareStatement(senderTransaction);

		            psSenderTransaction.setInt(1, sender_id);
		            psSenderTransaction.setDouble(2, amount_transfer);

		            psSenderTransaction.executeUpdate();
		            
		            String receiverTransaction = """
		                    insert into transactions
		                    (account_id, transaction_type, amount)
		                    values (?, 'TRANSFER_IN', ?)
		                    """;

		            PreparedStatement psReceiverTransaction =
		                    con.prepareStatement(receiverTransaction);

		            psReceiverTransaction.setInt(1, receiver_id);
		            psReceiverTransaction.setDouble(2, amount_transfer);

		            psReceiverTransaction.executeUpdate();
		            con.commit();

		            System.out.println("Transfer successful");
		            System.out.println("Transferred amount: " + amount_transfer);
	 }catch(Exception e) {
		 con.rollback();

         System.out.println("Transfer failed. Transaction rolled back.");
        throw e;
		 
	 }finally {
		 con.close();
		
	 }
	
}
}
