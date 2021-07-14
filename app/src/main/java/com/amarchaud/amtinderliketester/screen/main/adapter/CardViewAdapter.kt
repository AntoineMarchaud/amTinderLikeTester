package com.amarchaud.amtinderliketester.screen.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amarchaud.amtinderliketester.R
import com.amarchaud.amtinderliketester.databinding.ItemCardBinding
import com.amarchaud.amtinderliketester.databinding.ItemPhotoBinding
import com.amarchaud.amtinderliketester.domain.api.ApiProfile
import com.amarchaud.amtinderliketester.domain.app.AppPhoto
import com.amarchaud.amtinderliketester.utils.GlideUtils

class ProfileImageAdapter :
    ListAdapter<AppPhoto, ProfileImageAdapter.ItemPhotoViewHolder>(AppPhoto.AppPhotoDiff()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileImageAdapter.ItemPhotoViewHolder {
        val binding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemPhotoViewHolder(binding)
    }

    inner class ItemPhotoViewHolder(var binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AppPhoto) = with(binding) {
            GlideUtils.createGlide(itemView.context, item.photoUrl, profileImage)
        }
    }

    override fun onBindViewHolder(holder: ItemPhotoViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }
}

class CardViewAdapter :
    ListAdapter<ApiProfile, CardViewAdapter.ItemListingViewHolder>(ApiProfile.ApiProfileDiff()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemListingViewHolder {
        val binding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListingViewHolder(binding)
    }


    inner class ItemListingViewHolder(var binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ApiProfile) = with(binding) {

            val adapter = ProfileImageAdapter()
            rvPhotos.adapter = adapter
            adapter.submitList(item.photos?.map { AppPhoto(it) })

            profileNameAge.text =
                itemView.context.getString(R.string.profile_name_age, item.first_name, 21)
            profileTown.text =
                itemView.context.getString(R.string.profile_town_country, item.city, item.country)

            leftEdge.setOnClickListener {
                val currentIndex =
                    (rvPhotos.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (currentIndex > 0) {
                    val indexToDisplayed = (currentIndex - 1)
                    rvPhotos.smoothScrollToPosition(indexToDisplayed)
                    refreshBar(
                        itemView.context,
                        layoutPosition,
                        indexToDisplayed,
                        item.photos?.size ?: 0
                    )
                }

            }

            rightEdge.setOnClickListener {
                val currentIndex =
                    (rvPhotos.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (currentIndex < ((item.photos?.size ?: 1) - 1)) {
                    val indexToDisplayed = (currentIndex + 1)
                    rvPhotos.smoothScrollToPosition(indexToDisplayed)
                    refreshBar(
                        itemView.context,
                        layoutPosition,
                        indexToDisplayed,
                        item.photos?.size ?: 0
                    )
                }
            }

            itemView.post {
                refreshBar(itemView.context, layoutPosition, 0, item.photos?.size ?: 0)
            }
        }
    }

    private fun refreshBar(
        context: Context,
        layoutPosition: LinearLayoutCompat,
        indexToDisplayed: Int,
        indexMax: Int
    ) {
        layoutPosition.removeAllViews()

        for (i in 0 until indexMax) {
            val v = View(context)

            if (i == indexToDisplayed) {
                v.setBackgroundResource(R.drawable.item_selected)
            } else {
                v.setBackgroundResource(R.drawable.item_unselected)
            }

            val w = (layoutPosition.width / indexMax.toFloat()).toInt() - 8
            if (i != itemCount - 1) {
                val l = LinearLayoutCompat.LayoutParams(
                    LinearLayout.LayoutParams(
                        w,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                )
                l.marginEnd = 8
                v.layoutParams = l
            }

            layoutPosition.addView(v)
        }
    }

    override fun onBindViewHolder(holder: ItemListingViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }
}