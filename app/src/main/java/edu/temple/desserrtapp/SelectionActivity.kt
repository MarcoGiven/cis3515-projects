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

        val flavors = resources.getStringArray(R.array.flavor_array)
        val items = generateTestData(flavors)


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

    fun generateTestData(flavors: Array<String>): Array<Item> {
        return arrayOf(Item(R.drawable.ccf_original, flavors[0])
            , Item(R.drawable.ccf_freshstrawberry, flavors[1])
            , Item(R.drawable.ccf_chocolatecaramelicious,flavors[2])
            , Item(R.drawable.ccf_pineappleupsidedown,flavors[3])
            , Item(R.drawable.ccf_celebration,flavors[4])
            , Item(R.drawable.ccf_caramelapple,flavors[5])
            , Item(R.drawable.ccf_verycherryghirardellichocolate,flavors[6])
            , Item(R.drawable.ccf_lowlicious,flavors[7])
            , Item(R.drawable.ccf_cinnaboncinnamoncwirl,flavors[8])
            , Item(R.drawable.ccf_godiva,flavors[9])
            , Item(R.drawable.ccf_coconutcreampie,flavors[10])
            , Item(R.drawable.ccf_saltedcaramel,flavors[11]))
    }
}