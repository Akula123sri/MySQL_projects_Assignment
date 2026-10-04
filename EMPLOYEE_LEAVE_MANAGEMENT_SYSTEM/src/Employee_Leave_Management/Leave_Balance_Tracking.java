package Employee_Leave_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Leave_Balance_Tracking {
	public static void showBalance(Scanner sc) {

        String sql = """
                select e.employee_id,e.employee_name,e.department,lb.casual_leave,lb.sick_leave,lb.earned_leave
                from employees e join leave_balance lb on e.employee_id = lb.employee_id
                where e.employee_id = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =con.prepareStatement(sql)
        ) {

            System.out.print("Enter employee ID: ");

            int employeeId = sc.nextInt();
            sc.nextLine();

            ps.setInt(1, employeeId);

            try (ResultSet rs =ps.executeQuery()) {

                if (rs.next()) {

                    System.out.println("\n========== LEAVE BALANCE ==========");

                    System.out.println("Employee ID : "+ rs.getInt("employee_id"));

                    System.out.println("Name        : "+ rs.getString("employee_name"));

                    System.out.println("Department  : "+ rs.getString("department"));

                    System.out.println("Casual Leave : "+ rs.getInt("casual_leave"));

                    System.out.println("Sick Leave   : "+ rs.getInt("sick_leave"));

                    System.out.println("Earned Leave : "+ rs.getInt("earned_leave"));

                } else {

                    System.out.println("Employee not found");
                }
            }

        } catch (Exception e) {

            System.out.println("Unable to fetch balance: "+ e.getMessage());
        }
    }
}
