package com.sandeepshabd.podcaster.views

import android.content.ComponentName
import android.os.Bundle
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.STATE_PAUSED
import android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.sandeepshabd.podcaster.R
import com.sandeepshabd.podcaster.adapter.PodcastViewAdapter
import com.sandeepshabd.podcaster.adapter.PodcastViewHolder
import com.sandeepshabd.podcaster.handler.ConstantHandler
import com.sandeepshabd.podcaster.handler.ConstantHandler.Companion.PAUSE_BUTTON
import com.sandeepshabd.podcaster.handler.ConstantHandler.Companion.PLAY_BUTTON
import com.sandeepshabd.podcaster.models.RSSItem
import com.sandeepshabd.podcaster.services.PodcastMediaService
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.info


class DisplayDataActivity : AppCompatActivity(), AnkoLogger, IDisplayView {

    var audioUrl = ""
    var displayRssData = ArrayList<RSSItem>()
    var currentPosition = 0
    private var mCurrentState: Int = 0
    var mMediaBrowserCompat:MediaBrowserCompat? = null
    private var mMediaControllerCompat: MediaControllerCompat? = null

    private val mMediaBrowserCompatConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            super.onConnected()
            try {

                 mMediaControllerCompat = MediaControllerCompat(this@DisplayDataActivity,
                        mMediaBrowserCompat!!.sessionToken)
                mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback)

            } catch (e: RemoteException) {

            }

        }
    }


    private val mMediaControllerCompatCallback = object : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            if (state == null) {
                return
            }

            when (state.state) {
                PlaybackStateCompat.STATE_PLAYING -> {
                    mCurrentState = STATE_PLAYING
                }
                PlaybackStateCompat.STATE_PAUSED -> {
                    mCurrentState = STATE_PAUSED
                }
            }
        }
    }


    override fun onCardSelected(position: Int) {
        info("clicked position:" + position)
        if (currentPosition != position) {
            audioUrl = displayRssData.get(currentPosition).audioURL
            var podcastRecycler = find<RecyclerView>(R.id.podcastListingRecyclerView)
            var holder = podcastRecycler.findViewHolderForAdapterPosition(currentPosition) as PodcastViewHolder
            holder.linearLayout.isSelected = false;
            currentPosition = position

        } else {
            info("user clicked the same position.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)
        displayRssData = intent.getParcelableArrayListExtra<RSSItem>(ConstantHandler.RSS_DATA)
        info("got data in Diplay. Size of data:" + displayRssData.size)
        audioUrl = displayRssData.get(0).audioURL
        var podcastRecycler = find<RecyclerView>(R.id.podcastListingRecyclerView)
        var linearLayout = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false)

        podcastRecycler.setHasFixedSize(false)
        podcastRecycler.adapter = PodcastViewAdapter(this, displayRssData)
        podcastRecycler.layoutManager = linearLayout

        mMediaBrowserCompat = MediaBrowserCompat(this, ComponentName(this, PodcastMediaService::class.java!!),
                mMediaBrowserCompatConnectionCallback, intent.extras)
        mMediaBrowserCompat.let{
            it?.connect()
        }



        var playButton = find<Button>(R.id.play)
        playButton.setOnClickListener { view ->
            if (playButton.text.toString().equals(PLAY_BUTTON)) {
                playButton.text = PAUSE_BUTTON

            } else {
                playButton.text = PLAY_BUTTON
            }

            if (mCurrentState == STATE_PAUSED) {
                mMediaControllerCompat
                mCurrentState = STATE_PLAYING
            } else {
                if (getSupportMediaController().getPlaybackState().getState() === PlaybackStateCompat.STATE_PLAYING) {
                    getSupportMediaController().getTransportControls().pause()
                }

                mCurrentState = STATE_PAUSED
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaBrowserCompat!!.disconnect()
    }
}
