package com.example.movieflix.ui.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.movieflix.R
import com.example.movieflix.databinding.FragmentMovieDetailBinding
import com.example.movieflix.utils.Constants
import com.example.movieflix.viewmodels.MyViewModel
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment()
{
    private var _binding: FragmentMovieDetailBinding?=null
    private val binding get() = _binding
    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMovieDetailBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel.popMovieIdData.observe(viewLifecycleOwner){movieId->
            viewModel.detailMoviesApiCall(movieId)
        }
        viewModel.movieData.observe(viewLifecycleOwner){movieDetail->
            val finalPath = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon().appendEncodedPath(movieDetail?.poster_path).build().toString()
            Picasso.get().load(finalPath).placeholder(R.drawable.nullable).error(R.drawable.nullable)
                .into(binding!!.poster1)
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
