package com.example.secretdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.secretdiary.databinding.ActivityMainBinding
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val listNotes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            val newText = binding.newWritingEt.text.toString()

            if (newText.isNotBlank())
                postNote(newText)
            else
                postToast()
        }

        binding.undoBtn.setOnClickListener {
            if (listNotes.isEmpty())
                Toast.makeText(this, "Your diary is empty", Toast.LENGTH_SHORT).show()
            else
                showUndoDialog()
        }
    }

    private fun showUndoDialog() {
        AlertDialog.Builder(this)
            .setTitle("Remove last note")
            .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                unpostNote()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun unpostNote() {
        removeNoteFromList()

        updateTv()
    }

    private fun removeNoteFromList() {
        listNotes.removeAt(0)
    }

    private fun postToast() {
        val textToast = "Empty or blank input cannot be saved"
        Toast.makeText(this, textToast, Toast.LENGTH_SHORT).show()
    }

    private fun postNote(newText: String) {
        addNoteToList(newText)

        updateTv()

        binding.newWritingEt.setText("")
    }

    private fun updateTv() {
        val text = listNotes.joinToString("\n\n") {
            "${it.date} ${it.time}\n${it.note}"
        }

        binding.diaryTv.text = text
    }

    private fun addNoteToList(newText: String) {
        val instantTime = Clock.System.now()
        val localDateTime = instantTime.toLocalDateTime(TimeZone.currentSystemDefault())
        val date = localDateTime.date.toString()
        val time = localDateTime.time.toString().take(8)

        val newNote = Note(
            date,
            time,
            newText
        )

        listNotes.add(0, newNote)
    }
}