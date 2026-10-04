package Employee_Leave_Management;

import java.util.Scanner;

public class Employee_Leave_Main_Menu {

	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);

	        while (true) {

	            System.out.println("\n========== EMPLOYEE LEAVE MANAGEMENT ==========");

	            System.out.println("1. Employee Registration");

	            System.out.println("2. Apply Leave");

	            System.out.println("3. Approve / Reject Leave");

	            System.out.println("4. Leave Balance");

	            System.out.println("5. Exit");

	            System.out.print("Enter your choice: ");

	            int choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {

	                case 1:

	                    Employee_Registration.registerEmployee(sc);

	                    break;

	                case 2:

	                    Apply_Leave.applyLeave(sc);

	                    break;

	                case 3:

	                    Leave_Approval.approveOrReject(sc);

	                    break;

	                case 4:

	                    Leave_Balance_Tracking.showBalance(sc);

	                    break;

	                

	                case 5:

	                    System.out.println("Thank you for using Employee Leave Management System");

	                    sc.close();

	                    return;

	                default:

	                    System.out.println("Invalid choice");
	            }
	        }

	}

}
