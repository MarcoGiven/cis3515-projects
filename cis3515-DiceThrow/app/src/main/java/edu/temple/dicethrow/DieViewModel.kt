package edu.temple.dicethrow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class DieViewModel : ViewModel() {
    var dieSides: Int = 6
    private val dieRoll = MutableLiveData<Int>()

    fun throwDie(){
        dieRoll.value = (Random.nextInt(1, dieSides + 1))
    }

    fun getDieRoll() : LiveData<Int> {
        return dieRoll
    }

//    fun setDieSides(sides: Int) {
//        dieSides = sides
//    }


}