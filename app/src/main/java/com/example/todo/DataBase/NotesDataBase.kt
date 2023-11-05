package com.example.todo.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.Dao.NotesDao
import com.example.todo.model.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDataBase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {

        @Volatile
        var INSTANCE: NotesDataBase? = null

        fun getDataBaseInstance(context: Context): NotesDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDataBaseInstance = Room
                    .databaseBuilder(context, NotesDataBase::class.java, "Notes").build()
                INSTANCE = roomDataBaseInstance
                return roomDataBaseInstance
            }

        }
    }
}