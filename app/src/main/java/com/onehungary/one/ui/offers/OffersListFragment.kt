package com.onehungary.one.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onehungary.one.databinding.FragmentOffersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OffersListFragment : Fragment() {

    private var _binding: FragmentOffersBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<OffersListViewModel>()

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
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                viewModel.handle(OffersListViewAction.Refresh)
            }
        }
        if (savedInstanceState == null) {
            viewModel.handle(OffersListViewAction.Refresh)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}