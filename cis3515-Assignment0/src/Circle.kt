class Circle(name: String) : Shape(name) {

    private var radius : Double = 0.0


    fun setDimensions(radius: Double) {
        this.radius = radius
    }

    override fun printDimensions() {
        println("Circle: radius = $radius")
    }

    override fun getArea(): Double {
        return (radius * radius) * 3.14159
    }
}