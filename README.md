# Smart Journaling Project

### Role

1.	Login / User Registration, User Class creation and Data Storage -- LI YUCHEN
2.	Welcome Page and Summary Page -- YANG YUTING
3.	Journal Page -- LI ZHAOZIYU
4.	Weather Value Extraction -- MENG HANYUE
5.	Mood Classification Value Extraction -- CHEN YUHAN

### Build and run
1. Go to folder `SmartJournal/`
2. Enter in the terminal
    ```bash
    javac -d bin src/app/*.java
    cd bin
    jar cvfm SmartJournal.jar ../MANIFEST.MF app
    ```
3. Run the .jar file
    ```bash
    java -jar SmartJournal.jar
    ```
    Tips: You may meet `Permission denied: connect` error, try adding `-Djava.net.preferIPv4Stack=true` parameter.
> You need to provide a vaild `.env` file.

## 1. Project Mission

To design and develop a "Smart Journaling" application that empowers users to improve emotional awareness and foster personal reflection. By leveraging API-driven data enrichment (weather and sentiment analysis), the project aims to provide a smart, accessible platform that helps users understand emotional patterns and promotes mental well-being, aligning with SDG 3 (Good Health and Well-being).

## 2. Primary Objectives (Core Functionality - Phase 1)

These objectives represent the minimum viable product (MVP) required to deliver the core functionalities as outlined in the project specification.

### ✅User Authentication:

- ✅Implement a secure user registration and login system based on email and password.

- ✅Persist user account data (Email, Display Name, Password) in a local text file (UserData.txt).

### ✅Core Journaling Interface (CLI):

- ✅Develop a Command-Line Interface (CLI) as the primary user interface.

- ✅Implement functionality for users to Create, View, and Edit daily journal entries.

- ✅Ensure the "Create" and "Edit" options are logically constrained to the current date ("Today").

- ✅Allow users to view any past journal entry.

### ✅Data Persistence:

- ✅Implement file I/O operations to save, load, and update journal entries from user-specific files (e.g., s100201@student.fop_journal.txt).

- ✅Ensure all data (entries, mood, weather) is saved correctly so it persists after the application is closed.

### ✅Dynamic UX Elements:

- ✅Create a time-sensitive welcome message (Good Morning, Good Afternoon, Good Evening) based on the user's current time (GMT+8).

### ✅API Integration (Automated Enrichment):

- ✅Weather API (GET): Successfully integrate the Malaysian weather data API. Automatically fetch and store the summary_forecast for the current date with the journal entry.

- ✅Sentiment API (POST): Successfully integrate the Hugging Face (DistilBERT SST-2) model API.

- ✅Implement real-time sentiment analysis on the user's journal text.

- ✅Develop value-extraction logic to parse the API JSON response and store the label with the highest score (e.g., "POSITIVE" or "NEGATIVE") as the user's "mood".

### ✅Data Review:

- ✅Develop a "Weekly Summary" page that displays an overview of the user's mood and recorded weather for the past seven days.

## 3. Secondary Objectives (Enhancements - Phase 2)

These objectives represent future enhancements to improve the project's usability, security, and scalability, as suggested in the "Extra Features" section.

### ✅Enhanced Security:

- ✅Implement a robust password hashing algorithm (e.g., bcrypt, SHA-256) to replace the plaintext password storage, protecting user credentials.

### ✅Expanding Functionalities on Existing Features


- ✅Create a mapping rule to map different possible values of the `weather_forecast` field to simpler English terms.
