package com.lukasz.cointracker.searching

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.lukasz.cointracker.CoinAdapter
import com.lukasz.cointracker.CoinListener
import com.lukasz.cointracker.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val manager = GridLayoutManager(activity, 1)
        binding.searchedCoinList.layoutManager = manager

        binding.searchedCoinList.addItemDecoration(
            DividerItemDecoration(
                binding.searchedCoinList.context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.searchedCoinList.adapter = CoinAdapter(CoinListener {
            viewModel.displayCoinDetails(it)
        })
        binding.searchedCoinList.setHasFixedSize(true)

        binding.searchedCoinNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchedName(s.toString())
                updateSearchedCoins()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        viewModel.navigateToSelectedCoin.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
                )
                viewModel.displayCoinDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun updateSearchedCoins() {
        val job = Job()
        CoroutineScope(job + Dispatchers.Main).launch {
            viewModel.refreshSearchedDataFromRepository()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchedCoinNameEditText.requestFocus()
        val inputMethodManager: InputMethodManager =
            this.context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.searchedCoinNameEditText, InputMethodManager.SHOW_IMPLICIT)
    }
}