package com.onehungary.one.ui.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.onehungary.one.R
import com.onehungary.one.databinding.ItemOfferBinding
import com.onehungary.one.databinding.ItemTitleBinding
import com.onehungary.one.ui.offers.OfferListItem.*

class OffersAdapter(
    private val onOfferClick: (OfferListItem.OfferItem) -> Unit
) : ListAdapter<OfferListItem, OfferListItemViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<OfferListItem>() {

        override fun areItemsTheSame(old: OfferListItem, new: OfferListItem): Boolean {
            return when {
                old is TitleListType && new is TitleListType -> old.type == new.type
                old is OfferItem && new is OfferItem -> old.entity.id == new.entity.id
                else -> false
            }
        }

        override fun areContentsTheSame(old: OfferListItem, new: OfferListItem): Boolean {
            return old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferListItemViewHolder {
        return when (viewType) {
            R.layout.item_title -> {
                val binding = ItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                OfferListItemViewHolder.TitleViewHolder(binding)
            }
            R.layout.item_offer -> {
                val binding = ItemOfferBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                OfferListItemViewHolder.OfferItemViewHolder(binding, onOfferClick)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: OfferListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is TitleListType -> R.layout.item_title
            is OfferItem -> R.layout.item_offer
        }
    }

}