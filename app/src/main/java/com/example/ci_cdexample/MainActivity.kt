package com.example.ci_cdexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCenter.start(
            application, "fbc3e435-f898-459b-825c-30f695de0892",
            Analytics::class.java, Crashes::class.java
        )

        val number1 = findViewById<EditText>(R.id.number1)
        val number2 = findViewById<EditText>(R.id.number2)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val resultText = findViewById<TextView>(R.id.resultText)

        btnAdd.setOnClickListener {
            val num1 = number1.text.toString().toDoubleOrNull()
            val num2 = number2.text.toString().toDoubleOrNull()

            if (num1 != null && num2 != null) {
                val result = num1 + num2
                resultText.text = "Result: $result"
            } else {
                resultText.text = "Please enter valid numbers"
            }
        }

        btnMultiply.setOnClickListener {
            try {
                val num1 = number1.text.toString().toDoubleOrNull()
                val num2 = number2.text.toString().toDoubleOrNull()

                if (num1 == null || num2 == null) {
                    resultText.text = "Please enter valid numbers"
                } else if (num1 == 0.0 || num2 == 0.0) {
                    throw IllegalArgumentException("Multiplication by zero is not allowed")
                } else {
                    val result = num1 * num2
                    resultText.text = "Result: $result"
                }
            } catch (e: IllegalArgumentException) {
                resultText.text = e.message
                Crashes.trackError(e)
            }
        }
    }
}
