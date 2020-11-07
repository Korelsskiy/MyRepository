package space.korelskiy.korelskiymovieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import space.korelskiy.korelskiymovieapp.data.api.TheMovieDbInterface
import space.korelskiy.korelskiymovieapp.data.vo.MovieDetails
import java.lang.Exception

class MovieDetailsNetworkDataSource (private  val apiService: TheMovieDbInterface, private  val compositeDisposible: CompositeDisposable) {
    private  val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
         get() = _networkState

    private  val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieDetailsResponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetckMovieDetails(movieId: Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposible.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsSource", it.message)
                        }
                    )
            )
        }
        catch (e: Exception){
            Log.e("MovieDetailsSource", e.message)
        }
    }
}