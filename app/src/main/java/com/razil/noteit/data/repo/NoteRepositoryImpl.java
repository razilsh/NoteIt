package com.razil.noteit.data.repo;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.razil.noteit.data.db.NoteDao;
import com.razil.noteit.data.db.NoteEntity;
import java.util.List;
import java.util.concurrent.Executor;

public class NoteRepositoryImpl implements NoteRepository {
  private NoteDao mNoteDao;
  private Executor mExecutor;

  public NoteRepositoryImpl(@NonNull NoteDao mNoteDao, @NonNull Executor mExecutor) {
    this.mNoteDao = mNoteDao;
    this.mExecutor = mExecutor;
  }

  @Override public void insert(NoteEntity noteEntity) {
    mExecutor.execute(() -> mNoteDao.insert(noteEntity));
  }

  @Override public void update(NoteEntity noteEntity) {
    mExecutor.execute(() -> mNoteDao.update(noteEntity));
  }

  @Override public void delete(NoteEntity noteEntity) {
    mExecutor.execute(() -> mNoteDao.delete(noteEntity));
  }

  @Override public LiveData<List<NoteEntity>> getAllNotes() {
    return mNoteDao.getAllNotes();
  }

  @Override public LiveData<NoteEntity> getNoteById(int noteId) {
    return mNoteDao.getNoteById(noteId);
  }
}
