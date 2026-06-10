package com.example.companydirectory

fun getEmployees() : List<Employee> {
    return MutableList<Employee>(0){Employee()}.apply {
        add(Employee(name="Jone Smith", profileId=R.drawable.p1, department="Sales", phone="555-123-4567", email="Jone.smith@ourcompany.com", directReports=listOf("Patricia White")))
        add(Employee(name="Emile Johnson", profileId=R.drawable.p2, department="Engineering", phone="555-987-6543", email="emile.johnson@ourcompany.com", directReports=listOf("Jeffrey Martinez", "Daniel Moore")))
        add(Employee(name="Michael Williams", profileId=R.drawable.p3, department="Marketing", phone="555-111-2222", email="michael.williams@ourcompany.com", directReports=listOf("Ashley Miller")))
        add(Employee(name="Jermaine Brown", profileId=R.drawable.p4, department="Engineering", phone="555-333-4444", email="jermaine.brown@ourcompany.com", directReports=listOf()))
        add(Employee(name="Dana Davis", profileId=R.drawable.p5, department="Sales", phone="555-555-6666", email="dana.davis@ourcompany.com", directReports=listOf("Josephine Thompson")))
        add(Employee(name="Ashley Miller", profileId=R.drawable.p6, department="Marketing", phone="555-777-8888", email="ashley.miller@ourcompany.com", directReports=listOf("William Harris")))
        add(Employee(name="Christine Wilson", profileId=R.drawable.p7, department="Engineering", phone="555-999-0000", email="christine.wilson@ourcompany.com", directReports=listOf("Mary Martin")))
        add(Employee(name="Shah Garcia", profileId=R.drawable.p8, department="Sales", phone="555-101-1111", email="shah.garcia@ourcompany.com", directReports=listOf()))
        add(Employee(name="Matthew Rodriguez", profileId=R.drawable.p9, department="Marketing", phone="555-121-1313", email="matthew.rodriguez@ourcompany.com", directReports=listOf("Garcia Brody")))
        add(Employee(name="Jeffrey Martinez", profileId=R.drawable.p10, department="Engineering", phone="555-141-1515", email="jeffrey.martinez@ourcompany.com", directReports=listOf("Robert Jackson")))
        add(Employee(name="Jamie Anderson", profileId=R.drawable.p11, department="Sales", phone="555-161-1717", email="jamie.anderson@ourcompany.com", directReports=listOf("Susan Taylor")))
        add(Employee(name="Larry Thomas", profileId=R.drawable.p12, department="Marketing", phone="555-181-1919", email="larry.thomas@ourcompany.com", directReports=listOf()))
        add(Employee(name="Robert Jackson", profileId=R.drawable.p13, department="Engineering", phone="555-202-2121", email="robert.jackson@ourcompany.com", directReports=listOf()))
        add(Employee(name="Patricia White", profileId=R.drawable.p14, department="Sales", phone="555-222-2323", email="patricia.white@ourcompany.com", directReports=listOf()))
        add(Employee(name="William Harris", profileId=R.drawable.p15, department="Marketing", phone="555-242-2525", email="william.harris@ourcompany.com", directReports=listOf()))
        add(Employee(name="Mary Martin", profileId=R.drawable.p16, department="Engineering", phone="555-262-2727", email="mary.martin@ourcompany.com", directReports=listOf()))
        add(Employee(name="Josephine Thompson", profileId=R.drawable.p17, department="Sales", phone="555-282-2929", email="josephine.thompson@ourcompany.com", directReports=listOf()))
        add(Employee(name="Garcia Brody", profileId=R.drawable.p18, department="Marketing", phone="555-303-3131", email="garcia@ourcompany.com", directReports=listOf()))
        add(Employee(name="Daniel Moore", profileId=R.drawable.p19, department="Engineering", phone="555-323-3333", email="daniel.moore@ourcompany.com", directReports=listOf()))
        add(Employee(name="Susan Taylor", profileId=R.drawable.p20, department="Sales", phone="555-343-3535", email="susan.taylor@ourcompany.com", directReports=listOf()))
    }
}

class Employee (val name: String = "", val profileId: Int = 0, val department: String = "", val phone: String = "", val email: String = "", val directReports: List<String> = MutableList(1){""}) {
    /**
        Two employees are identical if their names match.
        An Employee can be compared to another employee object or a string of the employee name
    **/
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Employee -> name == other.name
            is String -> name == other
            else -> false
        }
    }

    override fun toString(): String {
        return name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}