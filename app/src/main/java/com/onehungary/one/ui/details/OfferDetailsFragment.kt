package com.onehungary.one.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onehungary.one.databinding.FragmentOfferDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

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

        if (savedInstanceState == null) {
            viewModel.handle(OfferDetailsViewAction.RetrieveOfferDetails)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}