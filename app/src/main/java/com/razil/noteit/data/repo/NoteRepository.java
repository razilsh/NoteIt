package com.razil.noteit.data.repo;

import android.arch.lifecycle.LiveData;

import com.razil.noteit.data.db.NoteEntity;

import java.util.List;

public interface NoteRepository {
  void insert(NoteEntity noteEntity);

  void update(NoteEntity noteEntity);

  void delete(NoteEntity noteEntity);

  LiveData<List<NoteEntity>> getAllNotes();

  LiveData<NoteEntity> getNoteById(int noteId);
}
