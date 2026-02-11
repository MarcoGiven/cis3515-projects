package edu.temple.signupform

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val name = findViewById<EditText>(R.id.editTextText)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val passwordConfirm = findViewById<EditText>(R.id.editTextTextPasswordConfirm)
        val save = findViewById<Button>(R.id.button)

        val programs = arrayOf("Please Select Your Program", "Information Science", "Computer Science", "Math and CS", "Data Science", "Other")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, programs)
        spinner.adapter = adapter


        

    }
}