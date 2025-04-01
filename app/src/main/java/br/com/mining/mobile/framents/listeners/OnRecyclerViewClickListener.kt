package com.unikode.mobile.fragments.listeners

interface OnRecyclerViewClickListener<T> {
    fun onItemSelected(item: T, position: Int)
}