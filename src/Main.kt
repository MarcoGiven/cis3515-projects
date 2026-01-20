fun main () {

    // want val temp: Temperature = Celsius(26.0)
    val temp: Temperature = Celsius(26.0)
    println("Current temperature in Celsius is: ${temp.getTemperature()}")


    val temp2: Temperature = Kelvin(26.0)
    println("Current temperature in Kelvin is: ${temp2.getTemperature()}")

    val temp3: Temperature = Fahrenheit(26.0)
    println("Current temperature in Fahrenheit is: ${temp3.getTemperature()}")
}
