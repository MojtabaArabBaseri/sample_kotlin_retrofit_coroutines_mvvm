package ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.webService

import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET("movielist.json")
    suspend fun getAllMovies(): Response<List<Movie>>

}