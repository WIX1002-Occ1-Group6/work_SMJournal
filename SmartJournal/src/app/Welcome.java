// by YANG YUTING

package app;

import java.time.LocalTime;
import java.util.Scanner;

public class Welcome {
    Scanner scanner = new Scanner(System.in);
    User user = new User();
        
    private String getGreeting() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= 0&&hour<12) {
            return "Good Morning";
        } else if (hour>=12&& hour<17) {
            return "Good Aftermoon";
        } else { 
            return "Good Evening";
        }
    }

    public boolean displayMainMenu(String DisplayName) {
        System.out.println("\n" + getGreeting() + ", " + DisplayName + "!");
        System.out.println("--- Main Menu ---");
        System.out.println("1. Create, Edit & View Journals");
        System.out.println("2. View Weekly Mood Summary");
        System.out.println("3. Log out");
        System.out.println("Please select an option: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                // TODO: 1
                break;
            case 2:
                // TODO
                break;
            case 3:
                return false;
            default:
                break;
        }
        return true;
    }

}