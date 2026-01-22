import kotlin.math.sqrt

class EquilateralTriangle(name: String) : Triangle(name) {

    private var side: Double = 0.0

    fun setDimensions(side: Double) {
        this.side = side
    }

    override fun printDimensions() {
        println("Equilateral Triangle: all sides = $side")
    }

    override fun getArea(): Double {
        val s = side / 2
        return sqrt(s * (s - side) * (s - side) * (s - side))
    }
}