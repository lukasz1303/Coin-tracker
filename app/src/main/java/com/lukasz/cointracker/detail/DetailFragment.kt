package com.lukasz.cointracker.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lukasz.cointracker.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

        val data = DetailFragmentArgs.fromBundle(requireArguments()).selectedCoin
        val viewModelFactory = DetailViewModelFactory(data, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)

        return binding.root
    }

}