package edu.temple.assignment3

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import edu.temple.assignment3.R.id.spinner
import kotlin.toString

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val myLayout = findViewById<View>(R.id.main)


        val colorArray = arrayOf("Select a Color", "RED", "BLUE", "GREEN")
        spinner.adapter = ColorAdapter(this, colorArray)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val selectedColor = parent!!.getItemAtPosition(position).toString()
                    myLayout.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }


    }
}