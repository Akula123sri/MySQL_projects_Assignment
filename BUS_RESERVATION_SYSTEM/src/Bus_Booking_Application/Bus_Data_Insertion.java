package Bus_Booking_Application;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Bus_Data_Insertion {

	public static void main(String[] args) {
		String sql = """
		        INSERT INTO buses
		        (bus_name, source, destination, total_seats)
		        VALUES (?, ?, ?, ?)
		        """;

		try (
		    Connection con = Db_Connection.getConnection();
		    PreparedStatement ps = con.prepareStatement(sql)
		) {

		    ps.setString(1, "Orange Express");
		    ps.setString(2, "Hyderabad");
		    ps.setString(3, "Vijayawada");
		    ps.setInt(4, 40);
		    ps.addBatch();

		    ps.setString(1, "Green Travels");
		    ps.setString(2, "Hyderabad");
		    ps.setString(3, "Chennai");
		    ps.setInt(4, 45);
		    ps.addBatch();

		    ps.setString(1, "SVK Express");
		    ps.setString(2, "Vijayawada");
		    ps.setString(3, "Hyderabad");
		    ps.setInt(4, 40);
		    ps.addBatch();

		    ps.setString(1, "Morning Star");
		    ps.setString(2, "Hyderabad");
		    ps.setString(3, "Vijayawada");
		    ps.setInt(4, 35);
		    ps.addBatch();

		    ps.setString(1, "Royal Travels");
		    ps.setString(2, "Chennai");
		    ps.setString(3, "Bangalore");
		    ps.setInt(4, 50);
		    ps.addBatch();

		    ps.setString(1, "City Express");
		    ps.setString(2, "Bangalore");
		    ps.setString(3, "Hyderabad");
		    ps.setInt(4, 45);
		    ps.addBatch();

		    ps.setString(1, "Super Deluxe");
		    ps.setString(2, "Hyderabad");
		    ps.setString(3, "Bangalore");
		    ps.setInt(4, 40);
		    ps.addBatch();

		    ps.setString(1, "Night Rider");
		    ps.setString(2, "Vijayawada");
		    ps.setString(3, "Chennai");
		    ps.setInt(4, 50);
		    ps.addBatch();

		    ps.setString(1, "Metro Travels");
		    ps.setString(2, "Chennai");
		    ps.setString(3, "Hyderabad");
		    ps.setInt(4, 45);
		    ps.addBatch();

		    ps.setString(1, "Fast Track");
		    ps.setString(2, "Bangalore");
		    ps.setString(3, "Chennai");
		    ps.setInt(4, 40);
		    ps.addBatch();

		    int[] result = ps.executeBatch();

		    System.out.println(result.length + " buses inserted successfully");

		} catch (Exception e) {

		    System.out.println("Error: " + e.getMessage());
		}

	}

}
