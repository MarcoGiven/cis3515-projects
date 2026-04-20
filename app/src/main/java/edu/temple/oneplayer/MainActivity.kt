package edu.temple.oneplayer

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import edu.temple.flossaudioplayer.AudioBookPlayerService

class MainActivity : AppCompatActivity(), BookControlFragment.BookControlInterface {
    lateinit var backPressCallback: OnBackPressedCallback

    private val searchURL = "https://kamorris.com/lab/audlibplayer/searchbooks.php?query="
    lateinit var progressSeekBar: SeekBar
    private var audioBinder: AudioBookPlayerService.MediaControlBinder? = null
    private var isBound = false

    private val progressHandler = Handler(Looper.getMainLooper()) { msg ->
        val progressObj = msg.obj as? AudioBookPlayerService.BookProgress
        progressObj?.let {
            val currentBook = it.book as Book
            findViewById<TextView>(R.id.nowPlayingTextView).text = "Now Playing: ${currentBook.title}"

            val percent = (it.progress.toFloat() / currentBook.duration * 100).toInt()
            progressSeekBar.progress = percent
        }
        true
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            audioBinder = p1 as AudioBookPlayerService.MediaControlBinder
            isBound = true

            audioBinder?.setProgressHandler(progressHandler)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
            audioBinder = null
        }
    }


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

        progressSeekBar = findViewById(R.id.progressSeekBar)
        progressSeekBar.max = 100


        progressSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                p0: SeekBar?,
                p1: Int,
                p2: Boolean
            ) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                val selectedBook = bookViewModel.getSelectedBook()?.value

                if(isBound && selectedBook != null){
                    val newProgress = (selectedBook.duration * (progressSeekBar.progress / 100f)).toInt()

                    audioBinder?.seekTo(newProgress)
                }
            }

        })

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

    override fun onStart() {
        super.onStart()
        Intent(this, AudioBookPlayerService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if(isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }

    override fun play() {
        if(isBound) {
            bookViewModel.getSelectedBook()?.value?.let { selectedBook ->
                audioBinder?.play(selectedBook)
                findViewById<TextView>(R.id.nowPlayingTextView).text = "Now Playing: ${selectedBook.title}"
            }
        }
    }

    override fun pause() {
        if(isBound) {
            audioBinder?.pause()
        }
    }
}