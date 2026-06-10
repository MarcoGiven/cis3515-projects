package edu.temple.inclassuiacvitivity

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TextSizeAdapter(_context: Context, _sizes: Array<Int>) : BaseAdapter() {

    private val context = _context
    private val sizes = _sizes

    override fun getCount(): Int {
        return sizes.size
    }


    override fun getItem(position: Int): Any {
        return sizes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = TextView(context)
        val size = sizes[position]

        textView.text = size.toString()
        textView.textSize = 22f
        textView.setPadding(20, 20, 20, 20)

        return textView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        // same as abv
        val textView = TextView(context)
        val size = sizes[position]

        textView.text = size.toString()
        // use size number not 22f
        textView.textSize = size.toFloat()
        textView.setPadding(20, 20, 20, 20)

        return textView
    }

}