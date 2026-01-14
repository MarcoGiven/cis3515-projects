interface Dimensionable {
    fun printDimensions() {
        println("Square: width = ${width}. height = $height")
        println("Circle: radius = $radius")
        println("Triangle: side A = ${sideA}, side B = ${sideB}, side C = $sideC")
        println("Equilateral Triangle: all sides = $side")
    }
}