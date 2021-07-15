package model;

public enum BorrowStatus {

    // Book is kept in library.
    AVAILABLE,

    // Book is borrowed by a librarian and allowed time has not finished yet. (time :10 day)
    BORROWED,

    // After one borrow, librarian has renewed borrow. librarian can keep book for 10 more day.
    BORROW_RENEWED,

    // Book is borrowed by a member and allowed time is finished.
    BORROW_EXPIRED,

    // After renewing Borrow, allowed time is elapsed and librarian should return the book to library.
    BORROW_RENEW_EXPIRED
}
