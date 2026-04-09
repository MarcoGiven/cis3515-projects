package edu.temple.oneplayer

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    lateinit var backPressCallback: OnBackPressedCallback

    private val searchURL = "https://kamorris.com/lab/audlibplayer/searchbooks.php?query="

    private val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(this)
    }

    private val isSingleContainer : Boolean by lazy{
        findViewById<View>(R.id.container2) == null
    }

    private val bookViewModel : BookViewModel by lazy {
        ViewModelProvider(this)[BookViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        findViewById<View>(R.id.searchImageButton).setOnClickListener {
            onSearchRequested()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (Intent.ACTION_SEARCH == intent!!.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also {
                searchBooks(it)

                // Unselect previous book selection
                bookViewModel.clearSelectedBook()

                // Remove any unwanted DisplayFragments instances from the stack
                supportFragmentManager.popBackStack()
            }
        }

    }

    private fun searchBooks(searchTerm: String) {
        requestQueue.add(
            JsonArrayRequest(searchURL + searchTerm,
                { bookViewModel.updateBooks(it) },
                { Toast.makeText(this, it.networkResponse.toString(), Toast.LENGTH_SHORT).show() })
        )
    }
}