package com.example.fastnote

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("notes", Context.MODE_PRIVATE)

    suspend fun saveNote(note: String) {
        withContext(Dispatchers.IO) {
            val notes = sharedPreferences.getStringSet("note_list", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            notes.add(note)
            sharedPreferences.edit().putStringSet("note_list", notes).apply()
        }
    }

    suspend fun getNotes(): Set<String> {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getStringSet("note_list", mutableSetOf()) ?: mutableSetOf()
        }
    }
}
