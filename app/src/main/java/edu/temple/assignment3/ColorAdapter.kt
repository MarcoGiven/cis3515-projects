package edu.temple.assignment3

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ColorAdapter(private val context: Context, private val colorArray: Array<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return colorArray.size
    }

    override fun getItem(p0: Int): Any? {
        return colorArray[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        val textView = TextView(context)


        textView.text = colorArray[p0]
        
        textView.setPadding(32, 32, 32, 32)
        textView.textSize = 18f

        return textView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val textView = TextView(context)

        textView.text = colorArray[position]
        textView.setPadding(32, 32, 32, 32)
        textView.textSize = 18f

        if(position != 0){
            textView.setBackgroundColor(Color.parseColor(colorArray[position]))
        }


        return textView
    }

}