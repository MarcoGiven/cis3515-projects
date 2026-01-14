import kotlin.math.sqrt

abstract class Shape (_name : String) : Dimensionable {
    var name = _name

    fun getArea(name: String) : Double{
        var area = 0.0
        if(name=="Square") area = width * height
        if(name=="Circle") area = 3.14 * (radius * radius)
        if(name=="Triangle") {
            val s = (sideA + sideB + sideC) / 2
            area = sqrt((s * (s - sideA) * (s - sideB) * (s - sideC)))
        }
        if(name=="EquilateralTriangle") {
            val s = side / 2
            area = sqrt(s * (s - side) * (s - side) * (s - side))
        }
        return area
    }

}