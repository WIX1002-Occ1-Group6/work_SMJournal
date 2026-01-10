// by LI YUCHEN

package app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class User {
    
    private String email, displayName;
    private static final String DATAFILE = "UserData/UserData.txt";

    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getEmail() {
        return this.email;
    }

    public boolean register(Scanner input) {
        try (
            PrintWriter outputStream = new PrintWriter(new FileOutputStream(DATAFILE,true));
        ) {
            Map<String, List<String>> userMap = new HashMap<>();
            readData(userMap);
            List<String> emailList = new ArrayList<>(userMap.keySet());

            System.out.print("Enter email: ");
            this.email = input.nextLine();
            if (!this.email.contains("@")) {
                clearScreen();
                System.out.println("Invaild email.");
                return false;
            }

            if (emailList.contains(this.email)) {
                clearScreen();
                System.out.println("You have already registered. Please use log in.");
                return false;
            }

            do {
                System.out.print("Enter display name: ");
                this.displayName = input.nextLine();
            } while (this.displayName.equals(""));

            
            String passwd = "";
            do {
                System.out.print("Enter password: ");
                passwd = input.nextLine();
            } while (passwd.equals(""));
            String encrypt = getSHA256(passwd);

            outputStream.println("\n===\n===\n");
            outputStream.println(this.email);
            outputStream.println(this.displayName);
            outputStream.println(encrypt);
            clearScreen();
            System.out.println("You have successfully registered!");
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean login(Scanner input) {
        try (
            PrintWriter outputStream = new PrintWriter(new FileOutputStream(DATAFILE,true));
        ) {
            outputStream.close();
            Map<String, List<String>> userMap = new HashMap<>();
            readData(userMap);
            List<String> emailList = new ArrayList<>(userMap.keySet());

            System.out.print("Enter email: ");
            this.email = input.nextLine();
            System.out.print("Enter password: ");
            String encrypt = getSHA256(input.nextLine());

            if (emailList.contains(this.email)) {
                List<String> privacyList = userMap.get(this.email);
                if (privacyList.get(1).equals(encrypt)) {
                    this.displayName = privacyList.get(0);
                    clearScreen();
                    System.out.println("Login successful!");
                    return true;
                }
            }
            clearScreen();
            System.out.println("Email or password incorrect!");
            return false;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private String getSHA256(String plaintext) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
            byte[] hash = md.digest(plaintext.concat("ThisIsSalt").getBytes(StandardCharsets.UTF_8));
            String hexString = "";
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                // Pad with a leading zero
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                hexString += hex;
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Failed to get SHA256 for password, now using plaintext");
            return plaintext;
        }
    }

    private void readData(Map<String, List<String>> map) {
        try {
            String file = new String(Files.readAllBytes(Paths.get(DATAFILE)));
            file = file.replace("\r\n", "\n"); // Replace for Windows
            String[] blocks = file.split("\n===\n===\n");
            for (String block : blocks) {
                block = block.trim();
                if (block.isEmpty()) continue;
                String[] lines = block.split("\n");
                if (lines.length >= 3) {
                    String emailKey = lines[0];
                    List<String> privacyKey = new ArrayList<>();
                    privacyKey.add(lines[1].trim());
                    privacyKey.add(lines[2].trim());
                    map.put(emailKey, privacyKey);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
