package view;


import java.util.ArrayList;
import java.util.Arrays;

public final class UI_Welcome extends UI{
    public UI_Welcome() {
        createMenuItems();
    }

    @Override
    void createMenuItems() {
        menuItems = new ArrayList<>(
                Arrays.asList("Exit",
                        "I'm Admin",
                        "I'm Librarian"
                ));
    }

    @Override
    public void handleUserAnswer(int ans) {
        switch (ans) {
            case 0:
                System.exit(0);
                break;
            case 1:
                UI_Admin_Login adminLogin= new UI_Admin_Login(this);
                adminLogin.templateMethod();
                break;
            case 2:
                UI_Librarian_Login librarianLogin =new UI_Librarian_Login(this);
                librarianLogin.templateMethod();
            default:
                throw new AssertionError();
        }
    }
}
