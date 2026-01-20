abstract class Temperature (private var currentTemp: Double) {
    // current temp is kelvin

    // one function get temperature -- each class implements get temperature differently
    // fun getTempInCel() = currentTemp - 273.15
    // fun getTempInFar() = (getTempInCel() * 1.8) + 32
    // fun getTempInKel() = currentTemp


    fun setTemperature(temp: Double) {
        currentTemp = temp
    }

    fun getTemperature(): Double {
       return currentTemp
    }

}


