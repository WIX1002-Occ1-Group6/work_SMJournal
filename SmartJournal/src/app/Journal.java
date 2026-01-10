// by LI ZHAOZIYU

package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Journal {
    private static final Scanner input = new Scanner(System.in);
    private static final ZoneId timezone = ZoneId.of("Asia/Kuala_Lumpur");
    private static final ZonedDateTime now = ZonedDateTime.now(timezone);
    private static final LocalDate today = now.toLocalDate();

    private String date = "";
    private int countJournal = 1;
    private boolean isTodayNoJournal = false;
    private String journalFile = "UserData/null_journal.txt";

    public int datePage(String email) {
        this.journalFile = "UserData/" + email + "_journal.txt";
        Map<String, List<String>> journalMap = new HashMap<>();
        readData(journalMap);
        List<String> dateList = new ArrayList<>(journalMap.keySet());
        Collections.sort(dateList);
        this.countJournal = dateList.size() + 1;
        System.out.println("\n=== Journal Dates ===");
        System.out.println("0. Return to Main Menu");
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            System.out.print(i + 1 + ". " + date);
            if (date.equals(today.toString())) {
                System.out.println(" (Today)");
            } else System.out.println();
        }
        if (!dateList.contains(today.toString())) {
            System.out.println(this.countJournal + ". (No journal for today, create one!)");
            this.isTodayNoJournal = true;
        } else this.countJournal--;
        return this.countJournal;
    }

    public boolean journalPage(int journalDateNum, String email) {
        WeatherExtraction weatherExtraction = new WeatherExtraction();
        MoodExtraction moodExtraction = new MoodExtraction();
        Map<String, List<String>> journalMap = new HashMap<>();
        readData(journalMap);
        List<String> dateList = new ArrayList<>(journalMap.keySet());
        Collections.sort(dateList);
        if (journalDateNum <= journalMap.size()) this.date = dateList.get(journalDateNum - 1);

        try (PrintWriter outputStream = new PrintWriter(new FileOutputStream(this.journalFile,true));) {
            if (this.isTodayNoJournal && journalDateNum == this.countJournal) {
                String entryText = "";
                do {
                    System.out.println("\nEnter your journal entry for " + today + ": ");
                    System.out.print("> ");
                    entryText = input.nextLine();
                } while (checkNoInput(entryText));
                System.out.println("Processing...");
                //String jsonMood = getMood(entryText);
                //String jsonWeather = getWeather();
                String mood = "mood";//moodExtraction.extractSummaryForecast(jsonMood);
                String weather = "bbb";//weatherInEng(weatherExtraction.extractSummaryForecast(jsonWeather));

                outputStream.println("\n===\n===\n");
                outputStream.println(today);
                outputStream.println(weather);
                outputStream.println(mood);
                outputStream.println(entryText);
                clearScreen();
                System.out.println("Journal saved successfully!");
                this.isTodayNoJournal = false;
                outputStream.close();
                journalPage(journalDateNum, email);
            } else if (journalDateNum == this.countJournal) {
                System.out.println("\n=== Journal Entry for " + this.date + " ===");
                System.out.println("Would you like to:");
                System.out.println("1. View Journal");
                System.out.println("2. Edit Journal");
                System.out.println("3. Back to Dates");
                System.out.print("\n> ");

                List<String> journalList = journalMap.get(this.date);
                String journalEditChoice = input.nextLine();
                switch (journalEditChoice) {
                    case "1":
                        clearScreen();
                        System.out.println("\n=== Journal Entry for " + this.date + " ===");
                        System.out.println("Weather: " + journalList.get(0));
                        System.out.println("Mood: " + journalList.get(1));
                        System.out.println(journalList.get(2));
                        System.out.print("\nPress Enter to go back.\n> ");
                        input.nextLine();
                        clearScreen();
                        break;
                    case "2":
                        clearScreen();
                        editJournal();
                        break;
                    case "3":
                        clearScreen();
                        return true;
                    default:
                        clearScreen();
                        System.out.println("\nInvaild input.");
                        break;
                }
            } else {
                List<String> journalList = journalMap.get(this.date);
                System.out.println("\n=== Journal Entry for " + this.date + " ===");
                System.out.println("Weather: " + journalList.get(0));
                System.out.println("Mood: " + journalList.get(1));
                System.out.println(journalList.get(2));
                System.out.print("\nPress Enter to go back.\n> ");
                input.nextLine();
                clearScreen();
            }
        }
        catch (Exception e) {
            System.out.println("Problem with file!!");
        }
        return false;
    }

    public void editJournal() {
        // Read & write n-1 line to temp file, then add 1 new line in temp, then rewrite temp file into original file
        File originalFile = new File(this.journalFile);
        File tempFile = new File("UserData/temp.txt");
        try (
                Scanner inputStream = new Scanner(new FileInputStream(originalFile));
                PrintWriter outputStream = new PrintWriter(new FileOutputStream(tempFile));
        ) {
            String lineBuffer = inputStream.nextLine();
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();
                outputStream.println(lineBuffer);
                lineBuffer = line;
            }
            String editInput = "";
            do {
                System.out.println("\nEdit your journal entry for " + this.date + ":");
                System.out.print("> ");
                editInput = input.nextLine();
            } while (checkNoInput(editInput));
            outputStream.print(editInput);
        } catch (Exception e) {
            System.out.println("Problem with file!!");
        }
        try (
                Scanner inputStream = new Scanner(new FileInputStream(tempFile));
                PrintWriter outputStream = new PrintWriter(new FileOutputStream(originalFile));
        ) {
            while (inputStream.hasNextLine()) {
                outputStream.println(inputStream.nextLine());
            }
            clearScreen();
            System.out.println("Journal saved successfully!");
        } catch (Exception e) {
            System.out.println("Problem with file!!");
        }
    }
    private void readData(Map<String, List<String>> map) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(journalFile)));
            content = content.replace("\r\n", "\n"); // Unify Windows(\r\n) Linux(\n) difference
            String[] blocks = content.split("\n===\n===\n");
            for (String block : blocks) {
                block = block.trim(); // Discard space
                if (block.isEmpty()) continue;
                String[] lines = block.split("\n");
                if (lines.length >= 4) {
                    String dateKey = lines[0].trim();
                    List<String> journalKey = new ArrayList<>();
                    journalKey.add(lines[1].trim());
                    journalKey.add(lines[2].trim());
                    journalKey.add(lines[3].trim());
                    map.put(dateKey, journalKey);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean checkNoInput(String inputLine) {
        if (inputLine.equals("")) {
            System.out.println("Invaild input. Please try again.");
            return true;
        }
        else return false;
    }
    private static String weatherInEng(String malay) {
        String eng = null;
        switch (malay) {
            case "Berjerebu" -> eng = "Hazy";
            case "Tiada hujan" -> eng = "No rain";
            case "Hujan" -> eng = "Rain";
            case "Hujan di beberapa tempat" -> eng = "Scattered rain";
            case "Hujan di satu dua tempat" -> eng = "Isolated Rain";
            case "Hujan di satu dua tempat di kawasan pantai" -> eng = "Isolated rain over coastal areas";
            case "Hujan di satu dua tempat di kawasan pedalaman" -> eng = "Isolated rain over inland areas";
            case "Ribut petir" -> eng = "Thunderstorms";
            case "Ribut petir di beberapa tempat" -> eng = "Scattered thunderstorms";
            case "Ribut petir di beberapa tempat di kawasan pedalaman" -> eng = "Scattered thunderstorms over inland areas";
            case "Ribut petir di satu dua tempat" -> eng = "Isolated thunderstorms";
            case "Ribut petir di satu dua tempat di kawasan pantai" -> eng = "Isolated thunderstorms over coastal areas";
            case "Ribut petir di satu dua tempat di kawasan pedalaman" -> eng = "Isolated thunderstorms over inland areas";
            default -> eng = malay;
        }
        return eng;
    }
    
    public Map<String, String> getAllMoodData(String email) {
        
        this.journalFile = "UserData/" + email + "_journal.txt";
        Map<String, List<String>> journalMap = new HashMap<>();
        
        readData(journalMap);
        
       
        Map<String, String> moodMap = new HashMap<>();
        for (String date : journalMap.keySet()) {
            List<String> journalContent = journalMap.get(date);
            String mood = journalContent.get(1); 
            moodMap.put(date, mood);
        }
        return moodMap;
    }

    
    public Map<String, String> getWeeklyMoodData(String email) {
        Map<String, String> allMoodMap = getAllMoodData(email);
        Map<String, String> weeklyMoodMap = new HashMap<>();
        
        
        for (String dateStr : allMoodMap.keySet()) {
            LocalDate journalDate = LocalDate.parse(dateStr); 
           
            if (isDateInCurrentWeek(journalDate)) {
                weeklyMoodMap.put(dateStr, allMoodMap.get(dateStr));
            }
        }
        return weeklyMoodMap;
    }

    
    private boolean isDateInCurrentWeek(LocalDate date) {
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1); 
        LocalDate endOfWeek = startOfWeek.plusDays(6); 
        
        return !date.isBefore(startOfWeek) && !date.isAfter(endOfWeek);
    }
    void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
