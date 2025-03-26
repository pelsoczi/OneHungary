package com.onehungary.one.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onehungary.one.databinding.FragmentOfferDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfferDetailsFragment : Fragment() {

    private var _binding: FragmentOfferDetailsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<OfferDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfferDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar
        supportActionBar?.let {
            it.show()
            it.setDisplayHomeAsUpEnabled(false)
        }

        binding.apply {
            errorRefresh.setOnClickListener {
                viewModel.handle(OfferDetailsViewAction.RetrieveOfferDetails)
            }
        }

        observeViewModel()

        if (savedInstanceState == null) {
            viewModel.handle(OfferDetailsViewAction.RetrieveOfferDetails)
        }
    }

    fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    setViewState(it)
                }
            }
        }
    }

    private fun setViewState(detailViewState: OfferDetailsViewState) {
        binding.viewState = detailViewState
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}