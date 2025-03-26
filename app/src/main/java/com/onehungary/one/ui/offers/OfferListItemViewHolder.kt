package com.onehungary.one.ui.offers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.onehungary.one.databinding.ItemOfferBinding
import com.onehungary.one.databinding.ItemTitleBinding

sealed class OfferListItemViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: OfferListItem)

    class TitleViewHolder(
        private val binding: ItemTitleBinding
    ) : OfferListItemViewHolder(binding.root) {

        override fun bind(item: OfferListItem) {
            item as OfferListItem.TitleListType
            binding.title = item
            binding.executePendingBindings()
        }
    }

    class OfferItemViewHolder(
        private val binding: ItemOfferBinding,
        private val onOfferClick: (OfferListItem.OfferItem) -> Unit
    ) : OfferListItemViewHolder(binding.root) {

        override fun bind(item: OfferListItem) {
            item as OfferListItem.OfferItem
            binding.offer = item
            binding.executePendingBindings()
            itemView.setOnClickListener {
                onOfferClick.invoke(item)
            }
        }
    }
}
