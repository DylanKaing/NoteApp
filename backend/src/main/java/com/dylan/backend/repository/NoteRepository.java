package com.dylan.backend.repository;

import com.dylan.backend.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{
    List<Note> findByUserUserId(Long userId);
}
