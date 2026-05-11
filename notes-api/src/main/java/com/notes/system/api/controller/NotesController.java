package com.notes.system.api.controller;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.dto.NoteRequest;
import com.notes.system.api.dto.NoteResponse;
import com.notes.system.api.dto.PageResponse;
import com.notes.system.api.dto.UpdateNoteRequest;
import com.notes.system.api.entity.enums.NotesState;
import com.notes.system.api.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class NotesController {
    private NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService){
        this.notesService=notesService;
    }

    @GetMapping("/notes")
    public ResponseEntity<ApiResponse<PageResponse<NoteResponse>>> getNote(@RequestParam(defaultValue="1") int page,
                                      @RequestParam(defaultValue = "10")int size,
                                      @RequestParam(defaultValue = "createdAt") String sort,
                                      @RequestParam(defaultValue = "asc") String direction,
                                      @RequestParam(defaultValue = "ACTIVE") NotesState state)
    {
        ApiResponse<PageResponse<NoteResponse>> response=notesService.getAllNotes(page-1, size, sort, direction, state);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/notes")
    public ResponseEntity<ApiResponse<Object>> addNotes(@RequestBody NoteRequest noteRequest){
        ApiResponse<Object> response=notesService.addNote(noteRequest);

        //Status Code: 201
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/notes/{notesId}")
    public ResponseEntity<ApiResponse<NoteResponse>> getNotesById(@PathVariable int notesId,
                                                                  @RequestParam(defaultValue = "ACTIVE") NotesState state){
        ApiResponse<NoteResponse> response= notesService.getNoteById(notesId, state);
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

    @PatchMapping("/notes/{notesId}")
    public ResponseEntity<ApiResponse<Object>> updateNotes(@PathVariable int notesId, @RequestBody UpdateNoteRequest updateNoteRequest){
        ApiResponse<Object> response=notesService.updateNote(notesId, updateNoteRequest);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<ApiResponse<Object>> unarchiveNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.unarchiveNotes(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}