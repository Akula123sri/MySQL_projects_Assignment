package Bus_Booking_Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Ticket_Cancelation {
	  public static void cancelTicket(Scanner sc) {

	        String checkBooking = """
	                select booking_id, bus_id,passenger_id, seat_number,booking_status
	                from bookings where booking_id = ? and booking_status = 'CONFIRMED'
	                """;

	        String cancelBooking = """
	                update bookings set booking_status = 'CANCELLED' where booking_id = ?
	                """;

	        try (
	            Connection con = Db_Connection.getConnection();
	            PreparedStatement psCheck =con.prepareStatement(checkBooking);
	            PreparedStatement psCancel =con.prepareStatement(cancelBooking)
	        ) {

	            System.out.print("Enter booking ID: ");
	            int bookingId = sc.nextInt();
	            sc.nextLine();

	            // Check booking
	            psCheck.setInt(1, bookingId);

	            try (ResultSet rs = psCheck.executeQuery()) {

	                if (!rs.next()) {

	                    System.out.println("Booking not found or already cancelled");

	                    return;
	                }

	                int busId = rs.getInt("bus_id");
	                int passengerId = rs.getInt("passenger_id");
	                int seatNumber = rs.getInt("seat_number");

	                System.out.println("Booking ID : " + bookingId);

	                System.out.println("Bus ID     : " + busId);

	                System.out.println("Passenger ID : " + passengerId);

	                System.out.println("Seat Number : " + seatNumber);

	                // Cancel booking
	                psCancel.setInt(1, bookingId);

	                int rows = psCancel.executeUpdate();

	                if (rows > 0) {

	                    System.out.println("Ticket cancelled successfully");

	                } else {

	                    System.out.println("Ticket cancellation failed");
	                }
	            }

	        } catch (Exception e) {

	            System.out.println("Cancellation failed: "+ e.getMessage());
	        }
	    }
}
