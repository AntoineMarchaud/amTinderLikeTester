package com.amarchaud.amtinderliketester.screen.main

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.amarchaud.amtinderliketester.R
import com.amarchaud.amtinderliketester.databinding.ActivityMainBinding
import com.amarchaud.amtinderliketester.screen.main.adapter.CardViewAdapter
import com.amarchaud.amtinderliketester.utils.*
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()
    private val animationLeft: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.bounce
        )
    }
    private val animationRight: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.bounce
        )
    }
    private val cardAdapter = CardViewAdapter()

    private val swipeLeft: SwipeAnimationSetting by lazy {
        SwipeAnimationSetting.Builder()
            .setDirection(Direction.Left)
            .build()
    }

    private val swipeRight: SwipeAnimationSetting by lazy {
        SwipeAnimationSetting.Builder()
            .setDirection(Direction.Right)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setFullScreen()

        binding.run {

            root.setTopBottomInsets { topInsets, bottomInsets ->
                topLayout.setTopPadding(topInsets)
                cardView.setTopPadding(
                    (convertDpToPixel(
                        100f,
                        applicationContext
                    ) + topInsets).toInt()
                )

                addButton.setBottomMargin(
                    (convertDpToPixel(
                        20f,
                        applicationContext
                    ) + bottomInsets).toInt()
                )
                deleteButton.setBottomMargin(
                    (convertDpToPixel(
                        20f,
                        applicationContext
                    ) + bottomInsets).toInt()
                )
            }

            cardView.layoutManager = CardStackLayoutManager(this@MainActivity, cardListener).apply {
                setStackFrom(StackFrom.Bottom)
            }
            cardView.adapter = cardAdapter


            deleteButton.setOnClickListener {
                it.startAnimation(animationLeft)
                (cardView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(
                    swipeLeft
                )
                cardView.swipe()
            }

            addButton.setOnClickListener {
                it.startAnimation(animationRight)
                (cardView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(
                    swipeRight
                )
                cardView.swipe()
            }
        }

        viewModel.numberToDisplay.observe(this, ::handleNb)
        viewModel.apiProfiles.observe(this, ::handleState)
        viewModel.fetchProfiles()
    }

    private val cardListener = object : CardStackListener {
        override fun onCardDragging(direction: Direction?, ratio: Float) {

        }

        override fun onCardSwiped(direction: Direction?) {
            if (direction == Direction.Right) {
                val item =
                    cardAdapter.currentList[(binding.cardView.layoutManager as CardStackLayoutManager).topPosition - 1]
                viewModel.manageItemAccepted(item)
            }
        }

        override fun onCardRewound() {

        }

        override fun onCardCanceled() {

        }

        override fun onCardAppeared(view: View?, position: Int) {

        }

        override fun onCardDisappeared(view: View?, position: Int) {

        }
    }

    private fun handleNb(nb: Int?) {
        nb?.let {
            binding.topRightNumber.text = it.toString()
        }
    }


    private fun handleState(status: Status?) {
        status?.let {
            when (status) {
                is Status.StatusOk -> {
                    cardAdapter.submitList(status.profiles?.profiles)
                }
                is Status.StatusError -> {
                    AlertDialog.Builder(this)
                        .setTitle("Error !")
                        .setMessage(status.message?.string())
                        .setPositiveButton(
                            android.R.string.ok
                        ) { _, _ ->
                            viewModel.fetchProfiles() // retry
                        }
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
            }
        }
    }
}