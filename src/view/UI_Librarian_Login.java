package view;

import model.Librarian;
import model.LibraryService;

import java.util.ArrayList;
import java.util.Arrays;

public final class UI_Librarian_Login extends UI {
    UI_Welcome welcome;
    LibraryService libraryService;

    public UI_Librarian_Login (UI_Welcome welcome) {
        this.welcome = welcome;
        createMenuItems();
        this.libraryService = LibraryService.getLibrary();
    }

    @Override
    void createMenuItems() {
        menuItems=new ArrayList<>(
                Arrays.asList("Back to welcome Menu",//0
                        "Sign In",
                        "Sign Up"
                ));
    }

    @Override
    public void handleUserAnswer(int ans) {
        switch (ans) {
            case 0://Back to Main Menu
                welcome.templateMethod();
                break;
            case 1:
                signIn();
                break;
            case 2:
                signUp();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void signIn(){
        System.out.println("Enter your ID:");
        int userID=InputUtil.readIntAnswer();
        System.out.println("\nEnter your Password:");
        String userPassword=staticScanner.nextLine();
        Librarian librarian =libraryService.authorizeLibrarian(userID, userPassword);
        if(librarian!=null){
            UI_Librarian UI_librarian =new UI_Librarian(this, librarian);
            UI_librarian.templateMethod();
        }else{
            System.out.println("Id Or UserName is Wrong!");
        }
    }

    private void signUp() {
        Librarian librarian = Librarian.createLibrarian();
        libraryService.addLibrarian(librarian);
        System.out.println("You signed up Successfully, Information: \n"+librarian.toString());
    }
}
