package space.korelskiy.korelskiymovieapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "930ac59a769b9b3028057ad4e5000811"
const val BASE_URL = "https://www.themoviedb.org/3/"

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/eTMMU2rKpZRbo9ERytyhwatwAjz.jpg"
object TheMovieDBClient {
    fun getClient(): TheMovieDbInterface{
        val requestInterseptor = Interceptor{chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterseptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDbInterface::class.java)

    }
}