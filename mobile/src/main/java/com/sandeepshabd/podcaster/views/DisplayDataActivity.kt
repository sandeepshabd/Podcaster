package com.sandeepshabd.podcaster.views

import android.media.MediaPlayer
import android.os.Bundle
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
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.find
import org.jetbrains.anko.info


class DisplayDataActivity : AppCompatActivity(), AnkoLogger,
        IDisplayView, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private var mediaPlayer: MediaPlayer? = null
    private var playButton: Button? = null
    private var podcastRecycler: RecyclerView? = null

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
        info("buffered:" + p1)
    }

    override fun onCompletion(p0: MediaPlayer?) {
        playButton?.text = PLAY_BUTTON
    }

    var audioUrl = ""
    var displayRssData = ArrayList<RSSItem>()
    var currentPosition = 0
    var dataChanged = false


    override fun onCardSelected(position: Int) {
        info("clicked position:" + position)
        info("prev position:" + currentPosition)
        if (currentPosition != position) {
            dataChanged = true
            audioUrl = displayRssData.get(position).audioURL
            var holder: PodcastViewHolder? = null
            try {
                holder = podcastRecycler?.findViewHolderForAdapterPosition(currentPosition) as PodcastViewHolder

                debug("holder:" + holder)

            } catch (e: Exception) {
                // error("recycler view or holder might be null.")
            }
            currentPosition = position
            if (holder != null) {
                holder?.linearLayout?.background = getDrawable(R.drawable.rect_border)
                holder.linearLayout.isSelected = false
            }
        } else {
            dataChanged = false
            info("user clicked the same position.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)
        displayRssData = intent.getParcelableArrayListExtra<RSSItem>(ConstantHandler.RSS_DATA)
        info("got data in Diplay. Size of data:" + displayRssData.size)
        audioUrl = displayRssData.get(0).audioURL
        podcastRecycler = find(R.id.podcastListingRecyclerView)
        var linearLayout = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false)
        podcastRecycler.let {
            it?.setHasFixedSize(false)
            it?.adapter = PodcastViewAdapter(this, displayRssData)
            it?.layoutManager = linearLayout
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer.let {
            it?.setOnBufferingUpdateListener(this)
            it?.setOnCompletionListener(this)
        }

        var playButtonLocal = find<Button>(R.id.play)

        playButtonLocal.setOnClickListener { view ->

            if (playButtonLocal.text.toString().equals(PLAY_BUTTON)) {
                playButtonLocal.text = PAUSE_BUTTON
                mediaPlayer.let {
                    try {
                        if(dataChanged){
                            it?.reset()
                            dataChanged = false
                        }
                        it?.setDataSource(audioUrl)
                        it?.prepare()
                    } catch (e: Exception) {
                    }
                    try {
                        it?.start()
                    } catch (e: Exception) {
                    }
                }
            } else {
                playButtonLocal.text = PLAY_BUTTON
                mediaPlayer.let {
                    if (it!!.isPlaying) {
                        try {
                            it?.pause()
                        } catch (e: Exception) {
                        }
                    }

                }
            }

        }

        playButton = playButtonLocal
    }
}
