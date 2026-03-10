package edu.temple.imageselector

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

data class MovieItem(val title: String, val imageId: Int)

class SelectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieList: List<MovieItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieList = listOf(
            MovieItem(R.string.movie1, R.drawable.anemone_poster),
            MovieItem(R.string.movie2, R.drawable.phantom_poster),
            MovieItem(R.string.movie3, R.drawable.lincoln_poster),
            MovieItem(R.string.movie4, R.drawable.nine_poster),
            MovieItem(R.string.movie5, R.drawable.blood_poster),
            MovieItem(R.string.movie6, R.drawable.jack_rose_poster),
            MovieItem(R.string.movie7, R.drawable.gangs_ny_poster),
            MovieItem(R.string.movie8, R.drawable.boxer_poster),
            MovieItem(R.string.movie9, R.drawable.crucible_poster),
            MovieItem(R.string.movie10, R.drawable.name_father_poster),
            MovieItem(R.string.movie11, R.drawable.age_innocence_poster),
            MovieItem(R.string.movie12, R.drawable.mohicans_poster),
            MovieItem(R.string.movie13, R.drawable.eversmile_poster),
            MovieItem(R.string.movie14, R.drawable.left_foot_poster),
            MovieItem(R.string.movie15, R.drawable.stars_poster),
            MovieItem(R.string.movie16, R.drawable.lightness_poster),
            MovieItem(R.string.movie17, R.drawable.nanou_poster),
            MovieItem(R.string.movie18, R.drawable.screen_two_poster),
            MovieItem(R.string.movie19, R.drawable.room_view_poster),
            MovieItem(R.string.movie20, R.drawable.brother_poster),
        )



    }
}