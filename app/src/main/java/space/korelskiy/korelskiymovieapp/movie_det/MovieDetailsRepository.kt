package space.korelskiy.korelskiymovieapp.movie_det

import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable
import space.korelskiy.korelskiymovieapp.data.api.TheMovieDbInterface
import space.korelskiy.korelskiymovieapp.data.repository.MovieDetailsNetworkDataSource
import space.korelskiy.korelskiymovieapp.data.repository.NetworkState
import space.korelskiy.korelskiymovieapp.data.vo.MovieDetails

class MovieDetailsRepository(private val apiService: TheMovieDbInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetckMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
    }

    fun getMovieDetailsNetworkState() : LiveData<NetworkState>{
        return  movieDetailsNetworkDataSource.networkState
    }
}