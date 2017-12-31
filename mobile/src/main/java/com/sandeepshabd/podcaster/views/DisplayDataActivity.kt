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
import org.jetbrains.anko.find
import org.jetbrains.anko.info


class DisplayDataActivity : AppCompatActivity(), AnkoLogger,
        IDisplayView, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private var mediaPlayer: MediaPlayer? = null
    private val mediaFileLengthInMilliseconds: Int = 0
    private var playButton: Button? = null

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
        info("buffered:" + p1)
    }

    override fun onCompletion(p0: MediaPlayer?) {
        playButton?.text = PLAY_BUTTON
    }

    var audioUrl = ""
    var displayRssData = ArrayList<RSSItem>()
    var currentPosition = 0


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
                    it?.setDataSource(audioUrl)
                    it?.prepare()
                    it?.start()
                }
            } else {
                playButtonLocal.text = PLAY_BUTTON
                mediaPlayer.let {
                    if (it!!.isPlaying) {
                        it?.pause()
                    }

                }
            }
        }

        playButton = playButtonLocal
    }
}
