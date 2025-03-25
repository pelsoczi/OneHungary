package com.onehungary.one.ui.offers

sealed class OffersListViewAction {

    object Refresh : OffersListViewAction()

    object Logout : OffersListViewAction()

}