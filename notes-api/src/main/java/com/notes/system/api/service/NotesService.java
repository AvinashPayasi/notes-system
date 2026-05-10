package com.notes.system.api.service;
import com.notes.system.api.ApiResponse;
import com.notes.system.api.ApiStatus;
import com.notes.system.api.entity.enums.NotesState;
import com.notes.system.api.dto.NoteRequestDTO;
import com.notes.system.api.dto.NoteResponseDTO;
import com.notes.system.api.exception.EmptyNoteException;
import com.notes.system.api.exception.InvalidNoteStateException;
import com.notes.system.api.exception.NoteNotFoundException;
import com.notes.system.api.entity.Notes;
import com.notes.system.api.repository.NotesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class NotesService {
    private final NotesRepo notesRepo;

    @Autowired
    public NotesService(NotesRepo notesRepo){
        this.notesRepo=notesRepo;
    }

    public ApiResponse<Object> addNote(NoteRequestDTO noteRequest){
        String title=noteRequest.getTitle();
        String note=noteRequest.getNote();
        if((title==null||title.isBlank())&&(note==null||note.isBlank())){
            throw new EmptyNoteException("At least one of title or note must be provided");
        }
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes notes = new Notes(userId, title, note);
        notesRepo.save(notes);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note Created Successfully");
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
        Notes note=notesRepo.fetchNoteIgnoringState(userId, notesId).orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(note.getState().equals(NotesState.ACTIVE)){
            throw new InvalidNoteStateException("Cannot permanently delete an active note. Move it to trash first.");
        } else if (note.getState().equals(NotesState.ARCHIVE)) {
            throw new InvalidNoteStateException("Cannot permanently delete an archive note. Move it to trash first.");
        }
        notesRepo.permanentlyDeleteNote(userId, notesId, NotesState.TRASH);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note deleted successfully");
    }

    public ApiResponse<NoteResponseDTO> getNoteById(int noteId, NotesState state) {
        UUID userId= (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note= notesRepo.fetchStateBasedNote(userId,noteId, state).
                orElseThrow(() -> new NoteNotFoundException("Note not found"));
        NoteResponseDTO noteResponse = new NoteResponseDTO(note.getNotesId(), note.getTitle(), note.getNote(), note.isPinned(), note.getCreatedAt());
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note fetched successfully", noteResponse);
    }

    @Transactional
    public ApiResponse<Object> pinNote(int notesId) {
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow( () -> new NoteNotFoundException("Note not found"));
        if(note.getState().equals(NotesState.TRASH)){
            throw new InvalidNoteStateException("Trash note can't be pinned");
        }
        note.setPinned(true);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note pinned successfully");
    }

    @Transactional
    public ApiResponse<Object> unpinNote(int notesId) {
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(note.getState().equals(NotesState.TRASH)){
            throw new InvalidNoteStateException("Trash note can't be unpinned");
        }
        note.setPinned(false);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note unpinned successfully");
    }

    @Transactional
    public ApiResponse<Object> archiveNote(int notesId) {
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(note.getState().equals(NotesState.TRASH)){
            throw new InvalidNoteStateException("Trash note can't be archived");
        }
        note.setState(NotesState.ARCHIVE);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note archived successfully");
    }

    @Transactional
    public ApiResponse<Object> unarchiveNotes(int notesId) {
        UUID userId=(UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notes note=notesRepo.fetchNoteIgnoringState(userId, notesId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if(note.getState().equals(NotesState.TRASH)){
            throw new InvalidNoteStateException("Pin/Unpin cannot work in trash");
        }
        note.setState(NotesState.ACTIVE);
        return new ApiResponse<>(ApiStatus.SUCCESS, "Note unarchived successfully");
    }

}