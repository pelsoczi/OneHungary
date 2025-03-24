package com.onehungary.one.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.onehungary.one.R
import com.onehungary.one.databinding.FragmentOffersBinding
import com.onehungary.one.ui.offers.OffersListViewState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OffersListFragment : Fragment() {

    private var _binding: FragmentOffersBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<OffersListViewModel>()

    private val onOfferClick: (OfferListItem.OfferItem) -> Unit = {
        // find nav controller
    }

    private val adapter by lazy { OffersAdapter(onOfferClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOffersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.offersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.offersRecyclerView.adapter = adapter
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                viewModel.handle(OffersListViewAction.Refresh)
            }
            errorRefresh.setOnClickListener {
                viewModel.handle(OffersListViewAction.Refresh)
            }
        }

        observeViewModel()

        if (savedInstanceState == null) {
            viewModel.handle(OffersListViewAction.Refresh)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    setViewState(it)
                }
            }
        }
    }

    private fun setViewState(offerViewState: OffersListViewState) {
        when (offerViewState) {
            is Empty -> null
            is Loading -> null
            is NetworkError -> null
            is TryAgainLater -> null
            is Offers -> offerViewState.entities
        }?.let { adapter.submitList(it) }

        when (offerViewState) {
            is Empty -> true
            is Loading -> true
            is NetworkError -> false
            is TryAgainLater -> false
            is Offers -> false
        }.let { binding.swipeRefresh.isRefreshing = it }

        when (offerViewState) {
            is Empty -> ""
            is Loading -> ""
            is NetworkError -> getString(R.string.check_internet)
            is TryAgainLater -> getString(R.string.try_again)
            is Offers -> ""
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