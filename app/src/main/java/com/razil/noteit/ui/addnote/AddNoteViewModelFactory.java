package com.razil.noteit.ui.addnote;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.razil.noteit.data.repo.NoteRepository;

public class AddNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {
  private final NoteRepository mNoteRepository;

  public AddNoteViewModelFactory(NoteRepository mNoteRepository) {
    this.mNoteRepository = mNoteRepository;
  }

  @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    return (T) new AddNoteViewModel(mNoteRepository);
  }
}
