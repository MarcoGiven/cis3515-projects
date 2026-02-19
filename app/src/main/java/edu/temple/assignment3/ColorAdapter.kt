package edu.temple.assignment3

import android.content.Context
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
        return textView
    }

}