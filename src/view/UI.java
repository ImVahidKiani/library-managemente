package view;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

// Super class of all UI classes which is used in implementing view part of this project.
public abstract class UI {
    ArrayList<String> menuItems;
    static Scanner staticScanner = new Scanner(System.in);
    int intAns;

    public UI() { }

    abstract void createMenuItems();
    public abstract void handleUserAnswer(int ans) throws ParseException;

    /*
      This method is executed as the first method when application wants to use
      each of UI concrete implementation classes.
      The main work flow is to print menu, get users choice, manage request and return back to this menu.
     */
    public void templateMethod()  {
        int ans;
        ans = InputUtil.getUserChoice(menuItems);
        if (ans == -1) {
            System.out.println("Empty List");
            return;
        }
        try {
            handleUserAnswer(ans);
        }catch (ParseException pe){
            System.out.println("have a parse exception"); //in UI_Reporting class on searchDate() and search2Date()
        }
        if (ans > 0) {
            returnToMenuRequest();
        }
    }

    // After managing a request this method is called to manage going back to menu.
    private void returnToMenuRequest() {
        System.out.println("\nPress Enter to return to Menu");
        String notUsedString = staticScanner.nextLine();
        this.templateMethod();
    }
}
