package space.korelskiy.korelskiymovieapp.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import space.korelskiy.korelskiymovieapp.data.vo.MovieDetails

interface TheMovieDbInterface {

    //https://api.themoviedb.org/3/movie/635302?api_key=930ac59a769b9b3028057ad4e5000811

    @GET("movie/{movie_id}")
            fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

}