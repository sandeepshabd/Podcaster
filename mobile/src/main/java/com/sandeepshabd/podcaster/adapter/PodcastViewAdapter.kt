package com.sandeepshabd.podcaster.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sandeepshabd.podcaster.R
import com.sandeepshabd.podcaster.models.RSSItem

/**
 * Created by sandeepshabd on 12/30/17.
 */

class PodcastViewAdapter(private var rssItems: ArrayList<RSSItem>):RecyclerView.Adapter<PodcastViewHolder>(){

    override fun onBindViewHolder(holder: PodcastViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        val movieCardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_card_view, parent, false)
        return PodcastViewHolder(movieCardView)
    }

    override fun getItemCount(): Int {
        return rssItems.size
    }


}