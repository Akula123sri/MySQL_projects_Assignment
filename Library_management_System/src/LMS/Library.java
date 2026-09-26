package LMS;

import java.util.ArrayList;

public class Library {
 ArrayList<Book> books=new ArrayList();
 public void addBook(Book book) {
	 books.add(book);
	 System.out.println("book added successfully");
 }
 public void displayBooks() {
	 if(books.isEmpty()) {
		 System.out.println("No books available");
		 return;
	 }
	 for(Book book:books) {
		 book.displayBook();
	 }
 }
 public void searchbook(int bookid) {
	 for(Book book:books) {
		 if(book.getBookid()==bookid) {
			 book.displayBook();
			 return;
		 }
	 }
	 System.out.println("Book not found");
 }
 public void issuedBook(int bookid) {
	 for(Book book:books) {
		 if(book.getBookid()==bookid) {
			 if(book.isIssued()) {
				 System.out.println("Book is already Issued");
			 }else {
				 book.setIssued(true);
				 System.out.println("Book issued successfully");
			 }
			 return;
		 }
	 }
	 System.out.println("Book not found");
 }
 public void returnBook(int bookid) {
	 for(Book book:books) {
		 if(book.getBookid()==bookid) {
			 if(!book.isIssued()) {
				 System.out.println("Book was not Issued");
			 }else {
				 book.setIssued(false);
				 System.out.println("Book returned successfully");
			 }
			 return;
	 }
 }
	 System.out.println("Book not found");
 }
 
}
