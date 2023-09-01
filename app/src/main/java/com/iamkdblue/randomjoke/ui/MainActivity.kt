package com.iamkdblue.randomjoke.ui

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.fitway.anime.activity.api.ApiService
import com.fitway.anime.activity.repository.Repository
import com.iamkdblue.randomjoke.R
import com.iamkdblue.randomjoke.adapter.CardStackAdapter
import com.iamkdblue.randomjoke.api.NetworkConnectionInterceptor
import com.iamkdblue.randomjoke.util.SpotDiffCallback
import com.iamkdblue.randomjoke.util.snackbar
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CardStackListener {


    private val cardStackView by lazy { findViewById<CardStackView>(R.id.cardStackView) }
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private lateinit var adapter: CardStackAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupCardStackView()
        setupObserver()
        setupButton()
    }


    private fun setupButton() {
        fabRewind.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            cardStackView.rewind()
        }
    }

    private fun setupObserver() {
        mainViewModel.jokesLiveData.observe(this, Observer { jokesList ->
            adapter.setJokes(jokesList)
            cardStackView.adapter = adapter
        })

        mainViewModel.lodeMoreJokesList.observe(this, Observer { newJokesList ->
            val old = adapter.getJokes()
            val new = old.plus(newJokesList)
            val callback = SpotDiffCallback(old, new)
            val result = DiffUtil.calculateDiff(callback)
            adapter.setJokes(new)
            result.dispatchUpdatesTo(adapter)
        })

        mainViewModel.status.observe(this, Observer { status ->
            //Toast.makeText(applicationContext, status, Toast.LENGTH_SHORT).show()
            rootLayout.snackbar(status)
        })
    }

    private fun setupViewModel() {
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val apiService = ApiService(networkConnectionInterceptor)
        val repository = Repository()
        val videoViewModelFactory = MainViewModelFactory(apiService, repository)

        mainViewModel =
            ViewModelProvider(
                this, videoViewModelFactory
            ).get(MainViewModel::class.java)

    }

    private fun setupCardStackView() {

        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        adapter = CardStackAdapter()
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }


    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction) {
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    private fun paginate() {
        mainViewModel.loadMoreJokes()
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardRewound() {
    }
}