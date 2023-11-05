package com.example.todo.Repository

import androidx.lifecycle.LiveData
import com.example.todo.Dao.NotesDao
import com.example.todo.model.Notes

class NotesRepository(val dao: NotesDao) {

    suspend fun getAllNotes(): LiveData<List<Notes>> = dao.getNotes()

    suspend fun insertNotes(notes: Notes) = dao.insertNotes(notes)

    suspend fun updateNotes(notes: Notes) = dao.updateNotes(notes)

    suspend fun deleteNotes(id: Int) = dao.deleteNotes(id)
}