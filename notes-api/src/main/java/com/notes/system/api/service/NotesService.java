package com.notes.system.api.service;
import com.notes.system.api.ApiResponse;
import com.notes.system.api.ApiStatus;
import com.notes.system.api.dto.NoteRequest;
import com.notes.system.api.dto.NoteResponse;
import com.notes.system.api.dto.PageResponse;
import com.notes.system.api.dto.UpdateNoteRequest;
import com.notes.system.api.entity.enums.NotesState;
import com.notes.system.api.exception.EmptyNoteException;
import com.notes.system.api.exception.InvalidNoteStateException;
import com.notes.system.api.exception.InvalidPaginationException;
import com.notes.system.api.exception.NoteNotFoundException;
import com.notes.system.api.entity.Notes;
import com.notes.system.api.repository.NotesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotesService {
    private final NotesRepo notesRepo;

    @Autowired
    public NotesService(NotesRepo notesRepo){
        this.notesRepo=notesRepo;
    }

    public ApiResponse<Object> addNote(NoteRequest noteRequest){
        String requestTitle =noteRequest.getTitle();
        String requestNote=noteRequest.getNote();
        if((requestTitle ==null|| requestTitle.isBlank())&&(requestNote==null||requestNote.isBlank())){
            throw new EmptyNoteException("At least one of title or note must be provided");
        }
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note = new Notes(userId, requestTitle, requestNote);
        notesRepo.save(note);
        Notes fetchedNote=notesRepo.fetchStateBasedNote(userId, note.getNotesId(), NotesState.ACTIVE).orElseThrow(()-> new NoteNotFoundException("Note not found"));
        NoteResponse data= new NoteResponse(fetchedNote);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note Created Successfully", data);
    }

    @Transactional
    public ApiResponse<Object> softDelete(int notesId) {
        UUID userId=(UUID)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note=notesRepo.fetchNoteIgnoringState(userId, notesId)
               .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        note.setPinned(false);
        note.setState(NotesState.TRASH);
        return new ApiResponse<>(ApiStatus.SUCCESS,"Note deleted successfully");
    }

    @Transactional
    public ApiResponse<Object> restoreTrashNote(int notesId){
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(note.getState().equals(NotesState.ARCHIVE)){
            throw new InvalidNoteStateException("Archived note cannot be restored");
        }else{
            note.setState(NotesState.ACTIVE);
        }
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note restored successfully");
    }

   /* @Transactional
    public void updateNotes(int notesId, Notes notes) {
        Notes notes1=notesRepo.getReferenceById(notesId);
        String title=notes.getTitle();
        String note=notes.getNote();
        if(title==null&&note==null){
            return;
        }else if(title==null){
            notes1.setNote(note);
        }else if (note==null){
            notes1.setTitle(title);
        }else{
            notes1.setTitle(title);
            notes1.setNote(note);
        }
    }*/

    @Transactional
    public ApiResponse<Object> deletePermanently(int notesId) {
        UUID userId= (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes fetchedNote=notesRepo.fetchNoteIgnoringState(userId, notesId).orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(fetchedNote.getState().equals(NotesState.ACTIVE)){
            throw new InvalidNoteStateException("Cannot permanently delete an active note. Move it to trash first.");
        } else if (fetchedNote.getState().equals(NotesState.ARCHIVE)) {
            throw new InvalidNoteStateException("Cannot permanently delete an archive note. Move it to trash first.");
        }
        notesRepo.permanentlyDeleteNote(userId, notesId, NotesState.TRASH);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note deleted successfully");
    }

    public ApiResponse<NoteResponse> getNoteById(int noteId, NotesState state) {
        UUID userId= (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note= notesRepo.fetchStateBasedNote(userId,noteId, state).
                orElseThrow(() -> new NoteNotFoundException("Note not found"));
        NoteResponse noteResponse = new NoteResponse(note);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note fetched successfully", noteResponse);
    }

    @Transactional
    public ApiResponse<Object> pinNote(int notesId) {
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes fetchedNote=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow( () -> new NoteNotFoundException("Note not found"));
        if(fetchedNote.getState().equals(NotesState.TRASH)){
            throw new InvalidNoteStateException("Trash note can't be pinned");
        }
        fetchedNote.setPinned(true);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note pinned successfully");
    }

    @Transactional
    public ApiResponse<Object> unpinNote(int notesId) {
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes fetchedNote=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(fetchedNote.getState().equals(NotesState.TRASH)){
            throw new InvalidNoteStateException("Trash note can't be unpinned");
        }
        fetchedNote.setPinned(false);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note unpinned successfully");
    }

    @Transactional
    public ApiResponse<Object> archiveNote(int notesId) {
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes fetchedNote=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(fetchedNote.getState().equals(NotesState.TRASH)){
            throw new InvalidNoteStateException("Trash note can't be archived");
        }
        fetchedNote.setState(NotesState.ARCHIVE);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note archived successfully");
    }

    @Transactional
    public ApiResponse<Object> unarchiveNotes(int notesId) {
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes fetchedNote=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(fetchedNote.getState().equals(NotesState.TRASH)){
            throw new InvalidNoteStateException("Pin/Unpin cannot work in trash");
        }
        fetchedNote.setState(NotesState.ACTIVE);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note unarchived successfully");
    }

    @Transactional
    public ApiResponse<Object> updateNote(int notesId, UpdateNoteRequest updateNoteRequest) {
        String requestTitle= updateNoteRequest.getTitle();
        String requestNote= updateNoteRequest.getNote();
        if((requestTitle==null || requestTitle.isBlank()) && (requestNote==null || requestNote.isBlank())){
            throw new EmptyNoteException("At least one of title or note must be provided");
        }
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes notes=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow(()-> new NoteNotFoundException("Note not found"));
        notes.setTitle(requestTitle);
        notes.setNote(requestNote);
        NoteResponse data=new NoteResponse(notes);
        return new ApiResponse<Object>(ApiStatus.SUCCESS, "Note updated successfully", data);
    }

    public ApiResponse<PageResponse<NoteResponse>> getAllNotes(int page, int size, String sort, String direction, NotesState state) {
        Sort.Direction directionValue;
        String sortValue;
        try{
            directionValue = Sort.Direction.fromString(direction);
        }catch (IllegalArgumentException exception){
            throw new InvalidPaginationException("Direction must be asc or desc");
        }
        if(sort.equalsIgnoreCase("createdAt")){
            sortValue="createdAt";
        } else if (sort.equalsIgnoreCase("title")) {
            sortValue="title";
        } else if (sort.equalsIgnoreCase("noteId")) {
            sortValue="noteId";
        }else{
            throw new InvalidPaginationException("Sort must be createdat, title or noteid");
        }
        if(page<0){
            throw new InvalidPaginationException("Page value must be 1 or more");
        } else if (size>30||size<1) {
            throw new InvalidPaginationException("Size must be between 1 to 30");
        }
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable=PageRequest.of(page, size, Sort.by(directionValue, sortValue));
        Page<Notes> notesPage=notesRepo.findAllByUserIdAndState(userId, state, pageable);
        Page<NoteResponse> dtoPage= notesPage.map(NoteResponse::new);
        PageResponse<NoteResponse> responsePage=new PageResponse<>(dtoPage);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note fetched successfully", responsePage);
    }
}