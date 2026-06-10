package edu.temple.signupform

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val welcome = findViewById<TextView>(R.id.welcomeText)
        val name = findViewById<EditText>(R.id.editTextText)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val passwordConfirm = findViewById<EditText>(R.id.editTextTextPasswordConfirm)
        val save = findViewById<Button>(R.id.button)

        val programs = arrayOf("Please Select Your Program", "Information Science", "Computer Science", "Math and CS", "Data Science", "Other")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, programs)
        spinner.adapter = adapter

        // Takes a string (warning message) returns a boolean
        fun EditText.blankError(warning: String) : Boolean {
            if(text.toString().trim().isEmpty()){
                // this (EditText object) error = warning message
                this.error = warning
                return true
            } else {
                return false
            }
        }

        // Takes another EditText object and warning message, returns a boolean
        fun EditText.matchError(other: EditText, warning: String) : Boolean {
            val password1 = this.text.toString()
            val password2 = other.text.toString()

            if(password1 == password2){
                return true
            } else {
                this.error = warning
                return false
            }
        }



        // When click save, check all the fields have text AND passwords match, if so update textview welcome message
        save.setOnClickListener {
            val hasBlank = name.blankError("Name Required") || email.blankError("Email Required") || password.blankError("Password Required") || passwordConfirm.blankError("Confirm Password")
            val hasMatch = password.matchError(passwordConfirm, "Passwords Must Match")
            val notSelected = spinner.selectedItemPosition == 0

            // For the spinner -> if position is 0 (instructions) use toast to display error
            if(notSelected){
                Toast.makeText(this, "Error: Use the Dropdown to Select a Program", Toast.LENGTH_SHORT).show()
            }

            if(!hasBlank && hasMatch && !notSelected){
                welcome.text = "Welcome, ${ name.text }, to the SignUpForm App".also { welcome.text = it }
            }
        }

    }
}