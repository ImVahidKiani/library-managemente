package view;


import model.Book;
import model.Librarian;
import model.LibraryService;
import model.UIOrderResult;

import java.util.ArrayList;
import java.util.Arrays;

public final class UI_Librarian extends UI {
    UI_Librarian_Login librarianLoginPage;
    Librarian librarian;
    LibraryService libraryService;

    public UI_Librarian(UI_Librarian_Login librarianLoginPage, Librarian librarian) {
        createMenuItems();
        this.librarianLoginPage = librarianLoginPage;
        this.librarian = librarian;
        libraryService = LibraryService.getLibrary();
    }

    @Override
    void createMenuItems() {
        menuItems = new ArrayList<>(
                Arrays.asList("Log Out",//0
                        "See All Books of Library",//1
                        "Show a Book Information",//2
                        "Show Books I Borrowed",//3
                        "Search books by author",//4
                        "Borrow a Book",//5
                        "Renew a Borrow",//6
                        "Give a Book Back"//7
                ));
    }

    @Override
    public void handleUserAnswer(int ans) {
        switch (ans) {
            case 0:
                librarianLoginPage.templateMethod();
                break;
            case 1:
                printBooks();
                break;
            case 2:
                showAbookInformation();
                break;
            case 3:
                showBorrowedBooks();
                break;
            case 4:
                searchBookByAuthor();
                break;
            case 5:
                borrowBook();
                break;
            case 6:
                borrowRenew();
                break;
            case 7:
                giveBookBack();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void printBooks() {
        if (libraryService.getBooks().size() > 0) {
            for (Book book : libraryService.getBooks()) {
                System.out.println(book.toString());
            }
        } else {
            System.out.println("Empty List");
        }
    }

    private void showAbookInformation() {
        intAns = InputUtil.getUserChoice(libraryService.getBooks());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Book book = libraryService.getBooks().get(intAns);
        System.out.println(book.getFullInfotoLibrarian());
    }

    private void showBorrowedBooks() {
        if (librarian.getBorrowedBooks().size() > 0) {
            for (Book book : librarian.getBorrowedBooks()) {
                System.out.println(book.toString()+"\n--------- "+book.getBorrowStatus());
            }
        } else {
            System.out.println("Empty List");
        }
    }

    private void searchBookByAuthor(){
        System.out.println("inter name of author :");
        String name = staticScanner.nextLine();

        ArrayList<Book> books = libraryService.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getAuthor().contains(name))
                System.out.println(books.get(i).getFullInfotoAdmin());
        }
    }

    private void borrowBook() {
        intAns = InputUtil.getUserChoice(libraryService.getBooks());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Book book = libraryService.getBooks().get(intAns);
        UIOrderResult result = libraryService.borrowRequest(book, librarian);
        System.out.println(result.getMessage());
    }

    private void borrowRenew() {
        intAns = InputUtil.getUserChoice(librarian.getBorrowedBooks());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Book book = librarian.getBorrowedBooks().get(intAns);
        UIOrderResult result = libraryService.borrowRenewRequest(book);
        System.out.println(result.getMessage());
    }

    private void giveBookBack() {
        intAns = InputUtil.getUserChoice(librarian.getBorrowedBooks());
        if (intAns == -1) {
            System.out.println("Empty List");
            return;
        }
        Book book = librarian.getBorrowedBooks().get(intAns);
        UIOrderResult result = libraryService.giveBackRequest(book, librarian);
        System.out.println(result.getMessage());
    }
}
