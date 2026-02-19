package edu.temple.assignment3

import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import edu.temple.assignment3.R.id.spinner

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinner)

        val colorArray = arrayOf("RED", "BLUE", "GREEN")
        spinner.adapter = ColorAdapter(this, colorArray)
    }
}