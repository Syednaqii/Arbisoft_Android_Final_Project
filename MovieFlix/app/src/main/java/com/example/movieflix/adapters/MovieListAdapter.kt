package com.example.movieflix.adapters


import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieflix.utils.Constants
import com.example.movieflix.R
import com.example.movieflix.models.Movie
import com.example.movieflix.databinding.LayoutRecyclerViewBinding
import com.squareup.picasso.Picasso


class MovieListAdapter(
    val onMovieSelected: (Movie) -> Unit,
    val dialogueOnMovieSelectedDelete: (List<Movie>) -> Unit
) : ListAdapter<Movie, MovieListAdapter.ViewHolder>(MovieDiffCallBack()) {

    private var dataList: MutableList<Movie> = mutableListOf()
    private var listSize = ObservableInt()

    fun setList(list: MutableList<Movie>) {
        dataList.clear()
        dataList.addAll(list)
        listSize.set(list.size)
        notifyItemRangeChanged(0, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            LayoutRecyclerViewBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    inner class ViewHolder(private var binding: LayoutRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            val finalPath =
                Uri.parse(Constants.BASE_IMAGE_URL).buildUpon().appendEncodedPath(movie.poster_path)
                    .build().toString()
            Picasso.get().load(finalPath)
                .placeholder(R.drawable.nullable).error(R.drawable.nullable).into(binding!!.poster);
            binding!!.rating.text = convertRatingToStar(movie.vote_average)
            binding!!.title.text = movie.title
            binding!!.release.text = movie.release_date
            binding!!.lang.text = movie.original_language

            binding!!.poster.setOnClickListener {
                onMovieSelected.invoke(movie)
            }
            binding!!.poster.setOnLongClickListener {
                dataList.remove(movie)
                dialogueOnMovieSelectedDelete.invoke(dataList)
                true
            }
        }

        private fun convertRatingToStar(rating: Double): String {
            val maxRating = 10
            val roundedRating = rating.coerceIn(0.0, maxRating.toDouble())
            val fullStars = (roundedRating / 2).toInt()
            val halfStar = (roundedRating % 2) >= 1

            val starBuilder = StringBuilder()

            for (i in 1..fullStars) {
                starBuilder.append("★") // Full star
            }
            if (halfStar) {
                starBuilder.append("✫") // Half star
            }
            for (i in (fullStars + if (halfStar) 1 else 0) until (maxRating / 2)) {
                starBuilder.append("☆") // Empty star
            }
            return starBuilder.toString()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
