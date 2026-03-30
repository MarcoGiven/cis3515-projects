package edu.temple.oneplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


data class Book(
    val title: String,
    val author: String
)

class MainActivity : AppCompatActivity() {

    private lateinit var bookList: BookList
    private lateinit var bookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]

        if (bookViewModel.bookListLiveData.value == null) {
            bookList = BookList()
            createBookList()
            bookViewModel.setBookList(bookList)
        }

        val twoPane = findViewById<View?>(R.id.fragmentContainer2) != null

        if (savedInstanceState == null) {
            if (twoPane) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer1, BookListFragment())
                    .commit()

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer2, BookPlayerFragment())
                    .commit()
            } else {
                if (bookViewModel.selectedBookLiveData.value == null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer1, BookListFragment())
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer1, BookPlayerFragment())
                        .commit()
                }
            }
        }
    }

    fun showBookPlayerInSinglePane() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer1, BookPlayerFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun createBookList() {
        val titles = resources.getStringArray(R.array.book_titles)
        val authors = resources.getStringArray(R.array.book_authors)

        for (i in titles.indices) {
            bookList.add(Book(titles[i], authors[i]))
        }
    }
}