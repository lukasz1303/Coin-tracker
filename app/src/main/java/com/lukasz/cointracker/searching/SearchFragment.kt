package com.lukasz.cointracker.searching

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.lukasz.cointracker.CoinAdapter
import com.lukasz.cointracker.CoinListener
import com.lukasz.cointracker.databinding.FragmentSearchBinding

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

        binding.searchedCoinNameEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchedName(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        setHasOptionsMenu(true)
        return binding.root
    }

}