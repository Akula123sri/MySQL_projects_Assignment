package Employee_Leave_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Apply_Leave {
	public static void applyLeave(Scanner sc) {

        String sql = """
                insert into leave_requests
                (employee_id, leave_type,start_date, end_date,reason, status)
                values (?, ?, ?, ?, ?, 'PENDING')
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            System.out.print("Enter employee ID: ");
            int employeeId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter leave type (CASUAL/SICK/EARNED): ");
            String leaveType =sc.nextLine().toUpperCase();

            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDate = sc.nextLine();

            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDate = sc.nextLine();

            System.out.print("Enter reason: ");
            String reason = sc.nextLine();

            // Validation

            if (!leaveType.equals("CASUAL")
                    && !leaveType.equals("SICK")
                    && !leaveType.equals("EARNED")) {

                System.out.println("Invalid leave type");

                return;
            }

            if (reason.trim().isEmpty()) {

                System.out.println("Reason cannot be empty");

                return;
            }

            ps.setInt(1, employeeId);
            ps.setString(2, leaveType);
            ps.setDate(3,java.sql.Date.valueOf(startDate)
            );
            ps.setDate(4,java.sql.Date.valueOf(endDate)
            );
            ps.setString(5, reason);

            ps.executeUpdate();

            System.out.println("Leave applied successfully");

            System.out.println("Leave status: PENDING");

        } catch (Exception e) {

            System.out.println("Leave application failed: "+ e.getMessage());
        }
    }
}
