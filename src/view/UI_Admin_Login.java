package view;


import model.Admin;

import java.util.ArrayList;
import java.util.Arrays;

public final class UI_Admin_Login extends UI {
    UI_Welcome welcomepage;
    Admin admin;

    public UI_Admin_Login(UI_Welcome welcomepage) {
        this.welcomepage = welcomepage;
        admin = Admin.getAdmin();
        createMenuItems();
    }

    @Override
    void createMenuItems() {
        menuItems = new ArrayList<>(
                Arrays.asList("Back to Welcome Page",
                        "Sign In Admin"
                ));
    }

    @Override
    public void handleUserAnswer(int ans) {
        switch (ans) {
            case 0:
                welcomepage.templateMethod();
                break;
            case 1:
                adminSignInRequest();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void adminSignInRequest() {
        System.out.println("Enter Name:");
        String adminName = staticScanner.nextLine();
        System.out.println("\nEnter Password:");
        String adminPassword = staticScanner.nextLine();
        if (Admin.authAdmin(adminName, adminPassword)) {
            UI_Admin adminpage = new UI_Admin(this);
            adminpage.templateMethod();
        } else {
            System.out.println("Admin Name or Password is Wrong!");
        }
    }


}
