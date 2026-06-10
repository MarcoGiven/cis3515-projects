class Celsius(private var currentTemp: Double) : Temperature(currentTemp) {

    // celsius = kelvin - 273.15

    override fun getTemperature(): Double {
        return super.getTemperature() - 273.15
    }

}
