// by CHEN YUHAN

package app;

import java.util.Map;

public class MoodExtraction{
    public String getMood(String entryText) {
        API api = new API();
        String postResponse = null;
        // Load environment variables from .env file (custom loader)
        Map<String, String> env = EnvLoader.loadEnv(".env");

        try {
            // --- Example POST request: Perform sentiment analysis using HuggingFace model ---
            String journalInput = entryText;
            String postUrl = "https://router.huggingface.co/hf-inference/models/distilbert/distilbert-base-uncased-finetuned-sst-2-english";

            // Safely get bearer token
            String bearerToken = env.get("BEARER_TOKEN");
            if (bearerToken == null || bearerToken.isEmpty()) {
                System.err.println("Error: BEARER_TOKEN is not set in the environment.");
                return "Failed to get Mood";
            }

            // Format JSON body
            String jsonBody = "{\"inputs\": \"" + journalInput + "\"}";

            // Call POST
            postResponse = api.post(postUrl, bearerToken, jsonBody);
            System.out.println("\nSentiment Analysis Response:\n" + postResponse);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postResponse;
    }

    public String extractSummaryForecast(String jsonResponse) {
        String searchKey = "\"label\":\"";
        int startIndex = jsonResponse.indexOf(searchKey) + searchKey.length();
        if (startIndex == -1) {
            return "Mood data not found";
        }
        // startIndex = startIndex + searchKey.length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }
}
