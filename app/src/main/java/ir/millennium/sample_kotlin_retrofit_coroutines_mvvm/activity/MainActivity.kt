package ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.activity

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.R
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.adapter.MovieAdapter
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.model.Movie
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.viewModel.MainViewModel
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.viewModel.MyViewModelFactory
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.viewModel.Resource
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.viewModel.Resource.Status.*
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.utils.GridSpacingItemDecoration
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.webService.MainRepository
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var pbLoading: ProgressBar

    private lateinit var rcvMovieList: RecyclerView
    private var movieListAdapter: MovieAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initProgressBar()
        initRecyclerView()
        getData()

    }

    private fun initProgressBar() {
        pbLoading = findViewById<ProgressBar>(R.id.progressDialog)
    }

    private fun initRecyclerView() {
        rcvMovieList = findViewById<RecyclerView>(R.id.rcvMovies)

        mLayoutManager = GridLayoutManager(this, 2)
        rcvMovieList.layoutManager = mLayoutManager
        rcvMovieList.addItemDecoration(
            GridSpacingItemDecoration(2, dpToPx(this, 6), true)
        )
        rcvMovieList.setHasFixedSize(true)

        movieListAdapter = MovieAdapter(this)
        rcvMovieList.adapter = movieListAdapter

        rcvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mainViewModel.getAllMovies()
                }
            }
        })
    }

    private fun getData() {
        val mainRepository = MainRepository()

        mainViewModel = ViewModelProvider(
            this, MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)

        mainViewModel.movieListViewModel.observe(this,
            Observer<Resource<List<Movie>>> { dataResource ->

                when (dataResource.status) {
                    LOADING -> {
                        pbLoading.visibility = View.VISIBLE
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }
                    SUCCESS -> {
                        Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
                        pbLoading.visibility = View.INVISIBLE
                        var movieList = dataResource.data
                        movieListAdapter!!.setMovies(movieList!!)
                    }
                    ERROR_LOGICAL -> {
                        pbLoading.visibility = View.INVISIBLE
                        Toast.makeText(this, "ERROR_LOGICAL", Toast.LENGTH_SHORT).show()
                    }
                    ERROR -> {
                        pbLoading.visibility = View.INVISIBLE
                        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        mainViewModel.getAllMovies()
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        val r = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            r.displayMetrics
        ).roundToInt()
    }
}