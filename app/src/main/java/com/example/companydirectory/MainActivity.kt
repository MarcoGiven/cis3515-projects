package com.example.companydirectory

import android.os.Bundle
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

        // This returns employee data
        val employees = getEmployees()

    }
}