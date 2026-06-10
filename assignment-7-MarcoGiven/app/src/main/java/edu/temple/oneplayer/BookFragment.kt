package edu.temple.oneplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.w3c.dom.Text

class BookFragment : Fragment(R.layout.fragment_book) {

    private var book: Book? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        book?.let {
            view.findViewById<TextView>(R.id.titleTextView).text = it.title
            view.findViewById<TextView>(R.id.authorTextView).text = it.author
        }
    }

    fun setBook(book: Book) {
        this.book = book

        view?.let {
            it.findViewById<TextView>(R.id.titleTextView).text = book.title
            it.findViewById<TextView>(R.id.authorTextView).text = book.author
        }
    }
}