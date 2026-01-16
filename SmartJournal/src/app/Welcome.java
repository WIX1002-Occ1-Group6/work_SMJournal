// by YANG YUTING

package app;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Welcome {
    Journal journal = new Journal();
        
    private String getGreeting() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= 0&&hour<12) {
            return "Good Morning";
        } else if (hour>=12&& hour<17) {
            return "Good Afternoon";
        } else { 
            return "Good Evening";
        }
    }

    public boolean displayMainMenu(String displayName, String email, Scanner input) {
        journal.clearScreen();
        System.out.println("\n" + getGreeting() + ", " + displayName + "!");
        System.out.println("=== Main Menu ===");
        System.out.println("1. Create, Edit & View Journals");
        System.out.println("2. View Weekly Mood Summary");
        System.out.println("3. Log out");
        System.out.print("\nPlease select an option:\n> ");
        int choice;
        String choiceInput = input.nextLine();
        try {
            choice = Integer.parseInt(choiceInput.trim());
        } catch (NumberFormatException e) {
            choice = -1;
        }
        switch (choice) {
            case 1 -> {
                boolean exit = false;
                while (exit == false) exit = journalDatePage(email, input);
            }
            case 2 -> {
                journal.clearScreen();
                viewWeeklyMoodSummary(email, input);
            }
            case 3 -> {
                journal.clearScreen();
                return true;
            }
            default -> {
            }
        }
        return false;
    }

    private boolean journalDatePage(String email, Scanner input) {
        int journalDateNum;
        journal.clearScreen();
        int countJournal = journal.datePage(email);
        journalDateNum = -1;
        while (journalDateNum < 0 || journalDateNum > countJournal) {
            System.out.println("\nSelect a date to view journal, or create a new journal for today:");
            System.out.print("> ");
            String inputLine = input.nextLine();
            try {
                journalDateNum = Integer.parseInt(inputLine.trim());
            } catch (NumberFormatException e) {
                journalDateNum = -1;
            }
        }
        journal.clearScreen();
        if (journalDateNum > 0) {
            boolean exit = false;
            while (exit == false) exit = journal.journalPage(journalDateNum, email, input);
        }
        return journalDateNum == 0;
    }
        
    private void viewWeeklyMoodSummary(String email, Scanner input) {
        System.out.println("=====================================");
        System.out.println("         WEEKLY MOOD SUMMARY         ");
        System.out.println("=====================================\n");
        
        
        Map<String, String> weeklyMood = journal.getWeeklyMoodData(email);
        if (weeklyMood.isEmpty()) {
            System.out.println("No journal entries found for this week!");
            System.out.println("Write a journal to track your mood  :)");
        } 
        else {
            List<String> sortedDates = new ArrayList<>(weeklyMood.keySet());
            Collections.sort(sortedDates);
            
            System.out.println("   Date         |  Your Mood");
            System.out.println("   ---------------------------");
            for (String date : sortedDates) {
                String mood = weeklyMood.get(date);
                System.out.printf("   %s   |  %s\n", date, mood);
            }
        }
        System.out.println("\n=====================================");
        System.out.print("Press Enter to return to Main Menu.\n> ");
        input.nextLine();
    }
}
