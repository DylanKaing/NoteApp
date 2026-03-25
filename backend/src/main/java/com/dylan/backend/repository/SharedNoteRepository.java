package com.dylan.backend.repository;

import com.dylan.backend.entity.SharedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SharedNoteRepository extends JpaRepository<SharedNote, Long> {
    
    List<SharedNote> findByUserUserId(Long userId);
    List<SharedNote> findByNoteNoteId(Long noteId);
    SharedNote findByNoteNoteIdAndUserUserId(Long noteId, Long userId);

}
