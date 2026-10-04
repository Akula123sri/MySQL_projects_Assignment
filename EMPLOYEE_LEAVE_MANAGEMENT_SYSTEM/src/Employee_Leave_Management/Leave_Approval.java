package Employee_Leave_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Leave_Approval {
	public static void approveOrReject(Scanner sc) {

        String getLeave = """
                select employee_id,leave_type,start_date,end_date,status
                from leave_requests where leave_id = ?
                """;

        String approveLeave = """
                update leave_requests set status = 'APPROVED' where leave_id = ?
                """;

        String rejectLeave = """
                update leave_requests set status = 'REJECTED' where leave_id = ?
                """;

        String updateCasual = """
                update leave_balance set casual_leave = casual_leave - ?
                where employee_id = ? and casual_leave >= ?
                """;

        String updateSick = """
                update leave_balance set sick_leave = sick_leave - ?
                where employee_id = ? and sick_leave >= ?
                """;

        String updateEarned = """
                update leave_balance set earned_leave = earned_leave - ?
                where employee_id = ? and earned_leave >= ?
                """;

        try (
            Connection con = DBConnection.getConnection()
        ) {

            con.setAutoCommit(false);

            System.out.print("Enter leave ID: ");
            int leaveId = sc.nextInt();
            sc.nextLine();

            int employeeId;
            String leaveType;
            LocalDate startDate;
            LocalDate endDate;
            String status;

            // Get leave request

            try (
                PreparedStatement ps =
                    con.prepareStatement(getLeave)
            ) {

                ps.setInt(1, leaveId);

                try (ResultSet rs =
                        ps.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println("Leave request not found");

                        con.rollback();
                        return;
                    }

                    employeeId =rs.getInt("employee_id");

                    leaveType =rs.getString("leave_type");

                    startDate =rs.getDate("start_date").toLocalDate();

                    endDate =rs.getDate("end_date").toLocalDate();

                    status =rs.getString("status");
                }
            }

            // Check status

            if (!status.equals("PENDING")) {

                System.out.println("Leave is already processed");

                con.rollback();
                return;
            }

            System.out.println("\n1. Approve");

            System.out.println("2. Reject");

            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            // Reject

            if (choice == 2) {

                try (
                    PreparedStatement ps =con.prepareStatement(rejectLeave)
                ) {

                    ps.setInt(1, leaveId);

                    ps.executeUpdate();
                }

                con.commit();

                System.out.println("Leave rejected successfully");

                return;
            }

            // Approve

            if (choice != 1) {

                System.out.println("Invalid choice");

                con.rollback();
                return;
            }

            long days =ChronoUnit.DAYS.between(startDate,endDate) + 1;

            if (days <= 0) {

                System.out.println("Invalid leave dates");

                con.rollback();
                return;
            }

            int rows;

            // Update leave balance

            if (leaveType.equals("CASUAL")) {

                try (
                    PreparedStatement ps =con.prepareStatement(updateCasual)
                ) {

                    ps.setLong(1, days);
                    ps.setInt(2, employeeId);
                    ps.setLong(3, days);

                    rows = ps.executeUpdate();
                }

            } else if (leaveType.equals("SICK")) {

                try (
                    PreparedStatement ps =con.prepareStatement(updateSick)
                ) {

                    ps.setLong(1, days);
                    ps.setInt(2, employeeId);
                    ps.setLong(3, days);

                    rows = ps.executeUpdate();
                }

            } else {

                try (
                    PreparedStatement ps =con.prepareStatement(updateEarned)
                ) {

                    ps.setLong(1, days);
                    ps.setInt(2, employeeId);
                    ps.setLong(3, days);

                    rows = ps.executeUpdate();
                }
            }

            if (rows == 0) {

                System.out.println("Insufficient leave balance");

                con.rollback();
                return;
            }

            // Approve leave

            try (
                PreparedStatement ps =con.prepareStatement(approveLeave)
            ) {

                ps.setInt(1, leaveId);

                ps.executeUpdate();
            }

            con.commit();

            System.out.println("Leave approved successfully");

            System.out.println("Leave days: " + days);

        } catch (Exception e) {

            System.out.println("Leave processing failed: "+ e.getMessage());
                }
            }
}
