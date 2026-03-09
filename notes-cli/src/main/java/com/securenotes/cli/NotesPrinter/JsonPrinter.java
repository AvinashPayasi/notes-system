package com.securenotes.cli.NotesPrinter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securenotes.cli.NotesPageResponseDTO;
import com.securenotes.cli.NotesResponseDTO;

public class JsonPrinter {

    private static final ObjectMapper objectMapper= new ObjectMapper();

    public static void parseNotes(String json){
        try {
            NotesPageResponseDTO response = objectMapper.readValue(json, NotesPageResponseDTO.class);
            for(NotesResponseDTO note: response.getContent()){
               printNote(note);
            }
        } catch (Exception e) {
            System.out.println(json);
        }
    }

    public static void parseSingleNote(String json){
        try{
            NotesResponseDTO response= objectMapper.readValue(json, NotesResponseDTO.class);
            printNote(response);
        } catch (Exception e) {
            System.out.println(json);
        }
    }

    private static void printNote(NotesResponseDTO note){
        System.out.println("["+note.getNotesId()+"]"+"  "+note.getTitle());
        System.out.println("---------------------------");
        System.out.println(note.getNotes());
        System.out.println("Pinned: "+(note.isPinned()?"Yes":"No")+"\n");
    }
}
