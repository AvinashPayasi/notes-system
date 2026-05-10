package com.notes.system.api.controller;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.entity.enums.NotesState;
import com.notes.system.api.dto.NoteRequestDTO;
import com.notes.system.api.dto.NoteResponseDTO;
import com.notes.system.api.entity.Notes;
import com.notes.system.api.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NotesController {
    private NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService){
        this.notesService=notesService;
    }

    //TODO: Implement multiple notes fetching and pagination
    /*@GetMapping("/notes")
    public PagedModel<Notes> getNote(@RequestParam(required = false) String state,
                                      @RequestParam(defaultValue="1") int page,
                                      @RequestParam(defaultValue = "10")int size,
                                      @RequestParam(defaultValue = "createdAt") String sortBy,
                                      @RequestParam(defaultValue = "asc") String direction)
    {
        Sort sort= Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,sortBy);

        if(state==null) {
            return notesService.getNote(page-1, size, sort);
        }
        switch(state.toLowerCase()){
            case "archived":
                return notesService.getArchivedNotes(page-1, size, sort);
            case "pinned":
                return notesService.getPinnedNotes(page-1, size, sort);
            case "trash":
                return notesService.getTrashedNotes(page-1, size, sort);
            default:
                throw new IllegalArgumentException("Invalid State: "+state);
        }
    }*/

    @PostMapping("/notes")
    public ResponseEntity<ApiResponse<Object>> addNotes(@RequestBody NoteRequestDTO noteRequest){
        ApiResponse<Object> response=notesService.addNote(noteRequest);

        //Status Code: 201
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //TODO: Implement Bulk add functionality
    @PostMapping("/notes/bulk")
    public void addBulkNotes(@RequestBody List<Notes> notes){
    }

    @GetMapping("/notes/{notesId}")
    public ResponseEntity<ApiResponse<NoteResponseDTO>> getNotesById(@PathVariable int notesId,
                                                                     @RequestParam(defaultValue = "ACTIVE") NotesState state){
        ApiResponse<NoteResponseDTO> response= notesService.getNoteById(notesId, state);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/notes/{notesId}")
    public ResponseEntity<ApiResponse<Object>> softDelete(@PathVariable int notesId){
        ApiResponse<Object> response = notesService.softDelete(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/notes/{notesId}/restore")
    public ResponseEntity<ApiResponse<Object>> recoverDeletedNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.restoreTrashNote(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: Implement edit note feature
    @PatchMapping("/notes/{notesId}")
    public void updateNotes(@PathVariable int notesId, @RequestBody Notes notes){
    }

    @DeleteMapping("/notes/{notesId}/purge")
    public ResponseEntity<ApiResponse<Object>> deletePermanently(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.deletePermanently(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/notes/{notesId}/pin")
    public ResponseEntity<ApiResponse<Object>> pinNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.pinNote(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/notes/{notesId}/unpin")
    public ResponseEntity<ApiResponse<Object>> unpinNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.unpinNote(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/notes/{notesId}/archive")
    public ResponseEntity<ApiResponse<Object>> archiveNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.archiveNote(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/notes/{notesId}/unarchive")
    public void unarchiveNote(@PathVariable int notesId){
        notesService.unarchiveNotes(notesId);
    }
}
