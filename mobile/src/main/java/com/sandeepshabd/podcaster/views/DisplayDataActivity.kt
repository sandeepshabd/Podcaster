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

class DisplayDataActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)
        var displayRssData = intent.getParcelableArrayListExtra<RSSItem>(ConstantHandler.RSS_DATA)
        info("got data in Diplay. Size of data:" + displayRssData.size)
        var podcastRecycler = find<RecyclerView>(R.id.podcastListingRecyclerView)
        var linearLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        podcastRecycler.layoutManager = linearLayout
        podcastRecycler.setHasFixedSize(false)
        podcastRecycler.adapter = PodcastViewAdapter(this, displayRssData)
    }
}
