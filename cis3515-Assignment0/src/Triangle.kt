import kotlin.math.sqrt

open class Triangle(name: String) : Shape(name) {

    private var sideA : Double = 0.0
    private var sideB : Double = 0.0
    private var sideC : Double = 0.0

    fun setDimensions(sideA: Double, sideB: Double, sideC: Double){
        this.sideA = sideA
        this.sideB = sideB
        this.sideC = sideC
    }

    override fun printDimensions() {
        println("Triangle: side A = ${sideA}, side B = ${sideB}, side C = $sideC")
    }

    override fun getArea(): Double {
        val s = (sideA + sideB + sideC) / 2
        return sqrt((s * (s - sideA) * (s - sideB) * (s - sideC)))
    }
}