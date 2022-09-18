package ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.model.Movie
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.webService.MainRepository
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.webService.NetworkState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    var job: Job? = null

    val movieListViewModel = MutableLiveData<Resource<List<Movie>>>()

    fun getAllMovies() {
        movieListViewModel.value = Resource.loading()

        job = viewModelScope.launch {
            try {
                when (val response = mainRepository.getAllMovies()) {

                    is NetworkState.Success -> {
                        movieListViewModel.postValue(Resource.success(response.data))
                    }

                    is NetworkState.Error -> {
                        movieListViewModel.value =
                            Resource.errorLogical(response.response.code(), null)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                movieListViewModel.value = Resource.error(e, null)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}