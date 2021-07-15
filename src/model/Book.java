package model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

// This class is representative of Books in library reservoir.
public class Book {
    private String name;
    private String author;
    private BorrowStatus borrowStatus;
    private Librarian librarian = null;

    /*
      Last status change time according to systems time is saved in this
      variable and is used for updating status of book from borrowed to
      borrow_expired and from renewed to renew_expired.
     */
    private long LastStatusChange;

    /*
      Only constructor of Book class.
      for example when a book is created it should be available until being borrowed.
     */
    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        this.borrowStatus = BorrowStatus.AVAILABLE;
        this.LastStatusChange = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public String getAuthor(){
        return author;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public long getLastStatusChange() {
        return LastStatusChange;
    }

    public BorrowStatus getBorrowStatus() {
        return borrowStatus;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public void setBorrowStatus2Borrowed() {
        if (this.borrowStatus == BorrowStatus.AVAILABLE) {
            this.borrowStatus = BorrowStatus.BORROWED;
            LastStatusChange = System.currentTimeMillis();
        } else {
            System.out.println("Wrong Order");
        }
    }

    public void setBorrowStatus2BorrowExpired() {
        if (this.borrowStatus == BorrowStatus.BORROWED) {
            this.borrowStatus = BorrowStatus.BORROW_EXPIRED;
            LastStatusChange = System.currentTimeMillis();
        } else {
            System.out.println("Wrong Order");
        }
    }

    public void setBorrowStatus2Renewed() {
        if (this.borrowStatus == BorrowStatus.BORROWED || this.borrowStatus == BorrowStatus.BORROW_EXPIRED) {
            this.borrowStatus = BorrowStatus.BORROW_RENEWED;
            LastStatusChange = System.currentTimeMillis();
        } else {
            System.out.println("Wrong Order");
        }
    }

    public void setBorrowStatus2RenewExpired() {
        if (this.borrowStatus == BorrowStatus.BORROW_RENEWED) {
            this.borrowStatus = BorrowStatus.BORROW_RENEW_EXPIRED;
            LastStatusChange = System.currentTimeMillis();
        } else {
            System.out.println("Wrong Order");
        }
    }

    public void setBorrowStatus2Awailable() {
        if (this.borrowStatus != BorrowStatus.AVAILABLE) {
            this.borrowStatus = BorrowStatus.AVAILABLE;
            LastStatusChange = System.currentTimeMillis();
        } else {
            System.out.println("Wrong Order");
        }
    }

    @Override
    public String toString() {
        return "Book{" + "name=" + name + ", author=" + author + '}';
    }

    public String getFullInfotoLibrarian() {
        return "Book{" + "name=" + name + ", author=" + author + ", borrowStatus=" + borrowStatus + '}';
    }

    public String getFullInfotoAdmin() {
        StringBuffer stb = new StringBuffer();
        stb.append("name=").append(name).append(", author=").append(author).append(", borrow status=").append(borrowStatus);
        if (borrowStatus == BorrowStatus.BORROWED || borrowStatus == BorrowStatus.BORROW_RENEWED) {
            stb = stb.append(", book keeper=").append(librarian.getName());
        } else {
            stb.append("    kept in library");
        }
        return stb.toString();
    }

    /*
      This method helps admin and librarian to add book to library.
      return Book object created by constructor in this method.
     */
    public static Book createBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter books Name");
        String bookName = scanner.nextLine();

        System.out.println("\nEnter Author Name");
        String bookAuthor = scanner.nextLine();

        return new Book(bookName, bookAuthor);
    }

    public void updateBook(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nChange name from " + name + " to : ");
        name = scanner.nextLine();

        System.out.print("\nChange Author from " + author + " to : ");
        author = scanner.nextLine();
    }


}
