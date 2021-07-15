package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Librarian {

    private static int idManager = 1000;
    private String firstName;
    private String lastName;
    private int id;
    String password;

    private ArrayList<Book> BorrowedBooks;

    public Librarian(String firstName, String lastName, String password) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.id = Librarian.idManager++;
        this.password = password.toLowerCase();
        BorrowedBooks = new ArrayList<>();
    }

    public void addBooktoBorrowedList(Book book) {
        BorrowedBooks.add(book);
    }
    public void removeBookFromBorrowedList(Book book) {
        BorrowedBooks.remove(book);
    }
    public ArrayList<Book> getBorrowedBooks() {
        return BorrowedBooks;
    }

    public static Librarian createLibrarian() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter First Name");
        String clientFirstName = scanner.nextLine().toLowerCase();

        System.out.println("\nEnter Last Name");
        String clientLastName = scanner.nextLine().toLowerCase();

        System.out.println("\nEnter Password (Lower Case)");
        String clientPassword = scanner.nextLine().toLowerCase();

        return new Librarian(clientFirstName, clientLastName, clientPassword);
    }

    @Override
    public String toString() {
        return "Librarian{" + "firstName= " + firstName + ", lastName= " + lastName + ", id= " + id + '}';
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }
    public int getId(){ return this.id;}

    public String getFullInfotoAdmin() {
        StringBuffer stb = new StringBuffer();
        stb.append("firstName=").append(firstName).append(", lastName=").append(lastName).append(", id=").append(id).append(", password=").append(password);
        if (BorrowedBooks.size() > 0) {
            stb = stb.append("\n  LIST OF BORROWED BOOKS");
            for (Book BorrowedBook : BorrowedBooks) {
                stb.append("\n     ");
                stb.append(BorrowedBook.getName());
            }
        } else {
            stb.append("\n     THIS MEMBER HAS NOT BORROWED A BOOK YET");
        }
        return stb.toString();
    }

    // Method used when a librarian wants to sign in. checks parameter ID and password.
    public boolean authorizeSignInInfo(int userID, String userPassword) {
        return (this.id == userID && (this.password == null ? userPassword == null : this.password.equals(userPassword.toLowerCase())));
    }
}
