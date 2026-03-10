package com.securenotes.cli;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.securenotes.cli.NotesPrinter.JsonPrinter;

import java.io.IOException;

public class ClientService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final Client client;

    public ClientService(Client client){
        this.client=client;
    }

    public void getAllNotes(){
        try {
            String json=client.getAllNotes();
            JsonPrinter.parseNotes(json);
        }catch(InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        } catch (IOException ioException) {
            System.out.println("Unable to connect to server");
        }
    }

    public void getNoteById(int noteId) {
        try {
            String note = client.getNoteById(noteId);
            JsonPrinter.parseSingleNote(note);
        }catch(IOException ioException){
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }

    public void addNote(NoteRequestDTO noteRequestDTO) {
        try {
            String json= mapper.writeValueAsString(noteRequestDTO);
            client.addNote(json);
        }catch (JsonProcessingException jsonProcessingException){
            System.out.println("?");
        }catch (IOException ioException) {
            System.out.printf("Unable to connect to server");
        } catch (InterruptedException interruptedException) {
            System.out.println("Request Interrupted");
        }
    }

    public void deleteNote(int id) {
        try {
            client.deleteNote(id);
        } catch (IOException ioException) {
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println(("Request Interrupted"));
        }
    }

    public void getStateBasedNotes(String state) {
        try{
            String notes=client.getStateBasedNotes(state);
            JsonPrinter.parseNotes(notes);
        }catch (IOException ioException){
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }

    public void getTrashNoteById(int id) {
        try{
            String trashNote=client.getTrashNoteById(id);
            JsonPrinter.parseSingleNote(trashNote);
        } catch (IOException ioException) {
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }

    public void pinNote(int id) {
        try{
            client.pinNote(id);
        }catch (IOException ioException){
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }

    public void unpinNote(int id) {
        try{
            client.unpinNote(id);
        }catch (IOException ioException){
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }

    public void archiveNote(int id){
        try{
            client.archiveNote(id);
        }catch (IOException ioException){
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }

    public void unarchiveNote(int id){
        try{
            client.unarchiveNote(id);
        }catch (IOException ioException){
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }

    public void recoverNote(int id) {
        try{
            client.recoverNote(id);
        }catch (IOException ioException){
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }

    public void deletePermanently(int id) {
        try{
            client.deletePermanently(id);
        }catch (IOException ioException){
            System.out.println("Unable to connect to server");
        }catch (InterruptedException interruptedException){
            System.out.println("Request Interrupted");
        }
    }
}
