package edu.temple.desserrtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectionActivity : AppCompatActivity() {

    /**
     * Companion objects are used in Kotlin
     * as containers of public static fields
     */
    companion object {
        val ITEM_KEY = "key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the title for the activity.
        // This is done to ensure that the activity label
        // displayed in the Android launcher is different
        supportActionBar?.title = "Selector"

        val items = generateTestData()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)


        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val clickEvent = {item: Item ->
            // Item object can be placed directly inside Intent because
            // the Item class implements the Parcelable interface
            val launchIntent = Intent(this, DisplayActivity::class.java)
                .putExtra(ITEM_KEY, item)

            startActivity(launchIntent)
        }

        recyclerView.adapter = ImageAdapter(items, clickEvent)
    }

    /**
     * Generate test info for app
     */
    fun generateTestData(): Array<Item> {
        return arrayOf(Item(R.drawable.ccf_original, "Original")
            , Item(R.drawable.ccf_freshstrawberry,"Fresh Strawberry")
            , Item(R.drawable.ccf_chocolatecaramelicious,"Chocolate Caramelicious Cheesecake ")
            , Item(R.drawable.ccf_pineappleupsidedown,"Pineapple Upside-Down")
            , Item(R.drawable.ccf_celebration,"Celebration")
            , Item(R.drawable.ccf_caramelapple,"Caramel Apple")
            , Item(R.drawable.ccf_verycherryghirardellichocolate,"Very Cherry Ghirardelli® Chocolate")
            , Item(R.drawable.ccf_lowlicious,"Low-Licious")
            , Item(R.drawable.ccf_cinnaboncinnamoncwirl,"Cinnabon® Cinnamon Swirl")
            , Item(R.drawable.ccf_godiva,"Godiva® Chocolate")
            , Item(R.drawable.ccf_coconutcreampie,"Coconut Cream Pie")
            , Item(R.drawable.ccf_saltedcaramel,"Salted Caramel"))
    }
}