class Temperature (private var currentTemp: Double) {
    // current temp is kelvin

    // return double
    fun getTempInCel() = currentTemp - 273.15
    fun getTempInFar() = (getTempInCel() * 1.8) + 32
    fun getTempInKel() = currentTemp

    
    fun setCurrentTemp(temp: Double, unit: TempUnit) {

        currentTemp = if (unit == TempUnit.KELVIN)
            temp
        else if (unit == TempUnit.CELCIUS)
            temp + 273.15
        else
            5/9.0 * (temp - 32) + 273.15
     }

}

enum class TempUnit {
    CELCIUS, KELVIN, FAHRENHEIT
}
