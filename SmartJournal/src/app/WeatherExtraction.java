// by MENG HANYUE

package app;

public class WeatherExtraction {
    
    static class API {
        
        public String get() {
           
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

    
    private API weatherApi;

    
    public WeatherExtraction() {
        this.weatherApi = new API();
    }

    
    public String extractSummaryForecast() {
        try {
            String weatherResponse = weatherApi.get();
            System.out.println("API return the weather data：" + weatherResponse); 
            String targetKey = "\"summary_forecast\": \""; 
            int keyStartIndex = weatherResponse.indexOf(targetKey);

            if (keyStartIndex == -1) {
                return "Weather summary information not obtained";
            }
            int valueStartIndex = keyStartIndex + targetKey.length();
            int valueEndIndex = weatherResponse.indexOf("\"", valueStartIndex);

            if (valueEndIndex == -1) {
                return "Weather data format is abnormal, cannot extract";
            }
            return weatherResponse.substring(valueStartIndex, valueEndIndex);

        } catch (Exception e) {
            return "Weather extraction failed：" + e.getMessage();
        }
    }

    public static void main(String[] args) {
       
        WeatherExtraction weatherExtractor = new WeatherExtraction();
        String weatherSummary = weatherExtractor.extractSummaryForecast();
        System.out.println("\nExtracted weather summary：" + weatherSummary);
    }
}
