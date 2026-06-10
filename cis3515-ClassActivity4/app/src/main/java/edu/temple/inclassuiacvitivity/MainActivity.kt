package edu.temple.inclassuiacvitivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val displayTextView = findViewById<TextView>(R.id.textDisplay)

        /* TODO Step 2: Populate this array with multiples of 5 from 5 - 100*/
        //val numberArray = Array Of Numbers
        val numberArray = Array(20) { i -> (i + 1) * 5}


        /* TODO Step 3: Create adapter to display items from array in Spinner */
        //spinner.adapter = ArrayAdapter...
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numberArray)


        // TODO Step 4: Change TextView's text size to the number selected in the Spinner */
        //spinner.onItemSelectedListener = object: ...
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // PREVIOUS IMPLEMENTATION (Before gone over this parrt in class -> works fine)
                // val selectedNum = numberArray[p2]
                // displayTextView.textSize = selectedNum.toFloat()

                p0?.run {
                    displayTextView.textSize = getItemAtPosition(p2).toString().toFloat()
                }
            }

            // Must include to fix error
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }
}