package com.lukasz.cointracker.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lukasz.cointracker.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by lazy {
        val application = requireNotNull(activity).application
        val coin = DetailFragmentArgs.fromBundle(requireArguments()).selectedCoin
        val viewModelFactory = DetailViewModelFactory(coin, application)
        ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.navigateToCoinGeckoUrl.observe(viewLifecycleOwner, {
            if (null != it) {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(it)
                )
                requireActivity().startActivity(webIntent)
                viewModel.displayInWebBrowserComplete()
            }
        })

        return binding.root
    }

}