package com.onehungary.one.ui.details

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.onehungary.one.R

@BindingAdapter("app:offerDetailsTitle")
fun setOfferDetailsTitle(textView: AppCompatTextView, viewState: OfferDetailsViewState?) {
    when (viewState) {
        is OfferDetailsViewState.Empty -> ""
        is OfferDetailsViewState.OfferDetail -> viewState.entity.name
        is OfferDetailsViewState.TryAgainLater -> ""
        is OfferDetailsViewState.NetworkError -> ""
        null -> null
    }?.let { textView.text = it }
}

@BindingAdapter("app:offerDetailsShortDescription")
fun setOfferDetailsShortDescription(textView: AppCompatTextView, viewState: OfferDetailsViewState?) {
    when (viewState) {
        is OfferDetailsViewState.Empty -> ""
        is OfferDetailsViewState.OfferDetail -> viewState.entity.shortDescription
        is OfferDetailsViewState.TryAgainLater -> ""
        is OfferDetailsViewState.NetworkError -> ""
        null -> null
    }?.let { textView.text = it }
}

@BindingAdapter("app:offerDetailsDescription")
fun setOfferDetailsDescription(textView: AppCompatTextView, viewState: OfferDetailsViewState?) {
    when (viewState) {
        is OfferDetailsViewState.Empty -> ""
        is OfferDetailsViewState.OfferDetail -> viewState.entity.description
        is OfferDetailsViewState.TryAgainLater -> ""
        is OfferDetailsViewState.NetworkError -> ""
        null -> null
    }?.let { textView.text = it }
}

@BindingAdapter("app:offerDetailsSpecialImage")
fun setOfferDetailsSpecialImage(imageView: AppCompatImageView, viewState: OfferDetailsViewState?) {
    when (viewState) {
        is OfferDetailsViewState.Empty -> null
        is OfferDetailsViewState.OfferDetail -> viewState.isSpecial
        is OfferDetailsViewState.TryAgainLater -> null
        is OfferDetailsViewState.NetworkError -> null
        null -> null
    }?.let {
        imageView.isVisible = it
        if (it) {
            Glide.with(imageView.context)
                .load(imageView.context.getString(R.string.spcial_image_url))
                .fitCenter()
                .into(imageView)
        }
    }
}

@BindingAdapter("app:offerDetailsShadeVisibility")
fun setOfferDetailsShadeVisibility(view: ConstraintLayout, viewState: OfferDetailsViewState?) {
    when (viewState) {
        is OfferDetailsViewState.Empty -> ""
        is OfferDetailsViewState.OfferDetail -> ""
        is OfferDetailsViewState.TryAgainLater -> view.context.getString(R.string.try_again)
        is OfferDetailsViewState.NetworkError -> view.context.getString(R.string.check_internet)
        null -> ""
    }.let {
        view.isVisible = it.isNotEmpty()
    }
}

@BindingAdapter("app:offerDetailsDisplayMessage")
fun setOfferDetailsDisplayMessage(textView: MaterialTextView, viewState: OfferDetailsViewState?) {
    when (viewState) {
        is OfferDetailsViewState.Empty -> ""
        is OfferDetailsViewState.OfferDetail -> ""
        is OfferDetailsViewState.TryAgainLater -> textView.context.getString(R.string.try_again)
        is OfferDetailsViewState.NetworkError -> textView.context.getString(R.string.check_internet)
        null -> ""
    }.let {
        textView.isVisible = it.isNotEmpty()
        textView.text = it
    }
}

@BindingAdapter("app:offerDetailsErrorRefreshVisibility")
fun setOfferDetailsErrorRefreshVisibility(view: MaterialButton, viewState: OfferDetailsViewState?) {
    when (viewState) {
        is OfferDetailsViewState.Empty -> ""
        is OfferDetailsViewState.OfferDetail -> ""
        is OfferDetailsViewState.TryAgainLater -> view.context.getString(R.string.try_again)
        is OfferDetailsViewState.NetworkError -> view.context.getString(R.string.check_internet)
        null -> ""
    }?.let {
        view.isVisible = it.isNotEmpty()
    }
}