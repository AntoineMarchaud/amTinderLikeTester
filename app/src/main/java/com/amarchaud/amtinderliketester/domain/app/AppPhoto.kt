package com.amarchaud.amtinderliketester.domain.app

import androidx.recyclerview.widget.DiffUtil

data class AppPhoto(
    val photoUrl: String
) {
    class AppPhotoDiff : DiffUtil.ItemCallback<AppPhoto>() {
        override fun areItemsTheSame(oldItem: AppPhoto, newItem: AppPhoto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AppPhoto, newItem: AppPhoto): Boolean {
            return oldItem == newItem
        }
    }
}