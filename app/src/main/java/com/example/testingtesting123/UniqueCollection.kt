package com.example.testingtesting123

class UniqueCollection {
    private val collection = ArrayList<Item>()


    /** Add an item to the collection */
    fun addItem (item: Item) {
        if (!collection.contains(item))
            collection.add(item)
    }

    /** Return an element from the collection at the specified position */
    fun get (position: Int) = collection[position]


    /** Remove an item from the collection */
    fun remove (item: Item) {
        !collection.remove(item)
    }


    // TODO 3: Complete this function such that the pre-written Unit Test (TDD) passes

    /** Clear all items from the collection */
    fun clear () {
        while (collection.isNotEmpty()) {
            remove(collection.first())
        }
    }

    /** Return the side of the collection */
    fun size() = collection.size


}