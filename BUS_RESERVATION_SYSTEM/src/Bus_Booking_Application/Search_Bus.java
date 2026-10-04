package Bus_Booking_Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Search_Bus {
	public static void searchBuses(Scanner sc) {

        String sql = """
                select bus_id,bus_name,source,destination,total_seats from buses
                where source = ? and destination = ?
                """;

        try (
            Connection con = Db_Connection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            System.out.print("Enter source: ");
            String source = sc.nextLine();

            System.out.print("Enter destination: ");
            String destination = sc.nextLine();
            
            if (source.isEmpty() || destination.isEmpty()) {
                System.out.println("Source and destination cannot be empty");
                return;
            }

            ps.setString(1, source);
            ps.setString(2, destination);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.println("\nBus ID       : "+ rs.getInt("bus_id"));

                    System.out.println("Bus Name     : "+ rs.getString("bus_name"));

                    System.out.println("Source       : "+ rs.getString("source"));

                    System.out.println("Destination  : "+ rs.getString("destination"));

                    System.out.println("Total Seats  : "+ rs.getInt("total_seats"));

                    System.out.println("-------------------------");
                }

                if (!found) {
                    System.out.println("No buses found for this route");
                }
            }

        } catch (Exception e) {

            System.out.println("Database error: " + e.getMessage());
        }
    }
}
