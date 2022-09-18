package ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.webService

import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.model.Movie

class MainRepository {

    suspend fun getAllMovies(): NetworkState<List<Movie>> {

        val response = RetrofitClient.getApi()!!.getAllMovies()

        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}