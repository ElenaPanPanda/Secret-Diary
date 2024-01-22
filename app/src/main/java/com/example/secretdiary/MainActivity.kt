package com.example.secretdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.secretdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            val note = binding.newWritingEt.text.toString()

            if (note.isNotBlank())
                postNote(note)
            else
                postToast()
        }
    }

    private fun postToast() {
        val textToast = "Empty or blank input cannot be saved"
        Toast.makeText(this, textToast, Toast.LENGTH_SHORT).show()
    }

    private fun postNote(note: String) {
        binding.diaryTv.text = note
        binding.newWritingEt.setText("")
    }
}