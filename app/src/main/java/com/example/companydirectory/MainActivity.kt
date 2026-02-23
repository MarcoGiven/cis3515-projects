package com.example.companydirectory

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var employeeListSpinner: Spinner
    lateinit var employeeNameTextView: TextView
    lateinit var employeeDepartmentTextView: TextView
    lateinit var employeeEmailTextView: TextView
    lateinit var employeeTelephoneTextView: TextView
    lateinit var employeeProfilePictureImageView: ImageView
    lateinit var directReportRecyclerView: RecyclerView
    lateinit var nameSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        employeeListSpinner = findViewById(R.id.nameSpinner)
        employeeNameTextView = findViewById(R.id.nameTextView)
        employeeDepartmentTextView = findViewById(R.id.departmentTextView)
        employeeEmailTextView = findViewById(R.id.emailTextView)
        employeeTelephoneTextView = findViewById(R.id.telephoneTextView)
        employeeProfilePictureImageView = findViewById(R.id.profileImageView)
        directReportRecyclerView = findViewById(R.id.reportsRecyclerView)
        nameSpinner = findViewById(R.id.nameSpinner)

        // This returns employee data
        val employees = getEmployees()
        val names = employees.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nameSpinner.adapter = adapter

        nameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedEmployee = employees[p2]
                displayEmployee(selectedEmployee)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun displayEmployee(selectedEmployee: Employee) {
        employeeNameTextView.text = selectedEmployee.name
    }
}