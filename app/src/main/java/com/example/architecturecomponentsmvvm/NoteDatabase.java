package com.example.architecturecomponentsmvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Note.class}, exportSchema = false, version = 1)
public abstract class NoteDatabase extends RoomDatabase
{
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    // now when our instance is first created it will execute onCreate method and populate our DB
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    // how to populate DB we start , so instead of empty table we start with already table has some nodes with it
    // this method was called with first time we created the DB , but not any time after that
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // execute asyncTask on onCreate
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDao noteDao;
        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","Description 1" ,1));
            noteDao.insert(new Note("Title 2","Description 2" ,2));
            noteDao.insert(new Note("Title 3","Description 3" ,3));
            return null;
        }
    }
}
