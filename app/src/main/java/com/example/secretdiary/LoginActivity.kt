package com.example.secretdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val layoutPin = findViewById<TextInputLayout>(R.id.pin_log_layout)
        val pinEditText = findViewById<EditText>(R.id.pin_log_et)
        val loginButton = findViewById<Button>(R.id.login_btn)


        if (!havePIN()) {
            layoutPin.hint = getString(R.string.create_pin)

            loginButton.setOnClickListener {

                rememberPIN(pinEditText.text.toString())

                showDiaryActivity()
            }

        } else {

            // clear error message
            pinEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    layoutPin.error = null
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            // click on LOG IN button
            loginButton.setOnClickListener {
                val pinCorrect = isPinCorrect(pinEditText.text.toString())

                if (pinCorrect)
                    showDiaryActivity()
                else
                    showError(layoutPin)
            }
        }

    }

    private fun havePIN(): Boolean {
        ModelPreferencesManager.with(application)

        val pin = ModelPreferencesManager.get<String>(ModelPreferencesManager.KEY_PIN)

        return pin != null
    }

    private fun rememberPIN(pin: String) {
        ModelPreferencesManager.put(pin, ModelPreferencesManager.KEY_PIN)
    }

    private fun isPinCorrect(pin: String): Boolean =
        pin == ModelPreferencesManager.get<String>(ModelPreferencesManager.KEY_PIN)

    private fun showDiaryActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun showError(layoutPin: TextInputLayout) {
        val errorMessage = getString(R.string.wrong_PIN_message)
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        layoutPin.error = errorMessage
    }
}