package com.razil.noteit.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = { NoteEntity.class }, version = 1)
@TypeConverters(DateConverter.class)
public abstract class NotesDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "weather";
  private static final Object LOCK = new Object();
  private static volatile NotesDatabase sInstance;

  public static NotesDatabase getInstance(Context context) {
    if (sInstance == null) {
      synchronized (LOCK) {
        if (sInstance == null) {
          sInstance =
              Room.databaseBuilder(
                  context.getApplicationContext(), NotesDatabase.class, DATABASE_NAME)
                  .build();
        }
      }
    }
    return sInstance;
  }

  public abstract NoteDao noteDao();
}
