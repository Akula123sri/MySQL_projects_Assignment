package Bus_Booking_Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Seat_Booking {
	public static void bookSeat(Scanner sc) {

        String checkBus ="select bus_id, total_seats from buses where bus_id = ?";
                
        String checkSeat = """
                select booking_id from bookings where bus_id = ?
                and seat_number = ? and booking_status = 'CONFIRMED'
                """;

        String insertPassenger ="insert into passengers(pname, phone, email)values (?, ?, ?)"; 
                
        String insertBooking = """
                insert into bookings(bus_id, passenger_id, seat_number, booking_status)
                values (?, ?, ?, 'CONFIRMED')
                """;

        try (
            Connection con = Db_Connection.getConnection()
        ) {

           
            con.setAutoCommit(false);

            
            // checking bus by id

            System.out.print("Enter bus ID: ");
            int busId = sc.nextInt();
            sc.nextLine();

            try (PreparedStatement psBus =con.prepareStatement(checkBus)) {

                psBus.setInt(1, busId);

                try (ResultSet rs = psBus.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println("Bus not found");
                        con.rollback();
                        return;
                    }

                    int totalSeats =rs.getInt("total_seats");

                    // 2. Passenger details for seat booking if bus found
                    

                    System.out.print("Enter passenger name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    
                    if (name == null || name.trim().isEmpty()) {
        	            System.out.println("Name cannot be empty");
        	            con.close();
        	            return;
        	        }

        	        if (!phone.matches("\\d{10}")) {
        	            System.out.println("Phone number must contain 10 digits");
        	            con.close();
        	            return;
        	        }

        	        if (email == null || email.trim().isEmpty()) {
        	            System.out.println("Email cannot be empty");
        	            con.close();
        	            return;
        	        }

        	        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        	            System.out.println("Invalid email");
        	            con.close();
        	            return;
        	        }

                    
                    // 3. Seat number

                    System.out.print("Enter seat number: ");
                    int seatNumber = sc.nextInt();
                    sc.nextLine();

                    if (seatNumber <= 0 ||seatNumber > totalSeats) {

                        System.out.println("Invalid seat number");

                        con.rollback();
                        return;
                    }

                   
                    // 4. Check seat availability
                    

                    try (PreparedStatement psSeat =con.prepareStatement(checkSeat)) {

                        psSeat.setInt(1, busId);
                        psSeat.setInt(2, seatNumber);

                        try (ResultSet rsSeat =psSeat.executeQuery()) {

                            if (rsSeat.next()) {

                                System.out.println("Seat already booked");

                                con.rollback();
                                return;
                            }
                        }
                    }

                    
                    // 5. Insert passenger

                    int passengerId;
                    
                    try (PreparedStatement psPassenger =
                            con.prepareStatement(insertPassenger,java.sql.Statement.RETURN_GENERATED_KEYS)) {

                        psPassenger.setString(1, name);
                        psPassenger.setString(2, phone);
                        psPassenger.setString(3, email);

                        psPassenger.executeUpdate();

                        try (ResultSet keys =psPassenger.getGeneratedKeys()) {

                            keys.next();

                            passengerId =keys.getInt(1);
                           
                        }
                    }

                    
                    // 6. Insert booking
                    int bookingID;
                    try (PreparedStatement psBooking =con.prepareStatement(insertBooking,java.sql.Statement.RETURN_GENERATED_KEYS)) {

                        psBooking.setInt(1, busId);
                        psBooking.setInt(2, passengerId);
                        psBooking.setInt(3, seatNumber);

                        psBooking.executeUpdate();
                    
                   

                    try (ResultSet keys =
                            psBooking.getGeneratedKeys()) {

                        keys.next();

                        bookingID = keys.getInt(1);
                    }}


                    
                    // 7. Commit
            
                    con.commit();

                    System.out.println("Seat booked successfully");
                    System.out.println("booking ID  : "+bookingID);
                    System.out.println("Passenger ID: " + passengerId);

                    System.out.println("Seat Number: " + seatNumber);
                    System.out.println("Kindly remember Booking ID for getting passenger detailes or ticket cancellation");
                }
            }

        } catch (Exception e) {

            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}
