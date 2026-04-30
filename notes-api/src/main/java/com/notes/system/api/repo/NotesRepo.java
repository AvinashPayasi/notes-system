package com.notes.system.api.repo;

import com.notes.system.api.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotesRepo extends JpaRepository<Notes, Integer> {

    Page<Notes> findByIsDeletedTrue(Pageable pageable);

    void deleteByNotesIdAndIsDeletedTrue(int notesId);

    Optional<Notes> findByNotesIdAndIsDeletedTrue(int notesId);

    Page<Notes> findByIsArchivedTrue(Pageable pageable);

    Page<Notes> findByIsPinnedTrue(Pageable pageable);

    Page<Notes> findByIsDeletedFalseOrderByIsPinnedDesc(Pageable pageable);

    Optional<Notes> findByNotesIdAndIsDeletedFalse(int notesId);

}
