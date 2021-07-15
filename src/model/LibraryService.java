package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// This class is workhorse of the application.
public class LibraryService {
    public static final long ALLOWED_BORROW_DURATION = 10 * 24 * 60 * 60 * 1000l;//10 day
    private static LibraryService libraryService = null;
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Librarian> librarians = new ArrayList<>();

    public ArrayList<Librarian> getLibrarians() {
        return librarians;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public static LibraryService getLibrary() {
        if (libraryService == null) {
            LibraryService.libraryService = new LibraryService();
        }
        return LibraryService.libraryService;
    }

    /*
      This method is used when a librarian wants to log in.
      return null if id or password is wrong and member object if there
      is librarian in librarian list of library which id and password matches.
     */
    public Librarian authorizeLibrarian(int userID, String userPassword) {
        for (Librarian librarian : librarians) {
            if (librarian.authorizeSignInInfo(userID, userPassword)) {
                return librarian;
            }
        }
        return null;
    }


    // Adds book to book list of library . return result of order
    public UIOrderResult addBook(Book book) {
        books.add(book);
        return new UIOrderResult("Added Successfully", true);
    }


    /*
      Deletes a book from library book list
      return UIOrderResult with proper message and true if book is deleted and
      false if book was borrowed and could not be deleted
     */
    public UIOrderResult deleteBookFromLib(Book book) {
        if (!books.contains(book)) {
            return new UIOrderResult("Library does not have this book", false);
        } else {
            if (book.getBorrowStatus() == BorrowStatus.AVAILABLE) {
                books.remove(book);
                return new UIOrderResult("Deleted successfully", true);
            } else {
                String message = "Book is not Available and can not be deleted while it is unavailable"
                        + "First ask member to return the book";
                return new UIOrderResult(message, false);
            }
        }

    }

    // Adds librarian to librarian list of library. return result of order
    public UIOrderResult addLibrarian(Librarian librarian) {
        librarians.add(librarian);
        return new UIOrderResult("Added Successfully", true);
    }

    /*
     Deletes a librarian from librarian list of library, before using this method
     librarian should bring back all borrowed books
     return false if librarian has borrowed book.
     */
    public UIOrderResult deleteLibrarian(Librarian librarian) {
        if (!librarians.contains(librarian)) {
            return new UIOrderResult("Library does not have such librarian", false);
        } else {
            if (librarian.getBorrowedBooks().size() > 0) {
                String message = "librarian has borrowed book and can not be deleted"
                        + "First ask to return book(s)";
                return new UIOrderResult(message, false);
            } else {
                librarians.remove(librarian);
                return new UIOrderResult("Deleted Successfully", true);
            }
        }

    }

    /*
      Manages a specific Borrow Request.
      return proper result, this will be unsuccessful if book is not available.
     */
    public UIOrderResult borrowRequest(Book book, Librarian librarian) {
        boolean bookIsAvailable = book.getBorrowStatus() == BorrowStatus.AVAILABLE;
        if (bookIsAvailable) {
            librarian.addBooktoBorrowedList(book);
            book.setBorrowStatus2Borrowed();
            book.setLibrarian(librarian);
            return new UIOrderResult("Borrowed Successfully", true);
        } else {
            return new UIOrderResult("Sorry, Book Status is " + book.getBorrowStatus(), false);
        }
    }

    /*
      Changes Borrow Status to Renewed if former status is either BORROWED or BORROW_EXPIRED.
      return result with proper message.
     */
    public UIOrderResult borrowRenewRequest(Book book) {
        if (book.getBorrowStatus() == BorrowStatus.BORROWED || book.getBorrowStatus() == BorrowStatus.BORROW_EXPIRED) {
            book.setBorrowStatus2Renewed();
            return new UIOrderResult("Borrow Renewed Successfully", true);
        } else {
            return new UIOrderResult("Book is not in proper status to be BORROW_RENEWED", false);
        }
    }

    /*
      updates status of books when time elapse and period of
      ALLOWED_BORROW_DURATION finishes.
     */
    public void updateBooksStatus() {
        long timeElapsedFromStatusChange;
        for (Book book : books) {
            timeElapsedFromStatusChange = System.currentTimeMillis() - book.getLastStatusChange();
            if (timeElapsedFromStatusChange > LibraryService.ALLOWED_BORROW_DURATION) {
                switch (book.getBorrowStatus()) {
                    case BORROWED:
                        book.setBorrowStatus2BorrowExpired();
                        break;
                    case BORROW_RENEWED:
                        book.setBorrowStatus2RenewExpired();
                        break;
                }
            }
        }
    }

    /*
      This method is called when a librarian wants to give a book back to library
      return result of order with proper message.
     */
    public UIOrderResult giveBackRequest(Book book, Librarian librarian) {
        if (librarian.getBorrowedBooks().contains(book)) {
            librarian.removeBookFromBorrowedList(book);
            book.setBorrowStatus2Awailable();
            return new UIOrderResult("Book was given back successfully", true);
        } else {
            return new UIOrderResult("Wrong Order in Give Back request", false);
        }
    }

    public static Date asDate(long millis){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String stringDate = sdf.format(millis);
            Date date = sdf.parse(stringDate);
            return date;
        }catch (ParseException pe) {
            System.out.println("have a parse exception");
        }
        return null;
    }
}
