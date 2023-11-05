package com.example.todo.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.DataBase.NotesDataBase
import com.example.todo.Repository.NotesRepository
import com.example.todo.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    var repository: NotesRepository
    lateinit var notesList: LiveData<List<Notes>>

    init {
        val notesDao = NotesDataBase.getDataBaseInstance(application).notesDao()
        repository = NotesRepository(notesDao)

    }

    fun addNotes(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNotes(notes)
        }
    }

    fun getNotes(): LiveData<List<Notes>> {
        viewModelScope.launch {
            notesList = repository.getAllNotes()
        }
        return notesList

    }

    fun updateNotes(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotes(notes)

        }
    }

    fun deleteNotes(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotes(id)
        }
    }
}