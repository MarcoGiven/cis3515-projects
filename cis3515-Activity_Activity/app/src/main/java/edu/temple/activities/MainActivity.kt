package edu.temple.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val SIZE_KEY = "someKey"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create array of integers that are multiples of 5
        // Verify correctness by examining array values.
        val textSizes = Array(20){(it + 1) * 5}

        Log.d("Array values", textSizes.contentToString())

        with (findViewById<RecyclerView>(R.id.textSizeSelectorRecyclerView)) {

            // TODO Step 2: Implement lambda body to launch new activity and pass value
            adapter = TextSizeAdapter(textSizes){ textSize ->
                startActivity(
                    Intent(this@MainActivity, DisplayActivity::class.java).putExtra(SIZE_KEY, textSize.toFloat())
                )
            }
            layoutManager = LinearLayoutManager(this@MainActivity)
        }



    }
}


/* Convert to RecyclerView.Adapter */
class TextSizeAdapter (private val textSizes: Array<Int>, private val callback: (Int)->Unit) : RecyclerView.Adapter<TextSizeAdapter.TextSizeViewHolder>() {

    // TODO Step 1: Complete onClickListener to return selected number
    inner class TextSizeViewHolder(val textView: TextView) : RecyclerView.ViewHolder (textView) {
        init {
            textView.setOnClickListener {
                val position = adapterPosition
                callback(textSizes[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextSizeViewHolder {
        return TextSizeViewHolder(TextView(parent.context).apply { setPadding(5, 20, 0, 20) })
    }

    override fun onBindViewHolder(holder: TextSizeViewHolder, position: Int) {
        holder.textView.apply {
            text = textSizes[position].toString()
            textSize = textSizes[position].toFloat()
        }
    }

    override fun getItemCount(): Int {
        return textSizes.size
    }

}








