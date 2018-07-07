package com.razil.noteit.ui.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.razil.noteit.data.db.NoteEntity;
import com.razil.noteit.data.repo.NoteRepository;
import java.util.List;

public class NotesViewModel extends ViewModel {
  private final NoteRepository mNoteRepository;

  public NotesViewModel(@NonNull NoteRepository noteRepository) {
    this.mNoteRepository = noteRepository;
  }

  public LiveData<List<NoteEntity>> getAllNotes() {
    return mNoteRepository.getAllNotes();
  }
}
