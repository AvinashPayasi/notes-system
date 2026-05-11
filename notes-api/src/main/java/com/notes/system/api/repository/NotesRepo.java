package com.notes.system.api.repository;

import com.notes.system.api.entity.enums.NotesState;
import com.notes.system.api.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotesRepo extends JpaRepository<Notes, Integer> {

    @Modifying
    @Query("DELETE FROM Notes n WHERE n.userId= :userId AND n.noteId= :noteId AND n.state=:state")
    void permanentlyDeleteNote(@Param("userId") UUID userId, @Param("noteId") int noteId, @Param("state") NotesState state);

    @Query("SELECT n FROM Notes n WHERE n.userId= :userId AND n.noteId= :noteId AND n.state= :state")
    Optional<Notes> fetchStateBasedNote(@Param("userId") UUID userId, @Param("noteId") int noteId, @Param("state")NotesState state);

    @Query("SELECT n FROM Notes n WHERE n.userId= :userId AND n.noteId=:noteId")
    Optional<Notes> fetchNoteIgnoringState(@Param("userId") UUID userId, @Param("noteId") int notesId);

    Page<Notes> findAllByUserIdAndState(UUID userId, NotesState state, Pageable pageable);
}
