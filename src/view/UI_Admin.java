package view;


import model.*;

import java.util.ArrayList;
import java.util.Arrays;

/*
  This class is a concrete subclass of UI and manages UI for a Admin user.
  Template method of this class is called after an admin signs in using methods
  in UI_Admin_Login class.
 */
public final class UI_Admin extends UI {
    UI_Admin_Login adminLoginPage;
    LibraryService libraryService;

    public UI_Admin(UI_Admin_Login adminLoginPage) {
        this.adminLoginPage = adminLoginPage;
        createMenuItems();
        this.libraryService = LibraryService.getLibrary();
    }

    @Override
    void createMenuItems() {
        menuItems = new ArrayList<>(
                Arrays.asList("Sign Out Admin",//0
                        "Print Books List",//1
                        "Add a new Book to library",//2
                        "Show a Book Information",//3
                        "Update Specific Book Information",//4
                        "Delete a Book from Library Inventory\n",//5
                        "Print member List",//6
                        "Read a member Information",//7
                        "Delete a member form member List",//8
                        "Advanced reporting"//9
                ));

    }

    @Override
    public void handleUserAnswer(int ans) {
        switch (ans) {
            case 0: //Back to Main Menu
                adminLoginPage.templateMethod();
                break;
            case 1://Print Books List
                printBooks();
                break;
            case 2://Add a new Book to library
                createBook();
                break;
            case 3://Show a Book Information
                showABookInformation();
                break;
            case 4://Update Specific Book Information
                updateBookSpecification();
                break;
            case 5://Delete a Book from Library Inventory
                deleteBook();
                break;
            case 6://Print Clients List
                showLibrarian();
                break;
            case 7://Read a Clients Information
                showLibrarianInformation();
                break;
            case 8://Delete a Librarian form Librarian List
                deleteLibrarian();
                break;
            case 9://go to advanced reporting page
                advancedReporting();
            default:
                throw new AssertionError();
        }
    }

    private void printBooks() {
        if (libraryService.getBooks().size() > 0) {
            String bookKeeper;
            for (Book book : libraryService.getBooks()) {
                bookKeeper = (book.getBorrowStatus() == BorrowStatus.AVAILABLE) ? " Kept in Library" : " Borrowed By: " + book.getLibrarian().getName();
                System.out.println(book.toString() + bookKeeper);
            }
        } else {
            System.out.println("Empty List");
        }
    }

    private void createBook() {
        Book book = Book.createBook();
        libraryService.addBook(book);
        System.out.println("Book " + book.getName() + " Added to Library Successfully");
    }

    private void showABookInformation() {
        intAns = InputUtil.getUserChoice(libraryService.getBooks());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Book book = libraryService.getBooks().get(intAns);
        System.out.println(book.getFullInfotoAdmin());
    }

    private void updateBookSpecification() {
        intAns = InputUtil.getUserChoice(libraryService.getBooks());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Book book = libraryService.getBooks().get(intAns);
        book.updateBook();
        System.out.println("Book Updated Successfully");

    }


    private void deleteBook() {
        intAns = InputUtil.getUserChoice(libraryService.getBooks());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Book book = libraryService.getBooks().get(intAns);
        UIOrderResult result = libraryService.deleteBookFromLib(book);
        System.out.println(result.getMessage());
    }

    private void showLibrarian() {
        if (libraryService.getLibrarians().size() > 0) {
            for (Librarian librarian : libraryService.getLibrarians()) {
                System.out.println(librarian.toString());
            }
        } else {
            System.out.println("Empty List");
        }

    }

    private void showLibrarianInformation() {
        intAns = InputUtil.getUserChoice(libraryService.getLibrarians());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Librarian librarian = libraryService.getLibrarians().get(intAns);
        System.out.println(librarian.getFullInfotoAdmin());
    }

    private void deleteLibrarian() {
        intAns = InputUtil.getUserChoice(libraryService.getLibrarians());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Librarian librarian = libraryService.getLibrarians().get(intAns);
        UIOrderResult result = libraryService.deleteLibrarian(librarian);
        System.out.println(result.getMessage());
    }

    private void advancedReporting(){
        UI_Reporting reportingPage = new UI_Reporting(this);
        reportingPage.templateMethod();
    }
}
