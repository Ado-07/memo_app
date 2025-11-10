package com.example.fastnote

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class NoteRepositoryTest {

    private lateinit var noteRepository: NoteRepository

    @Before
    fun setUp() {
        noteRepository = NoteRepository(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun saveNote_and_getNotes() = runBlocking {
        // Given
        val note = "This is a test note"

        // When
        noteRepository.saveNote(note)

        // Then
        val notes = noteRepository.getNotes()
        assertEquals(1, notes.size)
        assertTrue(notes.contains(note))
    }
}
