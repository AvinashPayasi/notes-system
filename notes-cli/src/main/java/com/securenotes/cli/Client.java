package com.securenotes.cli;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

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

    public String deleteNote(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+id))
                .DELETE()
                .build();

        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getStateBasedNotes(String state) throws InterruptedException, IOException{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"?state="+state))
                .GET()
                .build();

        HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getTrashNoteById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+id+"/trash"))
                .GET()
                .build();

        HttpResponse<String > response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String pinNote(int id) throws IOException, InterruptedException{
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+id+"/pin"))
                .method("PATCH", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String unpinNote(int id) throws IOException, InterruptedException{
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+id+"/unpin"))
                .method("PATCH", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String archiveNote(int id)  throws IOException, InterruptedException{
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+id+"/archive"))
                .method("PATCH", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String unarchiveNote(int id)  throws IOException, InterruptedException{
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+id+"/unarchive"))
                .method("PATCH", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String  recoverNote(int id) throws IOException, InterruptedException{
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+id+"/recover"))
                .method("PATCH", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String deletePermanently(int id) throws IOException, InterruptedException {
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/"+id+"/trash"))
                .DELETE()
                .build();

        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
