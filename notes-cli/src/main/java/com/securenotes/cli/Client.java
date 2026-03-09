package com.securenotes.cli;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    private final String BASE_URL="http://localhost:8080/api/v1/notes";
    private final HttpClient client;

    public Client(){
        client=HttpClient.newHttpClient();
    }

    public String getAllNotes() throws IOException, InterruptedException {
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getNoteById(int noteId) throws InterruptedException, IOException {
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+noteId))
                .GET()
                .build();

        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String addNote(String json) throws InterruptedException, IOException{
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
