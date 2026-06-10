abstract class Temperature (private var currentTemp: Double) {
    // current temp is kelvin
    // one function get temperature -- each class implements get temperature differently



    fun setTemperature(temp: Double) {
        currentTemp = temp
    }

    open fun getTemperature(): Double {
       return currentTemp
    }

}


