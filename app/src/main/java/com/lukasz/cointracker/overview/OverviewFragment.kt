package com.lukasz.cointracker.overview

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lukasz.cointracker.CoinAdapter
import com.lukasz.cointracker.CoinListener
import com.lukasz.cointracker.R
import com.lukasz.cointracker.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    private lateinit var binding: FragmentOverviewBinding


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.market_cap_sort) {
            viewModel.setOrder(1)
        }
        if (item.itemId == R.id.change_24h_sort){
            viewModel.setOrder(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val manager = GridLayoutManager(activity, 1)
        binding.coinList.layoutManager = manager


        binding.coinList.adapter = CoinAdapter(CoinListener {
            viewModel.displayCoinDetails(it)
        })

        viewModel.navigateToSelectedCoin.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it)
                )
                viewModel.displayCoinDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

}