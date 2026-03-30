package edu.temple.oneplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

data class Book(
    val title: String,
    val author: String
)


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}