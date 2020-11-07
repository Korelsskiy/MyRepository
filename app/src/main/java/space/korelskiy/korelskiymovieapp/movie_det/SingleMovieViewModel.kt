package space.korelskiy.korelskiymovieapp.movie_det

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import space.korelskiy.korelskiymovieapp.data.repository.NetworkState
import space.korelskiy.korelskiymovieapp.data.vo.MovieDetails

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int):ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails>  = movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)

    val networkState: LiveData<NetworkState>  = movieRepository.getMovieDetailsNetworkState()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}