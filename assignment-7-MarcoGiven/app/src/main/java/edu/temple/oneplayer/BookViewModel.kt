package edu.temple.oneplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {


    val bookListLiveData = MutableLiveData<BookList>()
    val selectedBookLiveData = MutableLiveData<Book>()


    fun setBookList(bookList: BookList) {
        bookListLiveData.value = bookList
    }

    fun selectBook(book: Book) {
        selectedBookLiveData.value = book
    }
}