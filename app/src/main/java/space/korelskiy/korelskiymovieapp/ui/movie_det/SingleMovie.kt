package space.korelskiy.korelskiymovieapp.ui.movie_det

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_single_movie.*
import space.korelskiy.korelskiymovieapp.R
import space.korelskiy.korelskiymovieapp.data.api.POSTER_BASE_URL
import space.korelskiy.korelskiymovieapp.data.api.TheMovieDBClient
import space.korelskiy.korelskiymovieapp.data.api.TheMovieDbInterface
import space.korelskiy.korelskiymovieapp.data.repository.NetworkState
import space.korelskiy.korelskiymovieapp.data.vo.MovieDetails
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private  lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val  movieId: Int = intent.getIntExtra("id", 1)

        val apiService: TheMovieDbInterface = TheMovieDBClient.getClient()
        movieRepository =
            MovieDetailsRepository(
                apiService
            )

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUi(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    fun bindUi(it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_runtime.text = it.runtime.toString() + "minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHEACKED CAST")
                return SingleMovieViewModel(
                    movieRepository,
                    movieId
                ) as T
            }
        }) [SingleMovieViewModel::class.java]
    }
}