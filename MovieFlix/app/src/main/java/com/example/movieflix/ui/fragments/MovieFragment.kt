package com.example.movieflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.R
import com.example.movieflix.adapters.MovieListAdapter
import com.example.movieflix.databinding.FragmentMovieBinding
import com.example.movieflix.models.Movie
import com.example.movieflix.viewmodels.MyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MovieFragment : Fragment()
{
    private var _binding: FragmentMovieBinding?=null
    private val binding get() = _binding
    private val viewModel: MyViewModel by activityViewModels()

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(
            this::onMovieSelected,
            this::dialogueOnMovieSelectedDelete
        )
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        viewModel.popularMoviesApiCall()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMovieBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movieDataList.observe(viewLifecycleOwner){popMovies->
            binding!!.movieRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding!!.movieRecyclerView.adapter = adapter
            adapter.setList(popMovies.toMutableList())
        }
       exitAppDialogue()
    }

    private fun exitAppDialogue()
    {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner)
        {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.fragment1_dialogue_text))
                .setNegativeButton(resources.getString(R.string.fragment1_dialogue_reject)) { _, _ ->
                }
                .setPositiveButton(resources.getString(R.string.fragment1_dialogue_accept)) { _, _ ->
                    requireActivity().finishAndRemoveTask() // Finish the activity and exit the app
                }
                .show()
        }
    }

    private fun onMovieSelected(movie: Movie)
    {
        viewModel.setPopMovieId(movie.id)
        findNavController().navigate(R.id.action_movieFragment_to_movieDescFragment)
        Toast.makeText(requireContext(), "Opening Movie !! ", Toast.LENGTH_SHORT).show()
    }

    private fun dialogueOnMovieSelectedDelete(movies: List<Movie>)
    {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment1_dialogue))
            .setNegativeButton(resources.getString(R.string.act1_dialogue_decline)) { _, _ ->
            }
            .setPositiveButton(resources.getString(R.string.act1_dialogue_accept)) { _, _ ->
                viewModel.setDataList(movies)
                    Toast.makeText(requireContext(), "Movie Deleting !! ", Toast.LENGTH_SHORT).show()
            }
            .show()
    }
}


