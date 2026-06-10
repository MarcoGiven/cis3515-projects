package edu.temple.oneplayer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class BookControlFragment : Fragment() {


    interface BookControlInterface {
        fun play()
        fun pause()
    }

    lateinit var playButton: ImageButton
    lateinit var pauseButton: ImageButton

    private var bookControlInterface: BookControlInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is BookControlInterface) {
            bookControlInterface = context
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_control, container, false).apply {
            playButton = findViewById(R.id.playButton)
            pauseButton = findViewById(R.id.pauseButton)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playButton.setOnClickListener {
            bookControlInterface?.play()
        }

        pauseButton.setOnClickListener {
            bookControlInterface?.pause()
        }
    }


}