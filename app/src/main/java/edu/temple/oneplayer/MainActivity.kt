package edu.temple.oneplayer

import android.app.SearchManager
import android.content.ComponentName
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

class MainActivity : AppCompatActivity(), BookControlFragment.BookControlInterface  {
    lateinit var backPressCallback: OnBackPressedCallback

    private val searchURL = "https://kamorris.com/lab/audlibplayer/searchbooks.php?query="

    private lateinit var progressSeekBar: SeekBar
    lateinit var bookServiceIntent: Intent
    var currentProgress: Int = 0


    var mediaControllerBinder: AudioBookPlayerService.MediaControlBinder? = null

    val bookProgressHandler = Handler(Looper.getMainLooper()) {

        with (it.obj as AudioBookPlayerService.BookProgress) {

            // Update ViewModel state based on whether we're seeing the currently playing book
            // from the service for the first time
            if (!bookViewModel.hasBookBeenPlayed()) {
                bookViewModel.setBookPlayed(true)
                bookViewModel.setPlayingBook(book as Book)
                bookViewModel.setSelectedBook(book as Book)
            }

            currentProgress = progress // store current
            // Update seekbar with progress of current book as a percentage
            progressSeekBar.progress = ((progress.toFloat() / (book as Book).duration) * 100).toInt()
        }
        true
    }

    // Callback that is invoked when (un)binding is complete
    private val bookServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mediaControllerBinder = service as AudioBookPlayerService.MediaControlBinder
            mediaControllerBinder?.setProgressHandler(bookProgressHandler)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mediaControllerBinder = null
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

        val nowPlayingTextView = findViewById<TextView>(R.id.nowPlayingTextView)
        progressSeekBar = findViewById<SeekBar?>(R.id.progressSeekBar).apply {
            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {

                        // If the user is dragging the SeekBar, convert progress percentage
                        // to value in seconds and seek to position
                        bookViewModel.getSelectedBook()?.value?.let {book ->
                            mediaControllerBinder?.run {
                                if (isPlaying) {
                                    seekTo(((progress.toFloat() / 100) * book.duration).toInt())
                                }
                            }
                        }
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })
        }

        // Use Back gesture to clear selected book
        val backPressCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // BackPress clears the selected book
                bookViewModel.clearSelectedBook()
                Log.d("Back", "Pressed")
                // Remove responsibility for handling back gesture
                isEnabled = false
                // Trigger back gesture
                onBackPressedDispatcher.onBackPressed()
            }
        }

        onBackPressedDispatcher.addCallback(backPressCallback)

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
                backPressCallback.isEnabled = true
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

        // Always show currently playing book
        bookViewModel.getPlayingBook()?.observe(this){
            nowPlayingTextView.text = String.format(getString(R.string.now_playing), it.title)
        }

        findViewById<View>(R.id.searchImageButton).setOnClickListener {
            onSearchRequested()
        }

        bookServiceIntent = Intent(this, AudioBookPlayerService::class.java)

        // store search
        getSharedPreferences("search_cache", MODE_PRIVATE)
            .getString("last_results", null)?.let {
                bookViewModel.updateBooks(org.json.JSONArray(it))
            }

        // Bind in order to send commands
        bindService(bookServiceIntent, bookServiceConnection, BIND_AUTO_CREATE)
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
                {
                    bookViewModel.updateBooks(it)
                    getSharedPreferences("search_cache", MODE_PRIVATE)
                        .edit()
                        .putString("last_results", it.toString())
                        .apply()
                },
                {
                    Toast.makeText(this, it.message ?: "Search failed", Toast.LENGTH_SHORT).show()
                }
            )
        )
    }

    override fun playBook() {
        bookViewModel.getSelectedBook()?.value?.apply {
            mediaControllerBinder?.run {

                if(isPlaying) {
                    bookViewModel.getPlayingBook()?.value?.let { currentBook ->
                        getSharedPreferences("playback_positions", MODE_PRIVATE)
                            .edit()
                            .putInt("book_${currentBook.book_id}", currentProgress)
                            .putLong("book_${currentBook.book_id}_time", System.currentTimeMillis())
                            .apply()
                    }
                }

                bookViewModel.setBookPlayed(false)
                val file = java.io.File(filesDir, "book_${book_id}.mp3")

                if(file.exists()) {
                    bookFile = file
                    val savedPosition = getSharedPreferences("playback_positions", MODE_PRIVATE)
                        .getInt("book_${book_id}", 0)

                     val savedTime = getSharedPreferences("playback_positions", MODE_PRIVATE)
                         .getLong("book_${book_id}_time", 0L)

                    val elapsedTime = System.currentTimeMillis() - savedTime

                    val rewind = if (elapsedTime < 60_000L) 5 // 1 minute = 60,000 MS
                        else if (elapsedTime < 300_000) 15    // 5 minutes = 300,000 MS
                        else if (elapsedTime < 3_600_000L) 30 // 1 hour = 3,600,000 MS
                        else 60

                    // so I can test the if statement time changes
                    Log.d("Playback", "savedPosition: $savedPosition")
                    Log.d("Playback", "elapsedMs: $elapsedTime")
                    Log.d("Playback", "rewindSeconds: $rewind")
                    Log.d("Playback", "resumePos: ${maxOf(savedPosition - rewind)}")

                    play(this@apply, maxOf(0, savedPosition - rewind))
                } else {
                    play(this@apply)
                    Thread {
                        try {
                            val readBytes = java.net.URL("https://kamorris.com/lab/oneplayer/downloadbook.php?id=${book_id}").readBytes()
                            val downloadedFile = java.io.File(filesDir, "book_${book_id}.mp3")
                            downloadedFile.writeBytes(readBytes)
                            runOnUiThread { bookFile = downloadedFile }
                        } catch (e: Exception ){ }
                    }.start()
                }


                // Start service to ensure it keeps playing even if the activity is destroyed
                startService(bookServiceIntent)
            }
        }
    }

    override fun pauseBook() {
        mediaControllerBinder?.run {
            if (isPlaying) {
                bookViewModel.getPlayingBook()?.value?.let { currentBook ->
                    getSharedPreferences("playback_positions", MODE_PRIVATE)
                        .edit()
                        .putInt("book_${currentBook.book_id}", currentProgress)
                        .putLong("book_${currentBook.book_id}_time", System.currentTimeMillis())
                        .apply()
                }
                stopService(bookServiceIntent)
            } else {
                startService(bookServiceIntent)
            }
            pause()
        }
    }

    override fun onDestroy() {
        unbindService(bookServiceConnection)
        super.onDestroy()
    }

}