package Employee_Leave_Management;

import java.sql.Connection;
import java.sql.Statement;

public class Tables {

	public static void main(String[] args) {

        String employees = """
                CREATE TABLE IF NOT EXISTS employees (
                    employee_id INT PRIMARY KEY AUTO_INCREMENT,
                    employee_name VARCHAR(50) NOT NULL,
                    phone VARCHAR(10) NOT NULL UNIQUE,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    department VARCHAR(50) NOT NULL
                )
                """;

        String leaveRequests = """
                CREATE TABLE IF NOT EXISTS leave_requests (
                    leave_id INT PRIMARY KEY AUTO_INCREMENT,
                    employee_id INT NOT NULL,
                    leave_type VARCHAR(30) NOT NULL,
                    start_date DATE NOT NULL,
                    end_date DATE NOT NULL,
                    reason VARCHAR(200),
                    status VARCHAR(20) DEFAULT 'PENDING',

                    FOREIGN KEY (employee_id)
                    REFERENCES employees(employee_id)
                )
                """;

        String leaveBalance = """
                CREATE TABLE IF NOT EXISTS leave_balance (
                    employee_id INT PRIMARY KEY,
                    casual_leave INT DEFAULT 10,
                    sick_leave INT DEFAULT 10,
                    earned_leave INT DEFAULT 15,

                    FOREIGN KEY (employee_id)
                    REFERENCES employees(employee_id)
                )
                """;

        try (
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement()
        ) {

            st.executeUpdate(employees);
            st.executeUpdate(leaveRequests);
            st.executeUpdate(leaveBalance);

            System.out.println("Tables created successfully");

        } catch (Exception e) {

            System.out.println(
                    "Table creation failed: "
                    + e.getMessage()
            );
        }
    

	}

}
