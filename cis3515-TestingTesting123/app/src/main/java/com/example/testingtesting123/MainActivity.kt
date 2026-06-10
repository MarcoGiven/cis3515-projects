package com.example.testingtesting123

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = UniqueCollection()


        val addButton = findViewById<Button>(R.id.button)
        val itemEditText = findViewById<EditText>(R.id.editTextText)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(items)

        addButton.setOnClickListener {
            items.addItem(Item(itemEditText.text.toString()))
            recyclerView.adapter?.run {
                notifyItemChanged(itemCount-1)
            }
            itemEditText.setText("")
        }


    }
}

class ItemAdapter(private val items: UniqueCollection) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val textView: TextView) : ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        TextView(parent.context).apply {
            textSize = 22f
            setPadding(10,8,0,8)
        }
    )

    override fun getItemCount() = items.size()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = items.get(position).name
    }


}


