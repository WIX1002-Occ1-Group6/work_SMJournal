// by MENG HANYUE

package app;

public class WeatherExtraction{

    public String getWeather() {
        API api = new API();
        String getResponse = "\"summary_forecast\":\"Failed to get Weather\"";
        try {
            // --- Example GET request: Fetch latest weather forecast for Kuala Lumpur ---
            String getUrl = "https://api.data.gov.my/weather/forecast/?contains=WP%20Kuala%20Lumpur@location__location_name&sort=date&limit=1";
            getResponse = api.get(getUrl);
            //System.out.println("GET Response:\n" + getResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getResponse;
    }
/*public class WeatherExtraction{
    public static void main(String[] args) {
        WeatherAPI api = new WeatherAPI();
        String weatherResponse = api.getTodayWeather();
        String summaryForecast = extractSummaryForecast(weatherResponse);
        
        LocalDate today = LocalDate.now();

        System.out.println("Today's Date: " + today);
        System.out.println("Today's Weather : " + summaryForecast);

    }  */
    public String extractSummaryForecast(String jsonResponse) {
        String searchKey = "\"summary_forecast\":\"";
        int startIndex = jsonResponse.indexOf(searchKey) + searchKey.length();
        if (startIndex == -1) {
            return "Weather data not found";
        }
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }
}
    
