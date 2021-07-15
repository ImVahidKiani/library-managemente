package view;

import model.Book;
import model.BorrowStatus;
import model.Librarian;
import model.LibraryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

;

public final class UI_Reporting extends UI {
    UI_Admin adminPage;
    LibraryService libraryService;

    public UI_Reporting(UI_Admin adminPage) {
        this.adminPage = adminPage;
        this.libraryService = LibraryService.getLibrary();
        createMenuItems();
    }

    @Override
    void createMenuItems() {
        menuItems = new ArrayList<>(
                Arrays.asList("Back to admin page",//0
                        "Search for a date and show books to be returned on that date",//1
                        "Search for two date and show books to be returned between those dates",//2
                        "Search for librarian with name",//3
                        "search for librarian with id",//4
                        "Search for book with name \n",//5
                        "Show borrowed books",//6
                        "Show available books",//7
                        "show deadline of a borrowed book"
                ));
    }

    @Override
    public void handleUserAnswer(int ans) {
        switch (ans) {
            case 0://Back to admin menu
                adminPage.templateMethod();
                break;
            case 1://Search a date
                searchDate();
                break;
            case 2://Search two date
                search2Date();
                break;
            case 3://Search librarian
                searchNameLibrarian();
                break;
            case 4:
                searchIdLibrarian();
                break;
            case 5://Search books
                searchNameBook();
                break;
            case 6:
                showBorrowedBooks();
                break;
            case 7:
                showAvailableBooks();
                break;
            case 8:
                showDeadlineOfBorrowedBooks();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void searchDate() {
        System.out.println("inter date : (yyyy/MM/dd)");
        String input = staticScanner.nextLine();
        int count =0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date inputDate = sdf.parse(input);
            ArrayList<Book> books = libraryService.getBooks();

            for (int i = 0; i < books.size(); i++) {

                String borrowDuration = sdf.format(books.get(i).getLastStatusChange() + LibraryService.ALLOWED_BORROW_DURATION);
                Date borrowDurationDate = sdf.parse(borrowDuration);

                if (inputDate.equals(borrowDurationDate) &&
                        (books.get(i).getBorrowStatus() == BorrowStatus.BORROWED || books.get(i).getBorrowStatus() == BorrowStatus.BORROW_RENEWED)) {
                    System.out.println(books.get(i).getFullInfotoAdmin());
                    count++;
                }
            }
            if (count==0)
                System.out.println("Empty list");
        } catch (ParseException pe) {
            System.out.println("wrong format for date");
        }
    }

    private void search2Date() {
        System.out.println("inter dates (yyyy/MM/dd) \nfrom :");
        String from = staticScanner.nextLine();
        System.out.println("to :");
        String to = staticScanner.nextLine();
        int count = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date fromDate = sdf.parse(from);
            Date toDate = sdf.parse(to);
            ArrayList<Book> books = libraryService.getBooks();

            for (int i = 0; i < books.size(); i++) {
                String borrowDuration = sdf.format(books.get(i).getLastStatusChange() + LibraryService.ALLOWED_BORROW_DURATION);
                Date borrowDurationDate = sdf.parse(borrowDuration);

                if (borrowDurationDate.after(fromDate) && borrowDurationDate.before(toDate)
                        && (books.get(i).getBorrowStatus() == BorrowStatus.BORROWED || books.get(i).getBorrowStatus() == BorrowStatus.BORROW_RENEWED)) {
                    System.out.println(books.get(i).getFullInfotoAdmin());
                    count++;
                }
            }
            if (count == 0)
                System.out.println("Empty list");
        } catch (ParseException pe) {
            System.out.println("wrong format for date");
        }
    }

    private void searchNameLibrarian() {
        System.out.println("inter name of librarian : ");
        String name = staticScanner.nextLine();

        ArrayList<Librarian> librarians = libraryService.getLibrarians();
        for (int i = 0; i < librarians.size(); i++) {
            if (librarians.get(i).getName().contains(name))
                System.out.println(librarians.get(i).getFullInfotoAdmin());
        }
    }

    private void searchIdLibrarian() {
        System.out.println("inter id of librarian : ");
        int id = staticScanner.nextInt();

        ArrayList<Librarian> librarians = libraryService.getLibrarians();
        for (int i = 0; i < librarians.size(); i++) {
            if (librarians.get(i).getId() == id)
                System.out.println(librarians.get(i).getFullInfotoAdmin());
        }
    }

    private void searchNameBook() {
        System.out.println("inter name of book : ");
        String name = staticScanner.nextLine();

        ArrayList<Book> books = libraryService.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equals(name))
                System.out.println(books.get(i).getFullInfotoAdmin());
        }
    }

    private void showBorrowedBooks() {
        ArrayList<Book> books = libraryService.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBorrowStatus() == BorrowStatus.BORROWED)
                System.out.println(books.get(i).getFullInfotoAdmin());
        }
    }

    private void showAvailableBooks() {
        ArrayList<Book> books = libraryService.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBorrowStatus() == BorrowStatus.AVAILABLE)
                System.out.println(books.get(i).getFullInfotoAdmin());
        }
    }

    private void showDeadlineOfBorrowedBooks() {
        System.out.println("inter book name: ");
        String name = staticScanner.nextLine();
        long deadline;
        int count=0;

        ArrayList<Book> books = libraryService.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equals(name)) {
                if (books.get(i).getBorrowStatus() == BorrowStatus.BORROWED || books.get(i).getBorrowStatus() == BorrowStatus.BORROW_RENEWED) {
                    deadline = books.get(i).getLastStatusChange() + LibraryService.ALLOWED_BORROW_DURATION;
                    System.out.println(LibraryService.asDate(deadline));
                    count++;
                }
            }
        }
        if(count==0)
            System.out.println("this book is AVAILABLE or not exist in library");
    }

}