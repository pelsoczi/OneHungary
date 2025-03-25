package com.onehungary.one.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.onehungary.one.R
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
        when (detailViewState) {
            is OfferDetailsViewState.Empty -> ""
            is OfferDetailsViewState.OfferDetail -> detailViewState.entity.name
            is OfferDetailsViewState.TryAgainLater -> ""
            is OfferDetailsViewState.NetworkError -> ""
        }.let { binding.detailsTitle.text = it }

        when (detailViewState) {
            is OfferDetailsViewState.Empty -> ""
            is OfferDetailsViewState.OfferDetail -> detailViewState.entity.shortDescription
            is OfferDetailsViewState.TryAgainLater -> ""
            is OfferDetailsViewState.NetworkError -> ""
        }.let { binding.detailsShortDescription.text = it }

        when (detailViewState) {
            is OfferDetailsViewState.Empty -> ""
            is OfferDetailsViewState.OfferDetail -> detailViewState.entity.description
            is OfferDetailsViewState.TryAgainLater -> ""
            is OfferDetailsViewState.NetworkError -> ""
        }.let { binding.detailsDescription.text = it }

        when (detailViewState) {
            is OfferDetailsViewState.Empty -> null
            is OfferDetailsViewState.OfferDetail -> detailViewState.isSpecial
            is OfferDetailsViewState.TryAgainLater -> null
            is OfferDetailsViewState.NetworkError -> null
        }.let {
            binding.detailsSpecialImage.isVisible = it != null && it
            it?.let { special ->
                if (special) Glide.with(requireView())
                    .load(requireContext().getString(R.string.spcial_image_url))
                    .fitCenter()
                    .into(binding.detailsSpecialImage)
            }
        }

        when (detailViewState) {
            is OfferDetailsViewState.Empty -> ""
            is OfferDetailsViewState.OfferDetail -> ""
            is OfferDetailsViewState.TryAgainLater -> getString(R.string.try_again)
            is OfferDetailsViewState.NetworkError -> getString(R.string.check_internet)
        }.let {
            binding.shade.isVisible = it.isNotEmpty()
            binding.displayMessage.isVisible = it.isNotEmpty()
            binding.displayMessage.text = it
            binding.errorRefresh.isVisible = it.isNotEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}