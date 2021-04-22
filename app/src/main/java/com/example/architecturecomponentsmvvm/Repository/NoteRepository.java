package com.example.architecturecomponentsmvvm.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.architecturecomponentsmvvm.Note;
import com.example.architecturecomponentsmvvm.NoteDao;
import com.example.architecturecomponentsmvvm.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }
    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);

    }
    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }
    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);

    }
    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();

    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
    // we use AsyncTask because the Room Don't allow operation in mainTread so make operation in background thread
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        //to make database operation
        private NoteDao noteDao;

        // this class is static and we need to create constructor
        private InsertNoteAsyncTask (NoteDao noteDao){
        this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        //to make database operation
        private NoteDao noteDao;

        // this class is static and we need to create constructor
        private UpdateNoteAsyncTask (NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        //to make database operation
        private NoteDao noteDao;

        // this class is static and we need to create constructor
        private DeleteNoteAsyncTask (NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{
        //to make database operation
        private NoteDao noteDao;

        // this class is static so we need to create constructor
        private DeleteAllNotesAsyncTask (NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
