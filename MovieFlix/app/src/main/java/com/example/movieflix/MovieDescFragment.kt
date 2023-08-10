package com.example.movieflix

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.databinding.FragmentMovieDescBinding
import com.example.movieflix.models.ApiResponse
import com.example.movieflix.models.DetailMovieApIInterface
import com.example.movieflix.models.DetailMovieApiClient
import com.example.movieflix.models.Movie
import com.example.movieflix.models.PopularMovieAPIInterface
import com.example.movieflix.viewmodels.MyViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response


class MovieDescFragment : Fragment() {

    private var _binding: FragmentMovieDescBinding?=null
    private val binding get() = _binding
    private var popMovieId :Int =0
    private val viewModel: MyViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMovieDescBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments
        if (arguments != null)
        {
            popMovieId = arguments.getInt("ID")
        }
        viewModel.detailMoviesApiCall(popMovieId)

        viewModel.movieData.observe(viewLifecycleOwner){movieDetail->
            val finalPath = Uri.parse("https://image.tmdb.org/t/p/w500").buildUpon().appendEncodedPath(movieDetail?.poster_path).build().toString()
            Picasso.get().load(finalPath)
                .placeholder(R.drawable.nullable).error(R.drawable.nullable).into(binding!!.poster1);
            binding!!.lang1.text=movieDetail?.original_language
            binding!!.rating1.text= movieDetail?.vote_average?.let { convertRatingToStar(it) }
            binding!!.title1.text=movieDetail?.title
            binding!!.release1.text=movieDetail?.release_date
            binding!!.desc.text=movieDetail?.overview
        }


    }
    private fun convertRatingToStar(rating: Double): String
    {
        val maxRating = 10
        val roundedRating = rating.coerceIn(0.0, maxRating.toDouble())
        val fullStars = (roundedRating / 2).toInt()
        val halfStar = (roundedRating % 2) >= 1

        val starBuilder = StringBuilder()

        for (i in 1..fullStars)
        {
            starBuilder.append("★") // Full star
        }
        if (halfStar)
        {
            starBuilder.append("✫") // Half star
        }
        for (i in (fullStars + if (halfStar) 1 else 0) until (maxRating / 2))
        {
            starBuilder.append("☆") // Empty
        }
        return starBuilder.toString()
    }

}