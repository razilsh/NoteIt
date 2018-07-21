package com.razil.noteit.ui.notes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.razil.noteit.data.repo.NoteRepository;

public class NotesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
  private final NoteRepository mNoteRepository;

  public NotesViewModelFactory(NoteRepository noteRepository) {
    this.mNoteRepository = noteRepository;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    return (T) new NotesViewModel(mNoteRepository);
  }
}
