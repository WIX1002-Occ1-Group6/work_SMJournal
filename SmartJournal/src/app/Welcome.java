// by YANG YUTING

package app;

import java.time.LocalTime;
import java.util.Scanner;

public class Welcome {
    Journal journal = new Journal();
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

    public boolean displayMainMenu(String displayName, String email) {
        journal.clearScreen();
        System.out.println("\n" + getGreeting() + ", " + displayName + "!");
        System.out.println("=== Main Menu ===");
        System.out.println("1. Create, Edit & View Journals");
        System.out.println("2. View Weekly Mood Summary");
        System.out.println("3. Log out");
        System.out.print("Please select an option:\n> ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                boolean exit = false;
                while (exit == false) exit = journalDatePage(email);
                break;
            case 2:
                // TODO
                break;
            case 3:
                journal.clearScreen();
                return false;
            default:
                break;
        }
        return true;
    }

    private boolean journalDatePage(String email) {
        journal.clearScreen();
        int countJournal = journal.datePage(email);
        int journalDateNum = -1;
        while (journalDateNum < 0 || journalDateNum > countJournal) {
            System.out.println("\nSelect a date to view journal, or create a new journal for today:");
            System.out.print("> ");
            journalDateNum = scanner.nextInt();
        }
        if (journalDateNum > 0) {
            boolean exit = false;
            while (exit == false) exit = journal.journalPage(journalDateNum, email);
        }
        return journalDateNum == 0;
    }
}