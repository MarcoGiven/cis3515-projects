package edu.temple.oneplayer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

data class Book(
    val title: String,
    val author: String
)

private lateinit var bookList: BookList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        bookList = BookList()
        createBookList()

    }


    private fun createBookList() {
        val bookTitles = resources.getStringArray(R.array.book_titles)
        val bookAuthors = resources.getStringArray(R.array.book_authors)

        for (i in bookTitles.indices) {
            bookList.add(Book(bookTitles[i], bookAuthors[i]))
        }
    }
}