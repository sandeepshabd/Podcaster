package com.sandeepshabd.podcaster.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sandeepshabd.podcaster.R
import com.sandeepshabd.podcaster.adapter.PodcastViewAdapter
import com.sandeepshabd.podcaster.handler.ConstantHandler
import com.sandeepshabd.podcaster.models.RSSItem
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.info

class DisplayDataActivity : AppCompatActivity(), AnkoLogger, IDisplayView {

    var audioUrl = ""
    var displayRssData = ArrayList<RSSItem>()
    var currentPosition = 0

    override fun onCardSelected(position: Int) {
        info("clicked position:" + position)
        if(currentPosition != position){
            audioUrl = displayRssData.get(currentPosition).audioURL
        }else{
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
        podcastRecycler.layoutManager = linearLayout
        podcastRecycler.setHasFixedSize(false)
        podcastRecycler.adapter = PodcastViewAdapter(this, displayRssData)
    }
}
