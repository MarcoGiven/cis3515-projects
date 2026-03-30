package edu.temple.oneplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class BookListFragment : Fragment(R.layout.fragment_book_list) {
    private lateinit var bookViewModel: BookViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookViewModel = ViewModelProvider(requireActivity())[BookViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.bookRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        bookViewModel.bookListLiveData.observe(viewLifecycleOwner) { bookList ->
            recyclerView.adapter = BookAdapter(bookList)
        }
    }

    inner class BookAdapter(private val bookList: BookList) :
        RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

        inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.itemTitleTextView)
            val authorTextView: TextView = itemView.findViewById(R.id.itemAuthorTextView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_list_item, parent, false)
            return BookViewHolder(view)
        }

        override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
            val book = bookList.get(position)

            holder.titleTextView.text = book.title
            holder.authorTextView.text = book.author

            holder.itemView.setOnClickListener {
                bookViewModel.selectBook(book)

                val twoPane = requireActivity().findViewById<View?>(R.id.fragmentContainer2) != null

                if (!twoPane) {
                    (requireActivity() as MainActivity).showBookPlayerInSinglePane()
                }
            }
        }

        override fun getItemCount(): Int {
            return bookList.size()
        }
    }
}