class Fahrenheit(private var currentTemp: Double) : Temperature (currentTemp){

    // fahrenheit = (k - 273.15) * 9/5 + 32
    override fun getTemperature(): Double {
        return (super.getTemperature() - 273.15) * 9/5 + 32
    }

}
