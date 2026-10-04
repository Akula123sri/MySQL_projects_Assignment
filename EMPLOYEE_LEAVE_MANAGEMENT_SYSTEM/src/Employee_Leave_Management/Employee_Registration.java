package Employee_Leave_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Employee_Registration {
	 public static void registerEmployee(Scanner sc) {

	        String insertEmployee = """
	                insert into employees
	                (employee_name, phone, email, department)values (?, ?, ?, ?)
	                """;

	        String insertBalance = """
	                insert into leave_balance
	                (employee_id, casual_leave,sick_leave, earned_leave)
	                values (?, 10, 10, 15)
	                """;

	        try (
	            Connection con = DBConnection.getConnection()
	        ) {

	            con.setAutoCommit(false);

	            System.out.print("Enter employee name: ");
	            String name = sc.nextLine();

	            System.out.print("Enter phone: ");
	            String phone = sc.nextLine();

	            System.out.print("Enter email: ");
	            String email = sc.nextLine();

	            System.out.print("Enter department: ");
	            String department = sc.nextLine();

	            // Validations

	            if (name.trim().isEmpty()) {

	                System.out.println("Employee name cannot be empty");

	                con.rollback();
	                return;
	            }

	            if (!phone.matches("\\d{10}")) {

	                System.out.println("Phone must contain exactly 10 digits");

	                con.rollback();
	                return;
	            }

	            if (email.trim().isEmpty()) {

	                System.out.println("Email cannot be empty");

	                con.rollback();
	                return;
	            }

	            int employeeId;

	            // Insert employee

	            try (
	                PreparedStatement ps =
	                    con.prepareStatement(insertEmployee,java.sql.Statement.RETURN_GENERATED_KEYS)
	            ) {

	                ps.setString(1, name);
	                ps.setString(2, phone);
	                ps.setString(3, email);
	                ps.setString(4, department);

	                ps.executeUpdate();

	                try (ResultSet keys =
	                        ps.getGeneratedKeys()) {

	                    keys.next();

	                    employeeId = keys.getInt(1);
	                }
	            }

	            // Insert leave balance

	            try (
	                PreparedStatement ps =
	                    con.prepareStatement(insertBalance)
	            ) {

	                ps.setInt(1, employeeId);

	                ps.executeUpdate();
	            }

	            con.commit();

	            System.out.println("\nEmployee registered successfully");

	            System.out.println("Employee ID: " + employeeId);

	            System.out.println("Initial Leave Balance:");

	            System.out.println("Casual Leave : 10");

	            System.out.println("Sick Leave   : 10");

	            System.out.println("Earned Leave : 15");

	        } catch (Exception e) {

	            System.out.println("Registration failed: "+ e.getMessage());
	        }
	    }
}
