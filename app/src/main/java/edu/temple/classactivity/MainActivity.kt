package edu.temple.classactivity

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var decreaseButton: Button
    lateinit var increaseButton: Button
    lateinit var textView: TextView
    lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        decreaseButton = findViewById(R.id.buttonDecrease)
        increaseButton = findViewById(R.id.buttonIncrease)
        textView = findViewById(R.id.textView)
        checkBox = findViewById(R.id.checkBox)




        decreaseButton.setOnClickListener {
            if (checkBox.isChecked){
                textView.textSize -= 1f
            }
        }

        increaseButton.setOnClickListener {
            if (checkBox.isChecked){
                textView.textSize += 1f
            }
        }

    }





}