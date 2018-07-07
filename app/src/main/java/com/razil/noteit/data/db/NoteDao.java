package com.razil.noteit.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface NoteDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(NoteEntity noteEntity);

  @Query("SELECT * FROM note")
  LiveData<List<NoteEntity>> getAllNotes();

  @Query("SELECT * FROM note WHERE id = :id")
  LiveData<NoteEntity> getNoteById(int id);

  @Update
  void update(NoteEntity noteEntity);

  @Delete
  void delete(NoteEntity noteEntity);
}
