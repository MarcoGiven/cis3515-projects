package edu.temple.oneplayer

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var backPressCallback: OnBackPressedCallback

    private val isSingleContainer : Boolean by lazy{
        findViewById<View>(R.id.container2) == null
    }

    private val bookViewModel : BookViewModel by lazy {
        ViewModelProvider(this)[BookViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        handleIntent(intent)

        // Use Back gesture to clear selected book
        backPressCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // BackPress clears the selected book
                bookViewModel.clearSelectedBook()
                Log.d("Back", "Pressed")
                // Remove responsibility for handling back gesture
                backPressCallback.isEnabled = false
                // Trigger back gesture
                onBackPressedDispatcher.onBackPressed()
            }

        }

        onBackPressedDispatcher.addCallback(this, backPressCallback)

        // If we're switching from one container to two containers
        // clear BookPlayerFragment from container1
        if (supportFragmentManager.findFragmentById(R.id.container1) is BookPlayerFragment) {
            supportFragmentManager.popBackStack()
        }

        // If this is the first time the activity is loading, go ahead and add a BookListFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container1, BookListFragment())
                .commit()
        } else
        // If activity loaded previously, there's already a BookListFragment
        // If we have a single container and a selected book, place it on top
            if (isSingleContainer && bookViewModel.getSelectedBook()?.value != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container1, BookPlayerFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }

        // If we have two containers but no BookPlayerFragment, add one to container2
        if (!isSingleContainer && supportFragmentManager.findFragmentById(R.id.container2) !is BookPlayerFragment)
            supportFragmentManager.beginTransaction()
                .add(R.id.container2, BookPlayerFragment())
                .commit()


        // Respond to selection in portrait mode using flag stored in ViewModel
        bookViewModel.getSelectedBook()?.observe(this){
            if (!bookViewModel.hasViewedSelectedBook()) {
                if (isSingleContainer) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container1, BookPlayerFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit()
                }
                bookViewModel.markSelectedBookViewed()
            }
        }
    }


    override fun onNewIntent(intent: Intent){
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent){
        if(Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                if(isSingleContainer){
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.container1)

                    if (currentFragment is BookPlayerFragment) {
                        supportFragmentManager.popBackStack()
                        bookViewModel.clearSelectedBook()
                    }
                }

                searchBooks(query)
            }
        }
    }

    private fun searchBooks(query: String) {
        if (query == "*"){
            getBooks("https://kamorris.com/lab/audlibplayer/searchbooks.php")
        } else {
            getBooks("https://kamorris.com/lab/audlibplayer/searchbooks.php?query=$query")
        }
    }

    private fun getBooks(url: String) {
        val newBookList = BookList()

        Volley.newRequestQueue(this).add(
            JsonArrayRequest(Request.Method.GET, url, null, { response ->
                    for(i in 0 until response.length()) {
                        val obj = response.getJSONObject(i)
                        newBookList.add(parseBook(obj))
                    }
                    bookViewModel.setBookList(newBookList)
                }, { error ->
                    Log.e("Books", "Fetch Failed: ${error.message}")
                }
            )
        )
    }

    private fun parseBook(obj: JSONObject): Book {
        val title = obj.getString("book_title")
        val author = obj.getString("author_name")
        val id = obj.getInt("book_id")
        val coverURI = obj.getString("cover_uri")
        return Book(title, author, id, coverURI)
    }


}