// by CHEN YUHAN

package app;

public class MoodExtraction{
    public String extractSummaryForecast(String jsonResponse) {
        String searchKey = "\"label\":\"";
        int startIndex = jsonResponse.indexOf(searchKey) + searchKey.length();
        if (startIndex == -1) {
            return "Mood data not found";
        }
        startIndex= startIndex + searchKey.length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }
}
