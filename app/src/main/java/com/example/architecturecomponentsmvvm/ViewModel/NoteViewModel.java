package com.example.architecturecomponentsmvvm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.architecturecomponentsmvvm.Note;
import com.example.architecturecomponentsmvvm.Repository.NoteRepository;

import java.util.List;

// android androidViewModel is subclass of viewModel
// difference between AndroidViewModel and ViewModel is AVM is based on application and constructor which we can
// use when the application context is needed ,to initialise our database class so we will  use ANM
// you should never store the context of the activity here in the viewModel because as we learn is the ViewModel is
// design to outlet in the activity after is destroyed and if we hold a reference to already destroyed activity
// we have a memory lek but we have pass the context to our repository because

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }
    // our Activity have reference of the viewModel not to repository so we create method for DB operations for our
    // repository
    public void insert (Note note){
        repository.insert(note);
    }
    public void update (Note note){
        repository.update(note);
    }
    public void delete (Note note){
        repository.delete(note);
    }
    public void deleteAllNotes (){
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
    // next step is to get reference of viewModel in Our activity

}
