package com.example.testingtesting123

class Item (val name: String){
    override fun equals(other: Any?): Boolean {
        return if (other is Item) other.name.uppercase() == name.uppercase() else false
    }
}