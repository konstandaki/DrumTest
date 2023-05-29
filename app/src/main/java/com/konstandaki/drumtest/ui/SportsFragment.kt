package com.konstandaki.drumtest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstandaki.drumtest.R
import com.konstandaki.drumtest.SportsApplication
import com.konstandaki.drumtest.databinding.FragmentSportsBinding
import com.konstandaki.drumtest.ui.adapter.SportsAdapter

class SportsFragment : Fragment() {

    private val viewModel: SportsViewModel by activityViewModels {
        SportsViewModelFactory((activity?.application as SportsApplication).container.sportsRepository)
    }

    private var _binding: FragmentSportsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSportsBinding.inflate(inflater, container, false)

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SportsAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.sports.observe(this.viewLifecycleOwner) { sports ->
            sports.let {
                adapter.submitList(it.distinct())
            }
        }
    }

    private fun onNetworkError() {
        Toast.makeText(activity, getString(R.string.network_error), Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}