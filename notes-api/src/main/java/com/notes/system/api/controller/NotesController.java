package com.notes.system.api.controller;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.dto.NoteRequest;
import com.notes.system.api.dto.NoteResponse;
import com.notes.system.api.dto.PageResponse;
import com.notes.system.api.dto.UpdateNoteRequest;
import com.notes.system.api.entity.enums.NotesState;
import com.notes.system.api.service.NotesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "JWT Authentication")
@Tag(name="Notes", description = "Endpoints for creating, retrieving, updating, and deleting authenticated user's notes.")
public class NotesController {
    private NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService){
        this.notesService=notesService;
    }

    @Operation(
            summary = "Get all notes",
            description = "Retrieves all notes belonging to the authenticated user",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note fetched successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Invalid pagination or sort parameter",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
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

    @Operation(
            summary = "Create a new note",
            description = "Creates a new note for authenticated user",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Note created successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "At least one of title or note must be provided",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @PostMapping("/notes")
    public ResponseEntity<ApiResponse<Object>> addNotes(@RequestBody NoteRequest noteRequest){
        ApiResponse<Object> response=notesService.addNote(noteRequest);

        //Status Code: 201
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get note by ID",
            description = "Retrieves a specific note by notes ID of authenticated user",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note fetched successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Path parameter 'notesId' must be an integer",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @GetMapping("/notes/{notesId}")
    public ResponseEntity<ApiResponse<NoteResponse>> getNotesById(@PathVariable int notesId,
                                                                  @RequestParam(defaultValue = "ACTIVE") NotesState state){
        ApiResponse<NoteResponse> response= notesService.getNoteById(notesId, state);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Move note to trash",
            description = "Moves the specified note to the trash. The note can be restored or permanently deleted later",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note deleted successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Path parameter 'notesId' must be an integer",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/notes/{notesId}")
    public ResponseEntity<ApiResponse<Object>> softDelete(@PathVariable int notesId){
        ApiResponse<Object> response = notesService.softDelete(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Restore trashed note",
            description = "Restores the specific notes from the trash",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note restored successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Path parameter 'notesId' must be an integer",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Invalid note state",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @PatchMapping("/notes/{notesId}/restore")
    public ResponseEntity<ApiResponse<Object>> recoverDeletedNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.restoreTrashNote(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Note",
            description = "Updates one or more fields of the specified note",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note updated successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "At least one of title or note must be provided",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @PatchMapping("/notes/{notesId}")
    public ResponseEntity<ApiResponse<Object>> updateNotes(@PathVariable int notesId, @RequestBody UpdateNoteRequest updateNoteRequest){
        ApiResponse<Object> response=notesService.updateNote(notesId, updateNoteRequest);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Permanently delete note",
            description = "Permanently deletes the specified trashed note. This operation cannot be undone",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note permanently deleted successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Path parameter 'notesId' must be an integer",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Only trash note can be permanently deleted",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/notes/{notesId}/purge")
    public ResponseEntity<ApiResponse<Object>> deletePermanently(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.deletePermanently(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Pin note",
            description = "Pinned the specific note",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note pinned successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Path parameter 'notesId' must be an integer",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Trash note can't be pinned/unpinned",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @PatchMapping("/notes/{notesId}/pin")
    public ResponseEntity<ApiResponse<Object>> pinNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.pinNote(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Unpin note",
            description = "Removes the pinned status from the specified note",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note unpinned successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Path parameter 'notesId' must be an integer",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Trash note can't be pinned/unpinned",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @PatchMapping("/notes/{notesId}/unpin")
    public ResponseEntity<ApiResponse<Object>> unpinNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.unpinNote(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Archive note",
            description = "Moves the specified note to the archive",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note archived successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Path parameter 'notesId' must be an integer",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Trash note can't be archive/unarchive",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @PatchMapping("/notes/{notesId}/archive")
    public ResponseEntity<ApiResponse<Object>> archiveNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.archiveNote(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Unarchive note",
            description = "Restores the specified note from the archive",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Note unarchived successfully",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Path parameter 'notesId' must be an integer",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Authentication required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Note not found",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Trash note can't be archive/unarchive",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Something went wrong",
                            content = @Content
                    )
            }
    )
    @PatchMapping("/notes/{notesId}/unarchive")
    public ResponseEntity<ApiResponse<Object>> unarchiveNote(@PathVariable int notesId){
        ApiResponse<Object> response=notesService.unarchiveNotes(notesId);
        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}