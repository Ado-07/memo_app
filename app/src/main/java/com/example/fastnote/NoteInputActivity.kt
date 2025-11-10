package com.example.fastnote

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class NoteInputActivity : AppCompatActivity() {

    private lateinit var noteRepository: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteRepository = NoteRepository(this)
        showInputDialog()
    }

    private fun showInputDialog() {
        val editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("メモを記入")
            .setView(editText)
            .setPositiveButton("保存") { _, _ ->
                val note = editText.text.toString()
                if (note.isNotEmpty()) {
                    saveNote(note)
                }
                finish()
            }
            .setNegativeButton("キャンセル") { _, _ -> finish() }
            .setNeutralButton("アプリを開く") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setOnCancelListener { finish() }
            .show()
    }

    private fun saveNote(note: String) {
        lifecycleScope.launch {
            noteRepository.saveNote(note)
        }
    }
}
