package Bus_Booking_Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Passenger_Detailes {
	public static void getPassengerDetails(Scanner sc) {

        String sql = """
                select p.passenger_id,p.pname,p.phone,p.email,b.bus_id,b.bus_name,b.source,b.destination,
                 bk.booking_id,bk.seat_number,bk.booking_status,bk.booking_date from passengers p
                join bookings bk on p.passenger_id = bk.passenger_id join buses b
                on bk.bus_id = b.bus_id where bk.booking_id = ?
                """;

        try (
            Connection con = Db_Connection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            System.out.print("Enter booking ID: ");
            int bookingId = sc.nextInt();
            sc.nextLine();
            

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    System.out.println("\n========== PASSENGER DETAILS ==========");

                    System.out.println("Passenger ID   : "+ rs.getInt("passenger_id"));

                    System.out.println("Passenger Name : "+ rs.getString("pname"));

                    System.out.println("Phone          : "+ rs.getString("phone"));

                    System.out.println("Email          : "+ rs.getString("email"));

                    System.out.println("Bus ID         : "+ rs.getInt("bus_id"));

                    System.out.println("Bus Name       : "+ rs.getString("bus_name"));

                    System.out.println("Source         : "+ rs.getString("source"));

                    System.out.println("Destination    : "+ rs.getString("destination"));

                    System.out.println("Booking ID     : "+ rs.getInt("booking_id"));

                    System.out.println("Seat Number    : "+ rs.getInt("seat_number"));

                    System.out.println("Booking Status : "+ rs.getString("booking_status"));

                    System.out.println("Booking Date   : "+ rs.getTimestamp("booking_date"));

                } else {

                    System.out.println("Booking not found");
                }
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

}
