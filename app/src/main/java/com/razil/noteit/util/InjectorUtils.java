package com.razil.noteit.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.razil.noteit.AppExecutors;
import com.razil.noteit.data.db.NotesDatabase;
import com.razil.noteit.data.repo.NoteRepository;
import com.razil.noteit.data.repo.NoteRepositoryImpl;
import com.razil.noteit.ui.addnote.AddNoteViewModelFactory;
import com.razil.noteit.ui.notes.NotesViewModelFactory;

public final class InjectorUtils {
  public static NoteRepositoryImpl provideNotesRepository(@NonNull Context context) {
    NotesDatabase database = NotesDatabase.getInstance(context.getApplicationContext());
    return new NoteRepositoryImpl(database.noteDao(), AppExecutors.getInstance().diskIO());
  }

  public static NotesViewModelFactory provideNotesViewModelFactory(@NonNull Context context) {
    NoteRepository noteRepository = provideNotesRepository(context.getApplicationContext());
    return new NotesViewModelFactory(noteRepository);
  }

  public static AddNoteViewModelFactory provideAddNoteViewModelFactory(@NonNull Context context) {
    NoteRepository noteRepository = provideNotesRepository(context.getApplicationContext());
    return new AddNoteViewModelFactory(noteRepository);
  }
}
