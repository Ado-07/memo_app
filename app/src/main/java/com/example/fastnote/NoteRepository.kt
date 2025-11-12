package com.example.fastnote

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
    private val gson = Gson()

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) {
            val notes = getNotes().toMutableList()
            notes.add(0, note) // Add to the top of the list
            val jsonString = gson.toJson(notes)
            sharedPreferences.edit().putString("note_list_json", jsonString).apply()
        }
    }

    suspend fun getNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            val jsonString = sharedPreferences.getString("note_list_json", null)
            if (jsonString != null) {
                val type = object : TypeToken<List<Note>>() {}.type
                gson.fromJson(jsonString, type)
            } else {
                emptyList()
            }
        }
    }
}
