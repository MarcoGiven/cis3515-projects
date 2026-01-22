fun main() {
    val square: Shape = Square("Square")
    print("Enter Square length: ")
    val length = readLine()!!.toDouble()
    print("Enter Square height: ")
    val height = readLine()!!.toDouble()

    val circle: Shape = Circle("Circle")
    print("Enter Circle Radius: ")
    val radius = readLine()!!.toDouble()

    val triangle: Shape = Triangle("Triangle")
    print("Enter Triangle SideA length: ")
    val sideA = readLine()!!.toDouble()
    print("Enter Triangle SideB length: ")
    val sideB = readLine()!!.toDouble()
    print("Enter Triangle SideC length: ")
    val sideC = readLine()!!.toDouble()

    val equilateraltriangle: Shape = EquilateralTriangle("EquilateralTriangle")
    print("Enter EquilateralTriangle side length: ")
    val side = readLine()!!.toDouble()


    (square as Square).setDimensions(length, height)
    square.printDimensions()
    println("Area: ${square.getArea()}")

    (circle as Circle).setDimensions(radius)
    circle.printDimensions()
    println("Area: ${circle.getArea()}")

    (triangle as Triangle).setDimensions(sideA, sideB, sideC)
    triangle.printDimensions()
    println("Area: ${triangle.getArea()}")

    (equilateraltriangle as EquilateralTriangle).setDimensions(side)
    equilateraltriangle.printDimensions()
    println("Area: ${equilateraltriangle.getArea()}")


}