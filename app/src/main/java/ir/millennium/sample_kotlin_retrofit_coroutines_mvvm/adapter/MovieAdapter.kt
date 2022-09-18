package ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.R
import ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.model.Movie

class MovieAdapter(context: Context) :
    RecyclerView.Adapter<MovieAdapter.CustomViewHolder>() {

    val context = context
    var movieList = mutableListOf<Movie>()

    fun setMovies(movies: List<Movie>) {
        this.movieList.addAll(movies.toMutableList())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, null)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val movie = movieList[position]
        if (validateMovie(movie)) {
            holder.lblName.text = movie.name
            Glide.with(context).load(movie.imageUrl).into(holder.imvImage)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun validateMovie(movie: Movie): Boolean {
        if (movie.name!!.isNotEmpty() && movie.category!!.isNotEmpty()) {
            return true
        }
        return false
    }


    inner class CustomViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val lblName: TextView = view.findViewById<View>(R.id.lblName) as TextView
        val imvImage: ImageView = view.findViewById<View>(R.id.imvImage) as ImageView
    }
}