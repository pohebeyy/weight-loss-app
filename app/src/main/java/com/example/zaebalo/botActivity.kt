package com.example.zaebalo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class botActivity : AppCompatActivity() {
    private lateinit var editTextUserInput: EditText
    private lateinit var textViewBotResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bot)

        editTextUserInput = findViewById(R.id.editTextUserInput)
        textViewBotResponse = findViewById(R.id.textViewBotResponse)

        val buttonSend: Button = findViewById(R.id.buttonSend)
        buttonSend.setOnClickListener { onSendClicked() }
    }

    private fun onSendClicked() {
        val userInput = editTextUserInput.text.toString()
        if (userInput.isNotBlank()) {
            displayUserMessage(userInput)
            displayOperatorMessage()
        }
    }

    private fun displayUserMessage(message: String) {
        val currentText = textViewBotResponse.text.toString()
        val newText = "$currentText\nYou: $message"
        textViewBotResponse.text = newText
    }

    private fun displayOperatorMessage() {
        val currentText = textViewBotResponse.text.toString()
        val newText = "$currentText\nOperator: С вами скоро свяжется оператор"
        textViewBotResponse.text = newText
    }
}
