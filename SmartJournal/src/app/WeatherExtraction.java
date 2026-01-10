// by MENG HANYUE

package app;

import java.time.LocalDate;

public class WeatherExtraction{
    public String extractSummaryForecast(String jsonResponse) {
        String searchKey = "\"summary_forecast\":\"";
        int startIndex = jsonResponse.indexOf(searchKey) + searchKey.length();
        if (startIndex == -1) {
            return "Weather data not found";
        }
        startIndex= startIndex + searchKey.length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }
}
    
