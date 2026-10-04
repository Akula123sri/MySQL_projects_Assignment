package Bus_Booking_Application;

import java.util.Scanner;

public class Bus_Reservation_Main_menu {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		while (true) {

            System.out.println("\n========== BUS RESERVATION SYSTEM ==========");
            System.out.println("1. Search Buses");
            System.out.println("2. Seat Booking");
            System.out.println("3. Ticket Cancellation");
            System.out.println("4. Passenger Details");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    Search_Bus.searchBuses(sc);
                    break;

                case 2:
                    Seat_Booking.bookSeat(sc);
                    break;

                case 3:
                    Ticket_Cancelation.cancelTicket(sc);
                    break;

                case 4:
                    Passenger_Detailes.getPassengerDetails(sc);
                    break;

                case 5:
                    System.out.println(
                            "Thank you for using Bus Reservation System"
                    );
                    sc.close();
                    return;

                default:
                    System.out.println(
                            "Invalid choice"
                    );
            }
        }

	}

}
