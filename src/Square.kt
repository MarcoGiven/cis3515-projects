class Square(name: String) : Shape(name) {

    private var length: Double = 0.0
    private var height: Double = 0.0

    fun setDimensions(length: Double, height: Double) {
        this.length = length
        this.height = height
    }

    override fun printDimensions() {
        println("Square: width = ${length}. height = $height")
    }

    override fun getArea() : Double {
        return length * height
    }



}