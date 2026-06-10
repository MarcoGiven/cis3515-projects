package edu.temple.dicethrow

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


/*
Our DieThrow application has been refactored to move the dieRoll() logic
into the ViewModel instead of the Fragment.
Study the DieViewModel, ButtonFragment, and DieFragment classes to
see the changes.

Follow the requirements below to have this app function
in both portrait and landscape configurations.
The Activity layout files for both Portrait and Landscape are already provided
*/

class MainActivity : AppCompatActivity(), ButtonFragment.ButtonInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* TODO 1: Load fragment(s)
            - Show _only_ ButtonFragment if portrait
            - show _both_ fragments if Landscape
          */


        if(findViewById<View>(R.id.container2) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, ButtonFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, ButtonFragment())
                .replace(R.id.container2, DieFragment())
                .commit()
        }

    }

    /* TODO 2: switch fragments if die rolled and in portrait (no need to switch fragments if Landscape)
        */

    // This callback function gets invoked when the child Fragment invokes it
    // Remember to place Fragment transactions on BackStack so then can be reversed
    override fun buttonClicked() {
        if(findViewById<View>(R.id.container2) == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, DieFragment())
                .addToBackStack(null)
                .commit()
        }

    }


}