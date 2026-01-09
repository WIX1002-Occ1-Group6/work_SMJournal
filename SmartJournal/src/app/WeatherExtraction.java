// by MENG HANYUE

package app;

import java.time.LocalDate;

 class WeatherAPI {
        
        public String getTodayWeather() {
           
            return "{\"location\":{\"location_id\":\"St009\",\"location_name\":\"WP Kuala Lumpur\"}," +
                    "\"date\":\"2025-10-11\"," +
                    "\"morning_forecast\":\"Ribut petir di beberapa tempat\"," +
                    "\"afternoon_forecast\":\"Ribut petir di beberapa tempat\"," +
                    "\"night_forecast\":\"Tiada hujan\"," +
                    "\"summary_forecast\":\"Ribut petir di beberapa tempat\"," +
                    "\"summary_when\":\"Pagi dan Petang\"," +
                    "\"min_temp\":23," +
                    "\"max_temp\":34}";
        }
    }
public class WeatherExtraction{
    public static void main(String[] args) {
        WeatherAPI api = new WeatherAPI();
        String weatherResponse = api.getTodayWeather();
        String summaryForecast = extractSummaryForecast(weatherResponse);
        
        LocalDate today = LocalDate.now();

        System.out.println("Today's Date: " + today);
        System.out.println("Today's Weather : " + summaryForecast);

    }
    public static String extractSummaryForecast(String jsonResponse) {
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
    
