package utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Translate {
    public static String translateSpanishToEnglish(String spanishText) throws Exception {
        String jsonPayload = "{\"from\":\"es\",\"to\":\"en\",\"q\":\"" + spanishText + "\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://rapid-translate-multi-traduction.p.rapidapi.com/t"))
                .header("x-rapidapi-key", "8a858f41f9msh286ddfb2c5b2213p1b139cjsnf109b5a8f04d") // your API key
                .header("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // Send the request and get the response
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the API response
        return response.body();
    }
}
