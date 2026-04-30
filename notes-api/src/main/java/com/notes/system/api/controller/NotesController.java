package com.notes.system.api.controller;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.ApiStatus;
import com.notes.system.api.entity.Notes;
import com.notes.system.api.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
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

    @GetMapping("/notes")
    public PagedModel<Notes> getNotes(@RequestParam(required = false) String state,
                                      @RequestParam(defaultValue="0") int page,
                                      @RequestParam(defaultValue = "10")int size,
                                      @RequestParam(defaultValue = "createdAt") String sortBy,
                                      @RequestParam(defaultValue = "asc") String direction)
    {
        Sort sort= Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,sortBy);

        if(state==null) {
            return notesService.getNotes(page, size, sort);
        }
        switch(state.toLowerCase()){
            case "archived":
                return notesService.getArchivedNotes(page, size, sort);
            case "pinned":
                return notesService.getPinnedNotes(page, size, sort);
            case "trash":
                return notesService.getTrashedNotes(page, size, sort);
            default:
                throw new IllegalArgumentException("Invalid State: "+state);
        }
    }

    @PostMapping("/notes")
    public void addNotes(@RequestBody Notes notes){
        notesService.addNote(notes);
    }

    @PostMapping("/notes/bulk")
    public void addBulkNotes(@RequestBody List<Notes> notes){
        notesService.addBulkNotes(notes);
    }

    @GetMapping("/notes/{notesId}")
    public ResponseEntity<ApiResponse<Notes>> getNotesById(@PathVariable int notesId){
        Notes note = notesService.getNotesById(notesId);
        ApiResponse<Notes> response= new ApiResponse<>(ApiStatus.SUCCESS, "Note fetched Successfully", note);

        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/notes/{notesId}")
    public void deleteNotesById(@PathVariable int notesId){
        notesService.deleteNotesById(notesId);
    }

    @GetMapping("/notes/{notesId}/trash")
    public ResponseEntity<ApiResponse<Notes>> getTrashNoteById(@PathVariable int notesId){
        Notes note= notesService.getTrashNoteById(notesId);

        ApiResponse<Notes> response = new ApiResponse<>(ApiStatus.SUCCESS, "Note fetched successfully", note);

        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/notes/{notesId}/recover")
    public void recoverDeletedNote(@PathVariable int notesId){
        notesService.recoverDeletedNotes(notesId);
    }

    @PatchMapping("/notes/{notesId}")
    public void updateNotes(@PathVariable int notesId, @RequestBody Notes notes){
        notesService.updateNotes(notesId,notes);
    }

    @DeleteMapping("/notes/{notesId}/trash")
    public void deletePermanently(@PathVariable int notesId){
        notesService.deletePermanently(notesId);
    }

    @PatchMapping("/notes/{notesId}/pin")
    public void pinNote(@PathVariable int notesId){
        notesService.pinNote(notesId);
    }

    @PatchMapping("/notes/{notesId}/unpin")
    public void unpinNote(@PathVariable int notesId){
        notesService.unpinNote(notesId);
    }

    @PatchMapping("/notes/{notesId}/archive")
    public void archiveNote(@PathVariable int notesId){
        notesService.archiveNote(notesId);
    }

    @PatchMapping("/notes/{notesId}/unarchive")
    public void unarchiveNote(@PathVariable int notesId){
        notesService.unarchiveNotes(notesId);
    }

}
