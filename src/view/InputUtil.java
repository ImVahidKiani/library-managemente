package view;

import java.util.ArrayList;

public class InputUtil {

    public static int getUserChoice(ArrayList<?> options) {
    int ans;
        if (options.size() > 0) {
        do {
            for (int i = 0; i < options.size(); i++) {
                System.out.println(i + ") " + options.get(i));
            }
            System.out.println("Choose one");
            ans = InputUtil.readIntAnswer();
        } while (ans < 0 || ans >= options.size());
    } else {
        ans = -1;
    }
        return ans;
}

    // This method reads user entered integer value, it keeps asking until getting right integer value.
    public static int readIntAnswer() {
        boolean WrongAnswer = true;
        int ans = 0;
        do {
            try {
                ans = Integer.parseInt(UI.staticScanner.nextLine());
                WrongAnswer = false;
            } catch (Exception e) {
                System.out.println("\nPlease Enter Correct Answer");
            }
        } while (WrongAnswer);
        return ans;
    }
}
