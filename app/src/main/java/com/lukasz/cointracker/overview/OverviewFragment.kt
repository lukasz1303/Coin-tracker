package com.lukasz.cointracker.overview

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.lukasz.cointracker.CoinAdapter
import com.lukasz.cointracker.CoinListener
import com.lukasz.cointracker.R
import com.lukasz.cointracker.databinding.FragmentOverviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    private lateinit var binding: FragmentOverviewBinding


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.market_cap_sort) {
            viewModel.setOrder(1)
        }
        if (item.itemId == R.id.change_24h_sort){
            viewModel.setOrder(0)
        }
        if (item.itemId == R.id.top_100_menu) {
            viewModel.setTop(100)
            updateCoins()
        }
        if (item.itemId == R.id.top_200_menu){
            viewModel.setTop(200)
            updateCoins()
        }
        if (item.itemId == R.id.top_300_menu) {
            viewModel.setTop(300)
            updateCoins()
        }
        if (item.itemId == R.id.top_400_menu){
            viewModel.setTop(400)
            updateCoins()
        }
        if (item.itemId == R.id.top_500_menu) {
            viewModel.setTop(500)
            updateCoins()
        }
        if (item.itemId == R.id.menu_refresh) {
            binding.swipeRefresh.isRefreshing = true
            updateCoins()
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

        binding.coinList.addItemDecoration(
            DividerItemDecoration(
                binding.coinList.context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.coinList.adapter = CoinAdapter(CoinListener {
            viewModel.displayCoinDetails(it)
        })

        binding.swipeRefresh.setOnRefreshListener {
            updateCoins()
        }



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

    private fun updateCoins(){
        val job = Job()
        CoroutineScope(job + Dispatchers.Main).launch {
            val result = viewModel.singleRefreshDataFromRepository()
            if (result == 2) {
                Toast.makeText(context,
                    R.string.refresh_failed_toast_message,
                    Toast.LENGTH_SHORT).show()
            }
        }
        binding.swipeRefresh.isRefreshing = false
    }
}