package com.example.fastnote

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteInputActivity : AppCompatActivity() {

    private lateinit var noteRepository: NoteRepository
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteRepository = NoteRepository(this)
        showInputDialog()
    }

    private fun showInputDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_note_input, null)
        val titleEditText = view.findViewById<EditText>(R.id.edit_text_title)
        val contentEditText = view.findViewById<EditText>(R.id.edit_text_content)
        val dateEditText = view.findViewById<EditText>(R.id.edit_text_date)
        val timeEditText = view.findViewById<EditText>(R.id.edit_text_time)
        val openAppButton = view.findViewById<ImageButton>(R.id.button_open_app)

        updateDateInView(dateEditText)
        updateTimeInView(timeEditText)

        dateEditText.setOnClickListener {
            showDatePickerDialog(dateEditText)
        }

        timeEditText.setOnClickListener {
            showTimePickerDialog(timeEditText)
        }

        openAppButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        AlertDialog.Builder(this, R.style.PopDialogTheme)
            .setView(view)
            .setPositiveButton("保存") { _, _ ->
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()
                if (title.isNotEmpty() || content.isNotEmpty()) {
                    val note = Note(
                        timestamp = calendar.timeInMillis,
                        title = title,
                        content = content
                    )
                    saveNote(note)
                }
                finish()
            }
            .setNegativeButton("キャンセル") { _, _ -> finish() }
            .setOnCancelListener { finish() }
            .show()
    }

    private fun showDatePickerDialog(dateEditText: EditText) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
            updateDateInView(dateEditText)
        }, year, month, day).show()
    }

    private fun showTimePickerDialog(timeEditText: EditText) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendar.set(Calendar.MINUTE, selectedMinute)
            updateTimeInView(timeEditText)
        }, hour, minute, true).show()
    }

    private fun updateDateInView(dateEditText: EditText) {
        val myFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(myFormat, Locale.JAPAN)
        dateEditText.setText(sdf.format(calendar.time))
    }

    private fun updateTimeInView(timeEditText: EditText) {
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.JAPAN)
        timeEditText.setText(sdf.format(calendar.time))
    }

    private fun saveNote(note: Note) {
        lifecycleScope.launch {
            noteRepository.saveNote(note)
        }
    }
}
