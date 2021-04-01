package com.lukasz.cointracker.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lukasz.cointracker.CoinAdapter
import com.lukasz.cointracker.CoinListener
import com.lukasz.cointracker.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val manager = GridLayoutManager(activity,1)
        binding.coinList.layoutManager = manager


        binding.coinList.adapter = CoinAdapter(CoinListener {
            viewModel.displayCoinDetails(it)
        })

        viewModel.navigateToSelectedCoin.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(
                        OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayCoinDetailsComplete()
            }
        })

        return binding.root
    }

}