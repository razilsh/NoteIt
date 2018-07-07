package com.razil.noteit.ui.addnote;

import android.arch.lifecycle.ViewModel;
import com.razil.noteit.R;
import com.razil.noteit.SingleLiveEvent;
import com.razil.noteit.data.db.NoteEntity;
import com.razil.noteit.data.repo.NoteRepository;
import com.razil.noteit.ui.SnackbarMessage;
import com.razil.noteit.util.Validator;
import java.util.Calendar;

public class AddNoteViewModel extends ViewModel {
  private static final String TAG = AddNoteViewModel.class.getSimpleName();
  private final NoteRepository mNoteRepository;
  private SnackbarMessage mSnackbarMessage = new SnackbarMessage();
  private SingleLiveEvent<Void> mNoteAdded = new SingleLiveEvent<>();

  AddNoteViewModel(NoteRepository noteRepository) {
    this.mNoteRepository = noteRepository;
  }

  SingleLiveEvent<Void> getNoteAdded() {
    return mNoteAdded;
  }

  SnackbarMessage getSnackbarMessage() {
    return mSnackbarMessage;
  }

  void addNote(String noteTitle, String noteDescription) {
    if (Validator.isNullOrEmpty(noteTitle, noteDescription)) {
      mSnackbarMessage.setValue(R.string.err_empty_note);
      return;
    }

    NoteEntity noteEntity = new NoteEntity();
    noteEntity.setTitle(noteTitle);
    noteEntity.setDescription(noteDescription);
    noteEntity.setCreatedAt(Calendar.getInstance().getTime());

    mNoteRepository.insert(noteEntity);
    mNoteAdded.call();
  }
}
