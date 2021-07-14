package com.amarchaud.amtinderliketester.domain.api

import androidx.recyclerview.widget.DiffUtil

data class ApiProfile(
    var id: Int = 0,
    var first_name: String? = null,
    var last_name: String? = null,
    var city: String? = null,
    var country: String? = null,
    var is_match: Boolean = false,
    var photos: List<String>? = null
) {
    class ApiProfileDiff : DiffUtil.ItemCallback<ApiProfile>() {
        override fun areItemsTheSame(oldItem: ApiProfile, newItem: ApiProfile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ApiProfile, newItem: ApiProfile): Boolean {
            return oldItem == newItem
        }
    }
}

data class ApiProfiles(
    var profiles: List<ApiProfile>
)