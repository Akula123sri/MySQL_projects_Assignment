package LMS;
import java.util.*;
public class Library_management {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		Library library=new Library();
		while(true) {
			System.out.println("=====Library Management System====");
			System.out.println("1. Add Book");
			System.out.println("2. Display Books");
			System.out.println("3. Search Book");
			System.out.println("4. Issue Book");
			System.out.println("5. Return Book");
			System.out.println("6. Exit LMS");
			System.out.println("Enter your choice");
			int choice=sc.nextInt();
			switch(choice) {
			case 1:
				System.out.println("Enter Book id :");
				int id=sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Book Name :");
				String name=sc.nextLine();
				System.out.println("Enter Author name :");
				String author=sc.nextLine();
				Book book=new Book(id,name,author);
				library.addBook(book);
				break;
			case 2:
				library.displayBooks();
				break;
			case 3:
				System.out.println("Enter book id :");
				int bookid=sc.nextInt();
				library.searchbook(bookid);
				break;
			case 4:
				System.out.println("Enter book id :");
				int bookid1=sc.nextInt();
				library.issuedBook(bookid1);
				break;
			case 5:
				System.out.println("Enter book id :");
				int id1=sc.nextInt();
				library.returnBook(id1);
				break;
			case 6:
				System.out.println("Thank you ");
				sc.close();
				return;
				default:
					System.out.println("Invalid choice");
			}
		}

	}

}
