package edu.temple.cis3515_startingservices

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        val inputTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val startButton = findViewById<Button>(R.id.button)


        startButton.setOnClickListener {
            val startValue = inputTextNumber.text.toString().trim().toIntOrNull()

            if(startValue == null || startValue < 0){
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val intent = Intent(this, CountdownService::class.java).apply {
                putExtra(CountdownService, START_VALUE, startValue)
            }

            startService(intent)


        }



    }
}