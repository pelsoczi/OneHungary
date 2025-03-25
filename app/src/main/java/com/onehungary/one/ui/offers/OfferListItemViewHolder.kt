package com.onehungary.one.ui.offers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.onehungary.one.R
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
            when (item.type) {
                OfferListItemTitles.SPECIAL -> R.string.special_offers
                OfferListItemTitles.OFFERS -> R.string.offers
            }.let {
                binding.titleItemTitle.text = itemView.context.getString(it)
            }
        }
    }

    class OfferItemViewHolder(
        private val binding: ItemOfferBinding,
        private val onOfferClick: (OfferListItem.OfferItem) -> Unit
    ) : OfferListItemViewHolder(binding.root) {

        override fun bind(item: OfferListItem) {
            item as OfferListItem.OfferItem
            binding.offerItemTitle.text = item.entity.name
            binding.offerItemDescription.text = item.entity.shortDescription
            itemView.setOnClickListener {
                onOfferClick.invoke(item)
            }
        }
    }
}
