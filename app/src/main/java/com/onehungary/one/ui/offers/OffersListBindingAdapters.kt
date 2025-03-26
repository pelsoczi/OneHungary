package com.onehungary.one.ui.offers

import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.onehungary.one.R

@BindingAdapter("app:swipeRefreshState")
fun setRefreshingState(swipeRefreshLayout: SwipeRefreshLayout, offersListViewState: OffersListViewState?) {
    when (offersListViewState) {
        is OffersListViewState.Empty -> true
        is OffersListViewState.Loading -> true
        is OffersListViewState.NetworkError -> false
        is OffersListViewState.TryAgainLater -> false
        is OffersListViewState.Offers -> false
        is OffersListViewState.Logout -> true
        null -> null
    }?.let { swipeRefreshLayout.isRefreshing = it }
}

@BindingAdapter("app:offerList")
fun bindOfferList(recyclerView: RecyclerView, offersListViewState: OffersListViewState?) {
    val adapter = recyclerView.adapter as OffersAdapter
    when (offersListViewState) {
        is OffersListViewState.Empty -> emptyList()
        is OffersListViewState.Loading -> emptyList()
        is OffersListViewState.NetworkError -> emptyList()
        is OffersListViewState.TryAgainLater -> emptyList()
        is OffersListViewState.Offers -> offersListViewState.entities
        is OffersListViewState.Logout -> null
        else -> null
    }?.let { adapter.submitList(it) }
}

@BindingAdapter("app:shadeVisibility")
fun setShadeVisibility(view: ConstraintLayout, offersListViewState: OffersListViewState?) {
    when (offersListViewState) {
        is OffersListViewState.Empty -> false
        is OffersListViewState.Loading -> false
        is OffersListViewState.NetworkError -> true
        is OffersListViewState.TryAgainLater -> true
        is OffersListViewState.Offers -> false
        is OffersListViewState.Logout -> false
        null -> null
    }?.let { view.isVisible = it }
}

@BindingAdapter("app:displayMessageText")
fun setDisplayMessageText(view: MaterialTextView, offersListViewState: OffersListViewState?) {
    when (offersListViewState) {
        is OffersListViewState.Empty -> ""
        is OffersListViewState.Loading -> ""
        is OffersListViewState.NetworkError -> view.context.getString(R.string.check_internet)
        is OffersListViewState.TryAgainLater -> view.context.getString(R.string.try_again)
        is OffersListViewState.Offers -> ""
        is OffersListViewState.Logout -> ""
        null -> null
    }?.let {
        view.text = it
        view.isVisible = it.isNotEmpty()
    }
}

@BindingAdapter("app:errorRefreshVisibility")
fun setErrorRefreshVisibility(view: MaterialButton, offersListViewState: OffersListViewState?) {
    when (offersListViewState) {
        is OffersListViewState.Empty -> false
        is OffersListViewState.Loading -> false
        is OffersListViewState.NetworkError -> true
        is OffersListViewState.TryAgainLater -> true
        is OffersListViewState.Offers -> false
        is OffersListViewState.Logout -> false
        null -> null
    }?.let { view.isVisible = it }
}

@BindingAdapter("app:titleText")
fun setTitleText(textView: AppCompatTextView, item: OfferListItem.TitleListType?) {
    when (item?.type) {
        OfferListItemTitles.SPECIAL -> R.string.special_offers
        OfferListItemTitles.OFFERS -> R.string.offers
        else -> null
    }?.let {
        textView.text = textView.context.getString(it)
    }
}