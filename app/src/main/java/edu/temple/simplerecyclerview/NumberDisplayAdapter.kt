package edu.temple.simplerecyclerview

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Step 3: Complete adapter implementation

class NumberDisplayAdapter : RecyclerView.Adapter<NumberDisplayAdapter.NumberViewHolder>()  {
    class NumberViewHolder (val textView: TextView) : RecyclerView.ViewHolder (textView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NumberViewHolder (
        TextView(parent.context).apply {
            textSize = 22f
        }
    )

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.textView.text = ((position + 1) * 5).toString()
    }

    override fun getItemCount() = 200

}