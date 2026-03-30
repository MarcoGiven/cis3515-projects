package edu.temple.oneplayer

class BookList {

    val books = ArrayList<Book>()


    // add(Book) : void – add a book to the booklist
    fun add(book: Book){
        books.add(book)
    }

    // get(int) : Book – retrieve a book from the collection at the given index
    fun get(i: Int) : Book {
        return books[i]
    }

    // size(): int – return the number of books in the booklist
    fun size(): Int {
        return books.size
    }
}