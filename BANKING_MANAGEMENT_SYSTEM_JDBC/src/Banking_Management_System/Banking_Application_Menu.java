package Banking_Management_System;

import java.util.Scanner;

public class Banking_Application_Menu {

	public static void main(String[] args) throws Exception{
		Scanner sc=new Scanner(System.in);
		try {
		while (true) {
            System.out.println("\n===== BANKING MANAGEMENT SYSTEM =====");
            System.out.println("1. Account Creation");
            System.out.println("2. Deposit");
            System.out.println("3. Withdrawal");
            System.out.println("4. Fund Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. GetAccountdetails");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    Account_Creation.createAccount(sc);
                    break;

                case 2:
                    Deposit_Amount.deposit(sc);
                    break;

                case 3:
                    Withdraw_Amount.withdraw(sc);
                    break;

                case 4:
                    Transfer_Amount.transfer(sc);
                    break;

                case 5:
                    Transaction_history.transaction_detailes(sc);;
                    break;
                case 6:
                	Account_Details.getAccountDetails(sc);
                	break;

                case 7:
                    System.out.println("Thank you for using Banking Management System");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
		}catch(Exception e) {
			System.out.println("something went wrong");
			e.printStackTrace();
		}finally {
			sc.close();
		}
	}

}
