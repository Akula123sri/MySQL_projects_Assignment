package Bus_Booking_Application;

import java.sql.Connection;
import java.sql.Statement;

public class Tables_Creation {
public static void main(String[] args) {
	try {
		Connection con=Db_Connection.getConnection();
		String bus="""
				create table buses(
				bus_id int primary key auto_increment,
				bus_name varchar(50) not null,
				source varchar(50) not null,
				destination varchar(50) not null,
				total_seats int not null check(total_seats>0))
				""";
		String Passenger="""
				create table passengers(
				passenger_id int primary key auto_increment,
				pname varchar(50) not null,
				phone varchar(10) not null unique,
				email varchar(50) not null unique)
				""";
		String booking="""
				create table bookings(
				booking_id int primary key auto_increment,
				bus_Id int not null ,
				passenger_Id int not null,
				seat_number int not null,
				booking_status varchar(50) not null,
				booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
				FOREIGN KEY (bus_Id)
	            REFERENCES buses(bus_id),
	            FOREIGN KEY (passenger_Id)
	            REFERENCES passengers(passenger_id))
				""";
		Statement screate=con.createStatement();
		screate.executeUpdate(bus);
		screate.executeUpdate(Passenger);
		screate.executeUpdate(booking);
		System.out.println("tables created");
	} catch (Exception e) {
		
		e.printStackTrace();
		
	}
}
}
