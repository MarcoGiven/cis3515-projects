package com.example.companydirectory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val names = employees.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        employeeListSpinner.adapter = adapter

        employeeListSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedEmployee = employees[p2]
                displayEmployee(selectedEmployee)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun displayEmployee(selectedEmployee: Employee) {
        employeeNameTextView.text = selectedEmployee.name
        employeeDepartmentTextView.text = selectedEmployee.department
        employeeEmailTextView.text = selectedEmployee.email
        employeeTelephoneTextView.text = selectedEmployee.phone
        employeeProfilePictureImageView.setImageResource(selectedEmployee.profileId)

    }
}

class DirectReportAdapter(private val reports: Array<String>) : RecyclerView.Adapter<DirectReportAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        init {
            textView = view.findViewById(android.R.id.text1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectReportAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DirectReportAdapter.ViewHolder, position: Int) {
        holder.textView.text = reports[position]
    }

    override fun getItemCount(): Int {
        return reports.size
    }

}