package edu.temple.oneplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider


class BookPlayerFragment : Fragment(R.layout.fragment_book_player) {
    private lateinit var bookViewModel: BookViewModel
    private lateinit var bookFragment: BookFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookViewModel = ViewModelProvider(requireActivity())[BookViewModel::class.java]

        val existingFragment = childFragmentManager.findFragmentById(R.id.bookFragmentContainer)

        if (existingFragment is BookFragment) {
            bookFragment = existingFragment
        } else {
            bookFragment = BookFragment()
            childFragmentManager.beginTransaction()
                .replace(R.id.bookFragmentContainer, bookFragment)
                .commit()
        }

        bookViewModel.selectedBookLiveData.observe(viewLifecycleOwner) { selectedBook ->
            if (selectedBook != null) {
                bookFragment.setBook(selectedBook)
            }
        }
    }
}