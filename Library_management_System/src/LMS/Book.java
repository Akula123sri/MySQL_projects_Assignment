package LMS;

public class Book {
private int bookid;
private String bookname;
private String author;
private boolean issued;
public int getBookid() {
	return bookid;
}
public void setBookid(int bookid) {
	this.bookid = bookid;
}
public String getBookname() {
	return bookname;
}
public void setBookname(String bookname) {
	this.bookname = bookname;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
}
public boolean isIssued() {
	return issued;
}
public void setIssued(boolean issued) {
	this.issued = issued;
}
Book(int bookid,String bookname,String author){
	this.bookid=bookid;
	this.bookname=bookname;
	this.author=author;
	this.issued=false;
}
public void displayBook() {
	System.out.println(bookid+" "+bookname+" "+author+" "+(issued?"issued":"available"));
}


}
